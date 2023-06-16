package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	@Autowired
	private CadastroUsuarioService cadastroUsuario;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	

	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;
	
	/*
	 * Assim como no caso de CadastroCidadeService, vamos utilizar o método de busca
	 * ou falha do CadastroCozinhaService, ao invés de usarmos diretamente o
	 * CozinhaRepository
	 */
	
	//  @Transactional é algo de fato necessário, pois esta anotação abstrai todo o trabalho que teríamos de abrir uma transação,
	// gerenciá-la e encerrar a mesma.
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
	    Long cozinhaId = restaurante.getCozinha().getId();
	    Long cidadeId = restaurante.getEndereco().getCidade().getId();
	    
	    Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
	    Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
	    
	    restaurante.setCozinha(cozinha);
	    restaurante.getEndereco().setCidade(cidade);
	    
	    return restauranteRepository.save(restaurante);
	}
	
	
	@Transactional
	public void ativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		
		restauranteAtual.ativar();
	}
	
	@Transactional
	public void inativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		
		restauranteAtual.inativar();
	}
	
	@Transactional
	public void ativar(List<Long> restauranteIds) {
		restauranteIds.forEach(this::ativar);
	}
	
	@Transactional
	public void inativar(List<Long> restauranteIds) {
		restauranteIds.forEach(this::inativar);
	}
	
	
	@Transactional
	public void abrir(Long restauranteId) {
	    Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
	    
	    restauranteAtual.abrir();
	}

	@Transactional
	public void fechar(Long restauranteId) {
	    Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
	    
	    restauranteAtual.fechar();
	}       
	
	
	/*
	 * public Restaurante salvar(Restaurante restaurante) { Long cozinhaId =
	 * restaurante.getCozinha().getId(); // Aqui uma forma de fazer diferente,
	 * colocando a exceção diretamente! Cozinha cozinha =
	 * cozinhaRepository.findById(cozinhaId) .orElseThrow(()-> new
	 * EntidadeNaoEncontradaException(
	 * String.format("Não existe cadastro de cozinha com código %d", cozinhaId)));
	 * 
	 * 
	 * restaurante.setCozinha(cozinha);
	 * 
	 * return restauranteRepository.save(restaurante); }
	 */
    
    public Restaurante buscarOuFalhar(Long restauranteId) {
        return restauranteRepository.findById(restauranteId)
            .orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
    }


	
		
    @Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		
		restaurante.removerFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		
		restaurante.adicionarFormaPagamento(formaPagamento);
	}	
	
	@Transactional
	public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
	    Restaurante restaurante = buscarOuFalhar(restauranteId);
	    Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
	    
	    restaurante.removerResponsavel(usuario);
	}

	@Transactional
	public void associarResponsavel(Long restauranteId, Long usuarioId) {
	    Restaurante restaurante = buscarOuFalhar(restauranteId);
	    Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
	    
	    restaurante.adicionarResponsavel(usuario);
	}
	
}
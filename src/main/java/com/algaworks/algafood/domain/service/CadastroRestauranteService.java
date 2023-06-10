package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.repository.CozinhaRepository;
import com.algaworks.algafood.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	private static final String MSG_RESTAURANTE_NAO_ENCONTRADO 
    = "Não existe um cadastro de restaurante com código %d";
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	

	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	/*
	 * Assim como no caso de CadastroCidadeService, vamos utilizar o método de busca
	 * ou falha do CadastroCozinhaService, ao invés de usarmos diretamente o
	 * CozinhaRepository
	 */
	
	public Restaurante salvar(Restaurante restaurante) {
	    Long cozinhaId = restaurante.getCozinha().getId();
	    
	    Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
	    
	    restaurante.setCozinha(cozinha);
	    
	    return restauranteRepository.save(restaurante);
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
	
}
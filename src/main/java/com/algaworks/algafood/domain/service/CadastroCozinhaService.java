package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service // É um tipo de componente
public class CadastroCozinhaService {
	
	private static final String MSG_COZINHA_EM_USO 
	= "Cozinha de código %d não pode ser removida, pois está em uso";

private static final String MSG_COZINHA_NAO_ENCONTRADA 
	= "Não existe um cadastro de cozinha com código %d";
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Transactional
	// Repare que o método salvar tem o mesmo nome em repositorio e em cozinha!
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	@Transactional
	// Estamos abaixo no Throw relançando ema exception de negócios
	public void excluir(Long cozinhaId) {
		try {
			cozinhaRepository.deleteById(cozinhaId);
			cozinhaRepository.flush();
			// Não é legal deixar o tratamento de exceções na classe de serviço e sim no controller, por isso está comentado e 
			//vamos para a classe de controler!
		} catch(EmptyResultDataAccessException e)     {	
	//		throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Não existe um cadastro de cozinha com código %d", cozinhaId));
			throw new CozinhaNaoEncontradaException(cozinhaId);
		} catch (DataIntegrityViolationException e) {
			
			throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, cozinhaId));
		}
	}

	public Cozinha buscarOuFalhar(Long cozinhaId) {
		return cozinhaRepository.findById(cozinhaId)
			.orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
	}

}

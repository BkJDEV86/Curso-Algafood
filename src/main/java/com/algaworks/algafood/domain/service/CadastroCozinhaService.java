package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.repository.CozinhaRepository;

@Service // É um tipo de componente
public class CadastroCozinhaService {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	// Repare que o método salvar tem o mesmo nome em repositorio e em cozinha!
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	// Estamos abaixo no Throw relançando ema exception de negócios
	public void excluir(Long cozinhaId) {
		try {
			cozinhaRepository.deleteById(cozinhaId);
		} catch(EmptyResultDataAccessException e)     {	
			
			throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de cozinha com código %d", cozinhaId));
		} catch (DataIntegrityViolationException e) {
			
			throw new EntidadeEmUsoException(String.format("Cozinha de código %d não pode ser removida pois está em uso", cozinhaId));
		}
	}

}

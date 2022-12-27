package com.algaworks.algafood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.repository.CozinhaRepository;

@RestController // É um componente Controller do tipo Rest que tem tem um ResponseBody embutido, que seria
// uma forma de devolver um corpo na Requisição quando ela é chamada!
// Quando tem mais de uma coisa declarada é necessário colocar value
@RequestMapping(value = "/cozinhas", produces =  MediaType.APPLICATION_JSON_VALUE )
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@GetMapping
	public List<Cozinha> listar() {
		return cozinhaRepository.listar();
	}
	
	
	// Abaixo é a mesma coisa que /cozinhas/{cozinhaId}
	@GetMapping("/{cozinhaId}")
	
	public Cozinha buscar(@PathVariable("cozinhaId")Long cozinhaId) {
		return cozinhaRepository.buscar(cozinhaId);
		
	}
	
	

}

package com.algaworks.algafood.api.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.api.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController // É um componente Controller do tipo Rest que tem tem um ResponseBody embutido,
				// que seria
// uma forma de devolver um corpo na Requisição quando ela é chamada!
// Quando tem mais de uma coisa declarada é necessário colocar value
@RequestMapping(value = "/cozinhas")
public class CozinhaController  implements CozinhaControllerOpenApi {
	
	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;

	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;     

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;


	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
		
		List<CozinhaModel> cozinhasModel = cozinhaModelAssembler
				.toCollectionModel(cozinhasPage.getContent());
		
		// abaixo está passando  conteúdo, algumas propriedades de pageable e o total de elementos.
		Page<CozinhaModel> cozinhasModelPage = new PageImpl<>(cozinhasModel, pageable, 
				cozinhasPage.getTotalElements());
		
		return cozinhasModelPage;
	}

	@GetMapping(value = "/{cozinhaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CozinhaModel buscar(@PathVariable Long cozinhaId) {
	    Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
	    
	    return cozinhaModelAssembler.toModel(cozinha);
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
	    Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
	    cozinha = cadastroCozinha.salvar(cozinha);
	    
	    return cozinhaModelAssembler.toModel(cozinha);
	}

	@PutMapping(value = "/{cozinhaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CozinhaModel atualizar(@PathVariable Long cozinhaId,
	        @RequestBody @Valid CozinhaInput cozinhaInput) {
	    Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
	    cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
	    cozinhaAtual = cadastroCozinha.salvar(cozinhaAtual);
	    
	    return cozinhaModelAssembler.toModel(cozinhaAtual);
	}
	
	/*
	 * @DeleteMapping("/{cozinhaId}") // Não é interessante tratar exceções no
	 * repositório e sim // na classe de serviços public ResponseEntity<?>
	 * remover(@PathVariable Long cozinhaId){ try {
	 * 
	 * cadastroCozinha.excluir(cozinhaId);
	 * 
	 * 
	 * return ResponseEntity.noContent().build();
	 * 
	 * 
	 * 
	 * //} catch (EntidadeNaoEncontradaException e){ // return
	 * ResponseEntity.notFound().build(); } catch (EntidadeEmUsoException e) { // O
	 * que acontece é que não podemos excluir um elemento que é chave principal //
	 * da tabela return
	 * ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); }
	 * 
	 * }
	 */
	
	@DeleteMapping("/{cozinhaId}") 
	 @ResponseStatus(HttpStatus.NO_CONTENT)
	 public void remover(@PathVariable Long cozinhaId){
		// Abaixo não precisa mais tratar pois herda de ResponseStatusException
//	  try {
		  cadastroCozinha.excluir(cozinhaId);
//	} catch (EntidadeNaoEncontradaException e) {
///		throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		// Abaixo outra forma
		// throw new ServerWebInputException(e.getMessage());
	
	 
	  
	  
	 }
	 

}

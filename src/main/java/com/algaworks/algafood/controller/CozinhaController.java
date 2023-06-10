package com.algaworks.algafood.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import com.algaworks.algafood.repository.CozinhaRepository;

@RestController // É um componente Controller do tipo Rest que tem tem um ResponseBody embutido,
				// que seria
// uma forma de devolver um corpo na Requisição quando ela é chamada!
// Quando tem mais de uma coisa declarada é necessário colocar value
@RequestMapping(value = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@GetMapping
	public List<Cozinha> listar() {
		return cozinhaRepository.findAll();
	}

	// Abaixo é a mesma coisa que /cozinhas/{cozinhaId}
	@GetMapping("/{cozinhaId}")
	public Cozinha buscar(@PathVariable Long cozinhaId) {
		return cadastroCozinha.buscarOuFalhar(cozinhaId);
		
		// Abaixo é outra forma antiga
		/* Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);
		 * // O Optional evita tomar null exception. E como cozinha é um optional
		 * chamamos // através do get if (cozinha.isPresent()) { return
		 * ResponseEntity.ok(cozinha.get()); }
		 * 
		 * // os dois abaixo são iguais // return
		 * ResponseEntity.status(HttpStatus.NOT_FOUND).build(); return
		 * ResponseEntity.notFound().build();
		 */
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha) {
		return cadastroCozinha.salvar(cozinha);
	}

	@PutMapping("/{cozinhaId}")
	// o padrão retornado é 200 por isso não colocamos @ResponseStatus
	public Cozinha atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
		
        Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
		
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
		
		return cadastroCozinha.salvar(cozinhaAtual);

		//Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(cozinhaId);

		/*if (cozinhaAtual.isPresent()) {

			// Cozinha é o que recebemos no corpo da requisição e cozinhaAtual é o que está
			// persistido no banco de dados!
			// O que temos que fazer é copiar os valores que estão no corpo da requisição
			// (cozinha) para
			// dentro do banco cozinhaAtual.
			// cozinhaAtual.setNome(cozinha.getnome());
			// Aqui copiando a cozinha para a cozinhaatual dentro de optional
			BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");// Aqui terceiro parâmetro é o que tem que ser
																		// ignorado
			// como estou copiando de um que o id é nulo para outro que o id é não nulo,
			// causará erro, logo temos que ignorar
			// o id

			Cozinha cozinhaSalva = cadastroCozinha.salvar(cozinhaAtual.get());
			return ResponseEntity.ok(cozinhaSalva);*/
	//	}

	//	return ResponseEntity.notFound().build();

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

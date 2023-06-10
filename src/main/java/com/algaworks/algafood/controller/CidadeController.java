package com.algaworks.algafood.controller;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.exceptionthandler.Problem;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;

import com.algaworks.algafood.domain.service.CadastroCidadeService;
import com.algaworks.algafood.repository.CidadeRepository;
@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@GetMapping
	public List<Cidade> listar() {
		return cidadeRepository.findAll();
	}
	
	@GetMapping("/{cidadeId}")
	public Cidade buscar(@PathVariable Long cidadeId) {
	    return cadastroCidade.buscarOuFalhar(cidadeId);
	}
	
	/*
	 * @GetMapping("/{cidadeId}") public ResponseEntity<Cidade> buscar(@PathVariable
	 * Long cidadeId) { Optional<Cidade> cidade =
	 * cidadeRepository.findById(cidadeId);
	 * 
	 * if (cidade.isPresent()) { return ResponseEntity.ok(cidade.get()); }
	 * 
	 * return ResponseEntity.notFound().build(); }
	 */
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade adicionar(@RequestBody Cidade cidade) {
		try {
			return cadastroCidade.salvar(cidade);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	/*
	 * @PostMapping public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {
	 * try { cidade = cadastroCidade.salvar(cidade);
	 * 
	 * return ResponseEntity.status(HttpStatus.CREATED) .body(cidade); } catch
	 * (EntidadeNaoEncontradaException e) { return ResponseEntity.badRequest()
	 * .body(e.getMessage()); } }
	 */
	
	@PutMapping("/{cidadeId}")
	public Cidade atualizar(@PathVariable Long cidadeId,
	        @RequestBody Cidade cidade) {
	    Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);
	    
	    BeanUtils.copyProperties(cidade, cidadeAtual, "id");
	    
	    try {
			return cadastroCidade.salvar(cidadeAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	/*
	 * @PutMapping("/{cidadeId}") public ResponseEntity<?> atualizar(@PathVariable
	 * Long cidadeId,
	 * 
	 * @RequestBody Cidade cidade) { try { // Podemos usar o orElse(null) também,
	 * que retorna a instância de cidade // dentro do Optional, ou null, caso ele
	 * esteja vazio, // mas nesse caso, temos a responsabilidade de tomar cuidado
	 * com NullPointerException Cidade cidadeAtual =
	 * cidadeRepository.findById(cidadeId).orElse(null);
	 * 
	 * if (cidadeAtual != null) { BeanUtils.copyProperties(cidade, cidadeAtual,
	 * "id");
	 * 
	 * cidadeAtual = cadastroCidade.salvar(cidadeAtual); return
	 * ResponseEntity.ok(cidadeAtual); }
	 * 
	 * return ResponseEntity.notFound().build();
	 * 
	 * } catch (EntidadeNaoEncontradaException e) { return
	 * ResponseEntity.badRequest() .body(e.getMessage()); } }
	 */
	
	
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
	    cadastroCidade.excluir(cidadeId);	
	}
	
	
	/*
	 * @DeleteMapping("/{cidadeId}") public ResponseEntity<Cidade>
	 * remover(@PathVariable Long cidadeId) { try {
	 * cadastroCidade.excluir(cidadeId); return ResponseEntity.noContent().build();
	 * 
	 * } catch (EntidadeNaoEncontradaException e) { return
	 * ResponseEntity.notFound().build();
	 * 
	 * } catch (EntidadeEmUsoException e) { return
	 * ResponseEntity.status(HttpStatus.CONFLICT).build(); } }
	 */
	

}
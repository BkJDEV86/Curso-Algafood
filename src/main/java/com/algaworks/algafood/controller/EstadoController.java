package com.algaworks.algafood.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import com.algaworks.algafood.repository.EstadoRepository;

// É um componente Controller do tipo Rest que tem tem um ResponseBody embutido, que seria
// uma forma de devolver um corpo na Requisição quando ela é chamada!
@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@GetMapping
	public List<Estado> listar() {
		return estadoRepository.findAll();
	}
	
	/*
	 * Agora que nosso Service está fazendo as validações necessárias, vamos apenas
	 * delegar a ele essa função.
	 * 
	 * Nos métodos em que chamávamos findById, iremos chamar agora buscarOuFalhar,
	 * assim a validação é garantida.
	 */
	
	@GetMapping("/{estadoId}")
	public Estado buscar(@PathVariable Long estadoId) {
	    return cadastroEstado.buscarOuFalhar(estadoId);
	}
	
	/*
	 * @GetMapping("/{estadoId}") public ResponseEntity<Estado> buscar(@PathVariable
	 * Long estadoId) { Optional<Estado> estado =
	 * estadoRepository.findById(estadoId);
	 * 
	 * if (estado.isPresent()) { return ResponseEntity.ok(estado.get()); }
	 * 
	 * return ResponseEntity.notFound().build(); }
	 */
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado adicionar(@RequestBody Estado estado) {
		return cadastroEstado.salvar(estado);
	}
	
	@PutMapping("/{estadoId}")
	public Estado atualizar(@PathVariable Long estadoId,
	        @RequestBody Estado estado) {
	    Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);
	    
	    BeanUtils.copyProperties(estado, estadoAtual, "id");
	    
	    return cadastroEstado.salvar(estadoAtual);
	}
	
	/*
	 * @PutMapping("/{estadoId}") public ResponseEntity<Estado>
	 * atualizar(@PathVariable Long estadoId,
	 * 
	 * @RequestBody Estado estado) { Estado estadoAtual =
	 * estadoRepository.findById(estadoId).orElse(null);
	 * 
	 * if (estadoAtual != null) { BeanUtils.copyProperties(estado, estadoAtual,
	 * "id");
	 * 
	 * estadoAtual = cadastroEstado.salvar(estadoAtual); return
	 * ResponseEntity.ok(estadoAtual); }
	 * 
	 * return ResponseEntity.notFound().build(); }
	 */
	
	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId) {
	    cadastroEstado.excluir(estadoId);	
	}
	
	/*
	 * @DeleteMapping("/{estadoId}") public ResponseEntity<?> remover(@PathVariable
	 * Long estadoId) { try { cadastroEstado.excluir(estadoId); return
	 * ResponseEntity.noContent().build();
	 * 
	 * } catch (EntidadeNaoEncontradaException e) { return
	 * ResponseEntity.notFound().build();
	 * 
	 * } catch (EntidadeEmUsoException e) { return
	 * ResponseEntity.status(HttpStatus.CONFLICT) .body(e.getMessage()); } }
	 */
	
}
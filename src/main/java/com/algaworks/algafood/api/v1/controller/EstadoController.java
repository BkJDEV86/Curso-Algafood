package com.algaworks.algafood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import com.algaworks.algafood.api.model.input.EstadoInput;
import com.algaworks.algafood.api.v1.assembler.EstadoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.EstadoModelAssembler;
import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;


// É um componente Controller do tipo Rest que tem tem um ResponseBody embutido, que seria
// uma forma de devolver um corpo na Requisição quando ela é chamada!
@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private EstadoModelAssembler estadoModelAssembler;

	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;   

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<EstadoModel> listar() {
	    List<Estado> todosEstados = estadoRepository.findAll();
	    
	    return estadoModelAssembler.toCollectionModel(todosEstados);
	}
	
	/*
	 * Agora que nosso Service está fazendo as validações necessárias, vamos apenas
	 * delegar a ele essa função.
	 * 
	 * Nos métodos em que chamávamos findById, iremos chamar agora buscarOuFalhar,
	 * assim a validação é garantida.
	 */
	
	@GetMapping(value = "/{estadoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public EstadoModel buscar(@PathVariable Long estadoId) {
		Estado estado = cadastroEstado.buscarOuFalhar(estadoId);
	    
	    return estadoModelAssembler.toModel(estado);
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
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {
	    Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);
	    
	    estado = cadastroEstado.salvar(estado);
	    
	    return estadoModelAssembler.toModel(estado);
	}
	
	@PutMapping(value = "/{estadoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public EstadoModel atualizar(@PathVariable Long estadoId,
	        @RequestBody @Valid EstadoInput estadoInput) {
	    Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);
	    
	    estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);
	    
	    estadoAtual = cadastroEstado.salvar(estadoAtual);
	    
	    return estadoModelAssembler.toModel(estadoAtual);
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
	
	
	/*
	 * A jpa não executa as operações na exata ordem em que descreve em seu código.
	 * Com isso, em alguns casos, podemos necessitar que alguma operação ocorra
	 * antes de outra. E para que possamos conseguir este efeito, executamos o
	 * flush. Desta forma, uma dada operação será imediatamente descarregada no
	 * banco de dados.
	 */
	
	
	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId) {
	    cadastroEstado.excluir(estadoId);	
	    estadoRepository.flush();
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
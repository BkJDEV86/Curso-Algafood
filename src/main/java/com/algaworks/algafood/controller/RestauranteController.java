package com.algaworks.algafood.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;

import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.repository.RestauranteRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController // É um componente Controller do tipo Rest que tem tem um ResponseBody embutido, que seria
// uma forma de devolver um corpo na Requisição quando ela é chamada!
// Quando tem mais de uma coisa declarada é necessário colocar value

@RequestMapping(value = "/restaurantes" )
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	
	
	@GetMapping
	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}
	
	@GetMapping("/{restauranteId}")
	public Restaurante buscar(@PathVariable Long restauranteId) {
		
	    return cadastroRestaurante.buscarOuFalhar(restauranteId);
	}
	
	
	/*
	 * //Abaixo é a mesma coisa que /cozinhas/{cozinhaId}
	 * 
	 * @GetMapping("/{restauranteId}") public ResponseEntity<Restaurante>
	 * buscar(@PathVariable Long restauranteId){ Optional<Restaurante> restaurante =
	 * restauranteRepository.findById(restauranteId);
	 * 
	 * if (restaurante.isPresent()) { return ResponseEntity.ok(restaurante.get()); }
	 * // os dois abaixo são iguais //return
	 * ResponseEntity.status(HttpStatus.NOT_FOUND).build(); return
	 * ResponseEntity.notFound().build(); }
	 */
		
		
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante adicionar(@RequestBody Restaurante restaurante) {
		try {
	        return cadastroRestaurante.salvar(restaurante);
	    } catch (CozinhaNaoEncontradaException e) {
	        throw new NegocioException(e.getMessage());
	    }
	}
	
	/*
	 * @PostMapping public ResponseEntity<?> adicionar(@RequestBody Restaurante
	 * restaurante) { try { restaurante = cadastroRestaurante.salvar(restaurante);
	 * 
	 * return ResponseEntity.status(HttpStatus.CREATED) .body(restaurante); } catch
	 * (EntidadeNaoEncontradaException e) { return ResponseEntity.badRequest()
	 * .body(e.getMessage()); } }
	 */
	
	
	
	@PutMapping("/{restauranteId}")
	public Restaurante atualizar(@PathVariable Long restauranteId,
	        @RequestBody Restaurante restaurante) {
	    Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
	    
	    BeanUtils.copyProperties(restaurante, restauranteAtual, 
	            "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
	    
	    try {
	        return cadastroRestaurante.salvar(restauranteAtual);
	    } catch (CozinhaNaoEncontradaException e) {
	        throw new NegocioException(e.getMessage());
	    }
	}
	
	
	/*
	 * @PutMapping("/{restauranteId}") public ResponseEntity<?>
	 * atualizar(@PathVariable Long restauranteId,
	 * 
	 * @RequestBody Restaurante restaurante) { try { Restaurante restauranteAtual =
	 * restauranteRepository .findById(restauranteId).orElse(null);
	 * 
	 * // Aqui abaixo não copia as formas de pagamento para que elas não sejam
	 * alteradas // Aqui abaixo está copiando restaurante para retauranteAtual // A
	 * data de atualização não precisa igorar pois o hibernae já entende como uma
	 * atualização if (restauranteAtual != null) {
	 * BeanUtils.copyProperties(restaurante, restauranteAtual, "id",
	 * "formasPagamento", "endereco", "dataCadastro", "produtos");
	 * 
	 * restauranteAtual = cadastroRestaurante.salvar(restauranteAtual); return
	 * ResponseEntity.ok(restauranteAtual); }
	 * 
	 * return ResponseEntity.notFound().build();
	 * 
	 * } catch (EntidadeNaoEncontradaException e) { return
	 * ResponseEntity.badRequest() .body(e.getMessage()); } }
	 */
	
	
	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino,
			HttpServletRequest request) {
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			
			Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
			
			dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				field.setAccessible(true);
				
				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
				
				ReflectionUtils.setField(field, restauranteDestino, novoValor);
			});
		} catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}
	}
	
}






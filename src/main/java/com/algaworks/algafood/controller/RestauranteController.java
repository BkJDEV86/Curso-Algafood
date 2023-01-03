package com.algaworks.algafood.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.repository.RestauranteRepository;

@RestController // É um componente Controller do tipo Rest que tem tem um ResponseBody embutido, que seria
// uma forma de devolver um corpo na Requisição quando ela é chamada!
// Quando tem mais de uma coisa declarada é necessário colocar value

@RequestMapping(value = "/restaurantes", produces =  MediaType.APPLICATION_JSON_VALUE )
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	
	
	@GetMapping
	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}
	
	
	

	//Abaixo é a mesma coisa que /cozinhas/{cozinhaId}
	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId){
Optional<Restaurante> restaurante = restauranteRepository.findById(restauranteId);
		
		if (restaurante.isPresent()) {
			return ResponseEntity.ok(restaurante.get());
		}
		        // os dois abaixo são iguais
				//return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.notFound().build();
	}
		
		
	
		
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
		try {
			restaurante = cadastroRestaurante.salvar(restaurante);
			
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}
	}
	
	
	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId,
			@RequestBody Restaurante restaurante) {
		try {
			Restaurante restauranteAtual = restauranteRepository
					.findById(restauranteId).orElse(null);
			
			// Aqui abaixo não copia as formas de pagamento para que elas não sejam alteradas
			// Aqui abaixo está copiando restaurante para retauranteAtual
			// A data de atualização não precisa igorar pois o hibernae já entende como uma atualização
			if (restauranteAtual != null) {
				BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
				
				restauranteAtual = cadastroRestaurante.salvar(restauranteAtual);
				return ResponseEntity.ok(restauranteAtual);
			}
			
			return ResponseEntity.notFound().build();
		
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}
	}
	
	

	 // Método patch trabalhoso... não é aconselhado seu uso
	   // Cada propriedade de camposOrgem vamos atribuir para restaurante destino
//		private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
			// O objeject mapper é para não precisarmos fazer casting das propriedades
//			ObjectMapper objectMapper = new ObjectMapper();
	 //		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
			//			
			//		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			//			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				//			field.setAccessible(true); // É para acessar campos privados
				
			//				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
				
//				System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);
				
			//				ReflectionUtils.setField(field, restauranteDestino, novoValor);
			//		});
			//		}
		
	}







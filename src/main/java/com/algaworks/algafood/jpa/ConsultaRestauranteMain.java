package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.repository.RestauranteRepository;

public class ConsultaRestauranteMain {

	// Aqui nós precisamos acessar o contexto da aplicação(no caso sem web) para termos acesso aos beans sendo assim podemos
	// ter acesso as classes
	// Algafood aplication é a fonte primária das aplicações por isso ela está sendo passada por parâmetro
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		 RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);
		
		List<Restaurante> restaurantes = restauranteRepository.listar();
		
		for (Restaurante restaurante : restaurantes) {
			System.out.printf("%s - %f - %s", restaurante.getNome(), restaurante.getTaxaFrete(), restaurante.getCozinha().getNome());
		}
	}
	
}
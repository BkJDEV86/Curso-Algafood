package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;

import com.algaworks.algafood.repository.CozinhaRepository;
import com.algaworks.algafood.repository.RestauranteRepository;


public class InclusaoRestauranteMain {
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
	
		 RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);
	
	Restaurante restaurante1 = new Restaurante();
	restaurante1.setNome("Brasileira");
	
	Restaurante restaurante2 = new Restaurante();
	restaurante2.setNome("Japonesa");
	
	restauranteRepository.salvar(restaurante1);
	restauranteRepository.salvar(restaurante2);
	
	}
	
}



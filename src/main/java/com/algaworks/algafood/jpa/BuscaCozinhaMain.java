package com.algaworks.algafood.jpa;



import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.repository.CozinhaRepository;

public class BuscaCozinhaMain {

	// Aqui nós precisamos acessar o contexto da aplicação(no caso sem web) para termos acesso aos beans sendo assim podemos
	// ter acesso as classes
	// Algafood aplication é a fonte primária das aplicações por isso ela está sendo passada por parâmetro
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		 CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);
		
		Cozinha cozinha =cozinhaRepository .buscar(1L);
		
		System.out.println(cozinha.getNome());
	}
	
}
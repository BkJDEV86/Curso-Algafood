package com.algaworks.algafood;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.algaworks.algafood.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
// Aqui abaixo o nosso repositorio base não é mais o singlerepository e sim o customjparepository
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
@EnableWebMvc
public class AlgafoodApiApplication  {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(AlgafoodApiApplication.class, args);
	}

}

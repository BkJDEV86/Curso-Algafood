package com.algaworks.algafood.core.web;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // qualqer caminho
			.allowedMethods("*"); // qualquer método
//			.allowedOrigins("*")
//			.maxAge(30);
	}
	
	@Bean// Ao receber uma requisição ele gera um hash da resposta e coloca um cabeçalho tag.
	// verifica se a tag é igual e caso não seja envia outra.
	public Filter shallowEtagHeaderFilter() {
		return new ShallowEtagHeaderFilter();
	}
	
}

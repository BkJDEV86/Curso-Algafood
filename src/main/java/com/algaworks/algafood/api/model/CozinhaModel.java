package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaModel {

	
	/*
	 * A estratégia por trás do modelmapper é associar algum nome de origem a algum destino
	 * Origem: cozinha, nome;
	 * Destino: cozinha, cozinha, nome;
	 * 
	 */
	
	
	private Long id;
	private String nome;
	
}
package com.algaworks.algafood.api.model;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

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
	
	@JsonView(RestauranteView.Resumo.class)
	private Long id;
	@JsonView(RestauranteView.Resumo.class)
	private String nome;
	
}
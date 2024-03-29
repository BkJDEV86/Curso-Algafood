package com.algaworks.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cozinhas")
@Setter
@Getter
public class CozinhaModel extends RepresentationModel<CozinhaModel> {

	
	/*
	 * A estratégia por trás do modelmapper é associar algum nome de origem a algum destino
	 * Origem: cozinha, nome;
	 * Destino: cozinha, cozinha, nome;
	 * 
	 */
	@ApiModelProperty(example = "1")
	//@JsonView(RestauranteView.Resumo.class)
	private Long id;

	@ApiModelProperty(example = "Brasileira")
	//(RestauranteView.Resumo.class)
	private String nome;
	
}
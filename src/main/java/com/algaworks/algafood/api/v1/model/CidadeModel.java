package com.algaworks.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@ApiModel(value = "Cidade", description = "Representa uma cidade") 
// não vamos mudar acima pois é muito óbvio.
@Relation(collectionRelation = "cidades")// É o nome que vai aparecer no payload.
@Setter
@Getter// RepresentationModel é um container de links, com métodos.
public class CidadeModel extends RepresentationModel<CidadeModel> {

	//@ApiModelPropety(value = "ID da cidade", example = "1")
	@ApiModelProperty(example = "1")
    private Long id;
	@ApiModelProperty( example = "Uberlândia")
    private String nome;
    private EstadoModel estado;
    
}       
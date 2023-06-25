package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@ApiModel(value = "Cidade", description = "Representa uma cidade") 
// não vamos mudar acima pois é muito óbvio.
@Setter
@Getter
public class CidadeModel {

	//@ApiModelPropety(value = "ID da cidade", example = "1")
	@ApiModelProperty(example = "1")
    private Long id;
	@ApiModelProperty( example = "Uberlândia")
    private String nome;
    private EstadoModel estado;
    
}       
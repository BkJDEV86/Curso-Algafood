package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstadoIdInput {

	// Aqui é obrigatório colocar required true pois por padrão a anotação @ApiModelProperty
	// tem por padrão required = false.
	@ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long id;
    
}
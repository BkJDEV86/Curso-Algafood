package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FormaPagamentoInput {
	
	@ApiModelProperty(example = "1", required = true)
	@NotNull
	private Long id;

	@ApiModelProperty(example = "Cartão de crédito", required = true)
	@NotBlank
	private String descricao;
} 
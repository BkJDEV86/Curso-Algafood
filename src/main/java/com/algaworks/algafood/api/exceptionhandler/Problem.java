package com.algaworks.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("Problema")
@JsonInclude(Include.NON_NULL) // Só inclua na representação da resposta o que não for nulo.
@Getter
@Builder
public class Problem {
	
	@ApiModelProperty(example = "400", position = 1)// O position para respeitar a ordem mas não tá funcionando. Foi aberta uma issui
	private Integer status;
	@ApiModelProperty(example = "2019-12-01T18:09:02.70844Z", position = 5)	
	private String type;
	@ApiModelProperty(example = "https://algafood.com.br/dados-invalidos", position = 10)
	private String title;
	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.",
			position = 20)
	private String detail;
	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.",
			position = 25)
	private String userMessage;
	@ApiModelProperty(value = "Lista de objetos ou campos que geraram o erro (opcional)",
			position = 30)
	private OffsetDateTime timestamp;
    private List<Object> objects;
	
    @ApiModel("ObjetoProblema")
	@Getter
	@Builder
	public static class Object {
		
    	@ApiModelProperty(example = "preco")
		private String name;
    	
    	@ApiModelProperty(example = "O preço é obrigatório")
		private String userMessage;
		
	}
	
}
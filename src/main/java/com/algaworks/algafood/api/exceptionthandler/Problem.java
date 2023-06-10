package com.algaworks.algafood.api.exceptionthandler;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL) // Só inclua na representação da resposta o que não for nulo.
@Getter
@Builder
public class Problem {
	
	private Integer status;
	private String type;
	private String title;
	private String detail;

	private String userMessage;
	private LocalDateTime timestamp;
	
}
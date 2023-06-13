package com.algaworks.algafood.api.exceptionthandler;

import java.time.OffsetDateTime;
import java.util.List;

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
	private OffsetDateTime timestamp;
    private List<Object> objects;
	
	@Getter
	@Builder
	public static class Object {
		
		private String name;
		private String userMessage;
		
	}
	
}
package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Não usa mais está apiExceptionHandler
//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NegocioException(String mensagem) {
		super(mensagem);
	}
	
	// Se coloca Throwable porque é de onde vem todas as exceções
	public NegocioException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
	
}
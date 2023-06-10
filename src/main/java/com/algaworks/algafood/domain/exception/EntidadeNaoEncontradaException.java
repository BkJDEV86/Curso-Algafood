package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Não tem necessidade abaixo pois a exceção está sendo relançada!
// O uso de ResponseStatus deixa o tipo de erro engessado como abaixo, já estender a responsestatusexception podemos colocar
// o nosso tipo
//Não usa mais está apiExceptionHandler
//@ResponseStatus(value = HttpStatus.NOT_FOUND) //, reason = "Entidade não encontrada")
// O responsestatusecpetion é idela quando se tem uma unica exceção e se pode mudar o código várias vezes)not found, conflict)
// public class EntidadeNaoEncontradaException extends ResponseStatusException {
public abstract class EntidadeNaoEncontradaException extends NegocioException {
	
	public EntidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
		
	}

	

}

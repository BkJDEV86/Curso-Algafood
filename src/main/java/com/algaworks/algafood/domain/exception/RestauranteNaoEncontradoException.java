package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public RestauranteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
    
    public RestauranteNaoEncontradoException(Long cidadeId) {
        this(String.format("Não existe um cadastro de cidade com código %d", cidadeId));
    }   
}  
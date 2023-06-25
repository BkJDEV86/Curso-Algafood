package com.algaworks.algafood.domain.service;



import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

public interface EnvioEmailService {

	void enviar(Mensagem mensagem);
	
	@Getter
	@Builder
	class Mensagem {
		
		@Singular // com essa anotacao eu posso enviar para destinatario ao inves de destinatarios.
		private Set<String> destinatarios;
		@NonNull
		private String assunto;
		@NonNull
		private String corpo;
		
		@Singular("variavel")// aqui abaixo como não é pssível singularizar , temos que escrever o que queremos.
		private Map<String, Object> variaveis;
		
	}
	
}
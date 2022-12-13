package com.algaworks.algafood.listener;

import org.springframework.context.event.EventListener;

import com.algaworks.algafood.di.service.ClienteAtivadoEvent;

public class EmissaoNotaFiscalService {
	
	@EventListener
	public void clienteAtivadoListener(ClienteAtivadoEvent event) {
		System.out.println("Emitindo nota fiscal para cliente " + event.getCliente().getNome());
	}

}

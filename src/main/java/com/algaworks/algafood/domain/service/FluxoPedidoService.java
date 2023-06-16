package com.algaworks.algafood.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.enumerations.StatusPedido;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedido;
	
	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
		
		if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
			throw new NegocioException(
					String.format("Status do pedido %d não pode ser alterado de %s para %s",
							pedido.getId(), pedido.getStatus().getDescricao(),StatusPedido.CONFIRMADO.getDescricao())); 
						
		}
		
		pedido.confirmar();
		pedido.setDataConfirmacao(OffsetDateTime.now());
	}
	
	@Transactional
	public void cancelar(String codigoPedido) {
	    Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
	    
	    if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
	        throw new NegocioException(
	                String.format("Status do pedido %d não pode ser alterado de %s para %s",
	                        pedido.getId(), pedido.getStatus().getDescricao(), 
	                        StatusPedido.CANCELADO.getDescricao()));
	    }
	    
	    pedido.cancelar();
	    pedido.setDataCancelamento(OffsetDateTime.now());
	}

	@Transactional
	public void entregar(String codigoPedido) {
	    Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
	    
	    if (!pedido.getStatus().equals(StatusPedido.CONFIRMADO)) {
	        throw new NegocioException(
	                String.format("Status do pedido %d não pode ser alterado de %s para %s",
	                        pedido.getId(), pedido.getStatus().getDescricao(), 
	                        StatusPedido.ENTREGUE.getDescricao()));
	    }
	    
	    pedido.entregar();
	    pedido.setDataEntrega(OffsetDateTime.now());
	}
	
}
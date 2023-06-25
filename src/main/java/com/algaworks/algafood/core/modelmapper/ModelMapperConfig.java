package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.model.EnderecoModel;
import com.algaworks.algafood.api.model.input.ItemPedidoInput;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		
		
		var modelMapper = new ModelMapper();
		
		
//		modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
//		.addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);
	
		// Aqui abaixo é para pular o id para não dar erro.
	   modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
		.addMappings(mapper -> mapper.skip(ItemPedido::setId));
		
		
		
	
	
	var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(
			Endereco.class, EnderecoModel.class);
	
	// Aqui abaixo está pegando o value que é enderecoSrc.getCidade().getEstado().getNome()
	// e colocando no destino. Não vai retornar tostring e sim o nome do estado.
	enderecoToEnderecoModelTypeMap.<String>addMapping(
			enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
			(enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));
	
	

	return modelMapper;
}
}
package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Embeddable // Aqui estamos dizendo que essa classe é uma parte de uma entidade( tem a capacidade de 
// ser incormporada em uma entidade. E todas essas propriedades serão refletidas na Entidade que incorpora
// esta entidade
public class Endereco {
	
	@Column(name = "endereco_cep")
	private String cep;
	
	@Column(name = "endereco_logradouro")
	private String logradouro;
	
	@Column(name = "endereco_numero")
	private String numero;
	
	@Column(name = "endereco_complemento")
	private String complemento;
	
	@Column(name = "endereco_bairro")
	private String bairro;
	
	@ManyToOne(fetch = FetchType.LAZY) // Muitos Endereços podem ter uma Cidade
	@JoinColumn(name = "endereco_cidade_id")
	private Cidade Cidade;
	

}

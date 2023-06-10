	package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data // Representa tudo que está abaixo (Getter, Setter, EqualsAndHashCode )
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Somente se inclúido...
//@Getter
//@Setter // O uso do projeto Lombok é para evitar usar projetos repetitivos como gerar set and get
//@EqualsAndHashCode
@Entity

public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;
	
	//@JsonIgnore// Para não adcionar uma cozinha na representação do recurso
	// O padrão de quem termina com many é eager e quem termina com one é lazy
	//@JsonIgnoreProperties("hibernateLazyInitializaer")// aqui está ignorando algumas propriedades da Cozinha
	@ManyToOne(fetch = FetchType.LAZY) // Muitos restaurantes possuem uma cozinha.
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;
	
	// É para tirar o endereço da implementação no postman, mas no banco de dados continua tendo a
	// a implementação
	@JsonIgnore 
	@Embedded// Esta classe é incorporada à classe Restaurante
	private Endereco endereco;
	@JsonIgnore
	@CreationTimestamp // Aqui abaixo é sem a precisão de milisegundos
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataCadastro;
	@JsonIgnore
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataAtualizacao;
	
	// Vamos agora referenciar na nossa entidade Restaurante, a entidade Produto que criamos no desafio anterior
	// Como um Restaurante pode ter vários produtos, o relacionamento aqui é Um para Muitos.
	// Adicionamos também a anotação @JsonIgnore, para evitar referencia circular, já que esse relacionamento é bi-direcional
	@JsonIgnore
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();
	
	
	@JsonIgnore // Nesse momento não é intessante mostrar tudo(formas de pagamento)
	//no Payload, mais a frente vamos ver outra forma de customizar
	@ManyToMany(fetch = FetchType.EAGER)// Muito restaurantes podem ter muitas formas de pagamento!!!
	@JoinTable(name = "restaurante_forma_pagamento",
	joinColumns = @JoinColumn(name = "restaurante_id"),
	inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private List<FormaPagamento> formasPagamento = new ArrayList<>();

	
	
	
	
}

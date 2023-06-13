	package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;

import java.time.OffsetDateTime;
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
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.algaworks.algafood.core.validation.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.Data;
import lombok.EqualsAndHashCode;


//@ValorZeroIncluiDescricao(valorField = "taxaFrete", 
//descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
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
	
	//@NotNull // Não Nulo
	//@NotEmpty// Não vazio
	// CadastroRestaurante é só pra validar quem tem esse essa anotação
	@NotBlank // Não pode ter espaços em branco
	@Column(nullable = false)
	private String nome;
	
	//@NotNull 
	//@DecimalMin("1")
	//@PositiveOrZero // É para dizer que um numero tem que ser positivo ou zero... é o mesmo efeito da anotação acima
	//@Multiplo(numero = 5)
	//@TaxaFrete
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;
	
	// Aqui abaixo estamos ignorando o nome mas estamos concedendo a serialização, ou seja
	// o nome aparecerá como objeto json na consulta.
	
	//@Valid // Aqui permite validar em cascata!!! Validando as propriedades de cozinha
	//@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
	//@NotNull
	//@JsonIgnore// Para não adcionar uma cozinha na representação do recurso
	// O padrão de quem termina com many é eager e quem termina com one é lazy
	//@JsonIgnoreProperties("hibernateLazyInitializaer")// aqui está ignorando algumas propriedades da Cozinha
	// Aqui abaixo se for lazy não vai carregar o objeto na busca e vai dar exceção quando usarmos aloowgetters.
	@ManyToOne //(fetch = FetchType.LAZY) // Muitos restaurantes possuem uma cozinha.
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;
	
	// É para tirar o endereço da implementação no postman, mas no banco de dados continua tendo a
	// a implementação


	@Embedded// Esta classe é incorporada à classe Restaurante
	private Endereco endereco;
	
	@CreationTimestamp // Aqui abaixo é sem a precisão de milisegundos
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;
	
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime  dataAtualizacao;
	
	private Boolean ativo = Boolean.TRUE;
	
	// Vamos agora referenciar na nossa entidade Restaurante, a entidade Produto que criamos no desafio anterior
	// Como um Restaurante pode ter vários produtos, o relacionamento aqui é Um para Muitos.
	// Adicionamos também a anotação @JsonIgnore, para evitar referencia circular, já que esse relacionamento é bi-direcional
	
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();
	
	
	// Nesse momento não é intessante mostrar tudo(formas de pagamento)
	//no Payload, mais a frente vamos ver outra forma de customizar
	@ManyToMany //(fetch = FetchType.EAGER)// Muito restaurantes podem ter muitas formas de pagamento!!!
	@JoinTable(name = "restaurante_forma_pagamento",
	joinColumns = @JoinColumn(name = "restaurante_id"),
	inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private List<FormaPagamento> formasPagamento = new ArrayList<>();

	public void ativar() {
		setAtivo(true);
	}
	
	public void inativar() {
		setAtivo(false);
	}
	
	
	
}

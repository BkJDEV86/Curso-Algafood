package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;
import lombok.EqualsAndHashCode;


@JsonRootName("cozinha") //Aqui é para mudar o nome do Root, mas não é muito interessante ás vezes
@Data // Representa tudo que está abaixo (Getter, Setter, EqualsAndHashCode )
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Somente se inclúido...
//@Getter
//@Setter // O uso do projeto Lombok é para evitar usar projetos repetitivos como gerar set and get
//@EqualsAndHashCode
@Entity
public class Cozinha {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@JsonIgnore // ignora propriedades da resposta da requisição
	//@JsonProperty("titulo") // Altera o nome da propriedade da resposta da requisição
	@Column(nullable = false)
	private String nome;

	

	
	
	
	

}

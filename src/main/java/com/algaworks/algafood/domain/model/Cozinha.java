package com.algaworks.algafood.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

import com.algaworks.algafood.core.validation.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;



@Data // Representa tudo que está abaixo (Getter, Setter, EqualsAndHashCode )
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Somente se inclúido...
//@Getter
//@Setter // O uso do projeto Lombok é para evitar usar projetos repetitivos como gerar set and get
//@EqualsAndHashCode
@Entity
public class Cozinha {
	
	//@NotNull(groups = Groups.CozinhaId.class)
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	//@JsonIgnore // ignora propriedades da resposta da requisição
	//@JsonProperty("titulo") // Altera o nome da propriedade da resposta da requisição
	
	@Column(nullable = false)
	private String nome;
	
	
	@OneToMany(mappedBy = "cozinha")// Aqui uma coinhz pode ter vários restaurantes!!!	
	private List<Restaurante> restaurantes = new ArrayList<>();

	

	
	
	
	

}

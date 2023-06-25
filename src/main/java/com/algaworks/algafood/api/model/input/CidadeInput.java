package com.algaworks.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Import;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;

@Setter
@Getter
public class CidadeInput {

	@ApiModelProperty(example = "Uberlândia", required = true)
    @NotBlank
    private String nome;
    
    @Valid
    @NotNull// foi colocada uma anotação @Import(BeanValidatorPluginsConfiguration.class) na classe SpringFoxConfig
    // Essa anotação aproveita o notNull tornando obrigatorio na documentação um campo não vaizo.
    private EstadoIdInput estado;
    
}
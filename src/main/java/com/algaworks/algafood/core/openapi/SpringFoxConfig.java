package com.algaworks.algafood.core.openapi;


import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.openapi.model.CozinhasModelOpenApi;
import com.algaworks.algafood.api.openapi.model.PageableModelOpenApi;
import com.algaworks.algafood.api.openapi.model.PedidosResumoModelOpenApi;
import com.algaworks.algafood.domain.model.PedidoResumoModel;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;



@Configuration
@Import(BeanValidatorPluginsConfiguration.class)// Importação de uma classe de configuração.
public class SpringFoxConfig   {

  @Bean// Estamos instanciando um sumário para especificar um conjunto de serviços que devem ser
  // documentados.
  public Docket apiDocket() {
	  
	  var typeResolver = new TypeResolver();
	  
    return new Docket(DocumentationType.OAS_30)
    		// estamos selceionando os endpoints que devem ser escaneados.
        .select()// Aqui abaixo queremos que apareca somente os controladores da algaffod e não aquele padrão
        // que vem no swagger que aparece o base-error-controller.
        .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
        .paths(PathSelectors.any())
        //.path(PathSelectors.ant("/restaurantes/*)) // Aqui é quando queremos definir manualmente os paths que gostaríamos
          // que aparecesse.
        .build()
        .useDefaultResponseMessages(false)// Vai desabilitar os cógigos de status 400 gerados automaticamente no Swagger Ui
        .globalResponses(HttpMethod.GET, globalGetResponseMessages())
        .globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
        .globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
        .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
			/*
			 * .globalRequestParameters(Collections.singletonList( new
			 * RequestParameterBuilder() .name("campos")
			 * .description("Nomes das propriedades para filtrar na resposta, separados por vírgula"
			 * ) .in(ParameterType.QUERY) .required(true) .query(q -> q.model(m ->
			 * m.scalarModel(ScalarType.STRING))) .build()) )// Configuração global que vai
			 * ser substituída pela específica.*/
		.additionalModels(typeResolver.resolve(Problem.class))// Para adicionar o Problem no swagger.
        .ignoredParameterTypes(ServletWebRequest.class )// Isso é para ignorar outros parâmetros da requisição e retornar só o Id.
        .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)// Aqui está havendo uma substituição de classes.
        .alternateTypeRules(AlternateTypeRules.newRule(
				typeResolver.resolve(Page.class, CozinhaModel.class), // Não usamos o metodo acima pois a classe que vai ser
				// substituida é uma classe com generics.
				CozinhasModelOpenApi.class))
        .alternateTypeRules(AlternateTypeRules.newRule(
                typeResolver.resolve(Page.class, PedidoResumoModel.class),
                PedidosResumoModelOpenApi.class))
        .apiInfo(apiInfo())
        .tags(new Tag("Cidades", "Gerencia as cidades"),
                new Tag("Grupos", "Gerencia os grupos de usuários"),
                new Tag("Cozinhas", "Gerencia as cozinhas"),
                new Tag("Formas de pagamento", "Gerencia as formas de pagamento"),
                new Tag("Pedidos", "Gerencia os pedidos"),
                new Tag("Restaurantes", "Gerencia os restaurantes"),
                new Tag("Estados", "Gerencia os estados"),
                new Tag("Produtos", "Gerencia os produtos de restaurantes"));
}
  
  private List<Response> globalGetResponseMessages() {
	  return Arrays.asList(
	      new ResponseBuilder()
	          .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
	          .description("Erro interno do Servidor") // cod 500
	          .representation( MediaType.APPLICATION_JSON )
              .apply(getProblemaModelReference())// configuração nas respostas globais,
	          .build(),
	      new ResponseBuilder()
	          .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value())) // cod 406
	          .description("Recurso não possui representação que pode ser aceita pelo consumidor")
						/*   Não retorna o problema no corpo por isso não passamos, como não é aceita a media type não tem corpo
						 * .representation( MediaType.APPLICATION_JSON )
						 * .apply(getProblemaModelReference())
						 */
	          .build()
	  );
	}
  
  private List<Response> globalPostPutResponseMessages() {
	    return Arrays.asList(
	        new ResponseBuilder()
	            .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
	            .description("Requisição inválida (erro do cliente)")
	            .representation( MediaType.APPLICATION_JSON )
                .apply(getProblemaModelReference())
	            .build(),
	        new ResponseBuilder()
	            .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
	            .description("Erro interno no servidor")
	            .representation( MediaType.APPLICATION_JSON )
                .apply(getProblemaModelReference())
	            .build(),
	        new ResponseBuilder()
	            .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
	            .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
						/*
						 * .representation( MediaType.APPLICATION_JSON )
						 * .apply(getProblemaModelReference())
						 */
	            .build(),
	        new ResponseBuilder()
	            .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
	            .description("Requisição recusada porque o corpo está em um formato não suportado")
	            .representation( MediaType.APPLICATION_JSON )
                .apply(getProblemaModelReference())
	            .build()
	    );
	  }
  
  private List<Response> globalDeleteResponseMessages() {
	    return Arrays.asList(
	        new ResponseBuilder()
	            .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
	            .description("Requisição inválida (erro do cliente)")
	            .representation( MediaType.APPLICATION_JSON )
                .apply(getProblemaModelReference())
	            .build(),
	        new ResponseBuilder()
	            .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
	            .description("Erro interno no servidor")
	            .representation( MediaType.APPLICATION_JSON )
                .apply(getProblemaModelReference())
	            .build()
	    );
	  }

  // Essa classe é para modificar as informações que aparecem no cabeçalho da API.
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("AlgaFood API")
				.description("API aberta para clientes e restaurantes")
				.version("1")
				.contact(new Contact("AlgaWorks", "https://www.algaworks.com", "contato@algaworks.com"))
				.build();
	}

	//Em seguida crie um Bean do tipo JacksonModuleRegistrar na sua classe SpringFoxConfig, para fazer com que o SpringFox carregue
	//o módulo de conversão de datas:
	@Bean
	public JacksonModuleRegistrar springFoxJacksonConfig() {
		return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
	}
	
	private Consumer<RepresentationBuilder> getProblemaModelReference() {
	    return r -> r.model(m -> m.name("Problema")
	            .referenceModel(ref -> ref.key(k -> k.qualifiedModelName(
	                    q -> q.name("Problema").namespace("com.algaworks.algafood.api.exceptionhandler")))));
	}
  
}

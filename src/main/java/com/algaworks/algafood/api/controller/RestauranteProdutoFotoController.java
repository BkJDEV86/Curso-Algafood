package com.algaworks.algafood.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.api.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.api.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;

//@PathVariable – define o recebimento de parâmetros de uma requisição.
	// @RequestParam – com essa anotação, podemos acessar parâmetros da solicitação HTTP.
	// São vários parametros passados por url mas que não são parte da url em sí.

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi  {

	@Autowired
	private CadastroProdutoService cadastroProduto;
	
	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProduto;
	
	@Autowired
	private FotoStorageService fotoStorage;
	
	@Autowired
	private FotoProdutoModelAssembler fotoProdutoModelAssembler;
	
	// O multipartfile é para trazer o content type.
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId,
			@PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput, @RequestPart(required = true) MultipartFile arquivo)
	 throws IOException {
		Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
		
		//MultipartFile arquivo = fotoProdutoInput.getArquivo();
		
		FotoProduto foto = new FotoProduto();
		foto.setProduto(produto);
		foto.setDescricao(fotoProdutoInput.getDescricao());
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(arquivo.getSize());
		foto.setNomeArquivo(arquivo.getOriginalFilename());
		
		FotoProduto fotoSalva = catalogoFotoProduto.salvar(foto, arquivo.getInputStream());
		
		return fotoProdutoModelAssembler.toModel(fotoSalva);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoModel buscar(@PathVariable Long restauranteId, 
	        @PathVariable Long produtoId) {
	    FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);
	    
	    return fotoProdutoModelAssembler.toModel(fotoProduto);
	}
	
	@GetMapping(produces = MediaType.ALL_VALUE)
	public ResponseEntity<InputStreamResource> servir(@PathVariable Long restauranteId, 
			@PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHeader) 
					throws HttpMediaTypeNotAcceptableException {
		try {
			FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);
			
			// Aqui abaixo está pegando o contéudo da mídia que é aceita.
			MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
			
			// o acceptHeader é o que é aceito na requisição.
			List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);
			
			verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);
			
			InputStream inputStream = fotoStorage.recuperar(fotoProduto.getNomeArquivo());
			
			return ResponseEntity.ok()
					.contentType(mediaTypeFoto)
					.body(new InputStreamResource(inputStream));
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}
	

    private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, 
		List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {
	
	boolean compativel = mediaTypesAceitas.stream()
			.anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));
	
	if (!compativel) {
		throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
	}
}
    
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long restauranteId, 
            @PathVariable Long produtoId) {
        catalogoFotoProduto.excluir(restauranteId, produtoId);
    }   
	
}
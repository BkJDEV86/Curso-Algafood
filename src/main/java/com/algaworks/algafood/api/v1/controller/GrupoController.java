package com.algaworks.algafood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.api.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafood.api.v1.assembler.GrupoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;



/*Ao adicionar o produces = MediaType.APPLICATION_JSON_VALUE na Annotation @RequestMapping nos Controllers de Grupo ou Cidade,
 *  o SpringFox 3 nos apresentará um NullPointerException. Isso é um problema conhecido que ainda não foi corrigido nessa biblioteca.
 *   Acontece devido a alguns métodos das Controllers terem o retorno void, como os de DELETE, que somado ao 
 *   produces = MediaType.APPLICATION_JSON_VALUE gera um NullPointerException.*/

/*Sendo assim, é necessário adicionar o produces em cada um dos métodos, com exceção daqueles que retornam void, 
 * ao invés de adicioná-los na Controller.*/

@RestController
@RequestMapping("/grupos")
public class GrupoController implements  GrupoControllerOpenApi {

    @Autowired
    private GrupoRepository grupoRepository;
    
    @Autowired
    private CadastroGrupoService cadastroGrupo;
    
    @Autowired
    private GrupoModelAssembler grupoModelAssembler;
    
    @Autowired
    private GrupoInputDisassembler grupoInputDisassembler;
    
    @Override
    @GetMapping
    public CollectionModel<GrupoModel> listar() {
        List<Grupo> todosGrupos = grupoRepository.findAll();
        
        return grupoModelAssembler.toCollectionModel(todosGrupos);
    }
    

    @GetMapping(path = "/{grupoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GrupoModel buscar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
        
        return grupoModelAssembler.toModel(grupo);
    }
    
    @PostMapping(MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);
        
        grupo = cadastroGrupo.salvar(grupo);
        
        return grupoModelAssembler.toModel(grupo);
    }
    
    @PutMapping(path = "/{grupoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GrupoModel atualizar(@PathVariable Long grupoId,
            @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupoAtual = cadastroGrupo.buscarOuFalhar(grupoId);
        
        grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);
        
        grupoAtual = cadastroGrupo.salvar(grupoAtual);
        
        return grupoModelAssembler.toModel(grupoAtual);
    }
    
    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long grupoId) {
        cadastroGrupo.excluir(grupoId);	
    }   
} 
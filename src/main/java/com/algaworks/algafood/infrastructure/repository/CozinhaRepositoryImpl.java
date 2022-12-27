package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.repository.CozinhaRepository;

@Component
public class CozinhaRepositoryImpl implements CozinhaRepository{

	@PersistenceContext
	private EntityManager manager;
	
	// Abaixo uma JPQL consultando objetos e não tabelas e como queremos todos os dados colocamos from
	@Override
	public List<Cozinha> listar() {
		return manager.createQuery("from Cozinha", Cozinha.class).getResultList();
	}
	
	@Override
	public Cozinha buscar(Long id) {
		return manager.find(Cozinha.class, id);
	}
	
	@org.springframework.transaction.annotation.Transactional
	// É preciso colocar esse método para que ele seja executado dentro de uma transação!
	// O método salvar é utilizado tamto para adicionar quanto para salvar
	@Override
	public Cozinha salvar(Cozinha cozinha) {
		return manager.merge(cozinha);
	}
	
	@Transactional // Para remover temos que tirar primeiro do estado transiente , ir para o gerenciado
	// no método buscar... para depois remover.
	@Override
	public void remover(Cozinha cozinha) {
		cozinha = buscar(cozinha.getId());
		manager.remove(cozinha);
	}

}
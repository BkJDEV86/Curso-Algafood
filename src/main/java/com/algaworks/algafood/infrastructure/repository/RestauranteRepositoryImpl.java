package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import com.algaworks.algafood.domain.model.Restaurante;

import com.algaworks.algafood.repository.RestauranteRepository;

@Component
public class RestauranteRepositoryImpl implements RestauranteRepository {

	@PersistenceContext
	private EntityManager manager;
	
	// Abaixo uma JPQL consultando objetos e não tabelas e como queremos todos os dados colocamos from
	public List<Restaurante> listar() {
		return manager.createQuery("from Restaurante", Restaurante.class).getResultList();
	}
	
	@Override
	public Restaurante buscar(Long id) {
		return manager.find(Restaurante.class, id);
	}
	
	@org.springframework.transaction.annotation.Transactional
	// É preciso colocar esse método para que ele seja executado dentro de uma transação!
	// O método salvar é utilizado tamto para adicionar quanto para salvar
	
	public Restaurante salvar(Restaurante restaurante) {
		return manager.merge(restaurante);
	}
	
	@Transactional // Para remover temos que tirar primeiro do estado transiente , ir para o gerenciado
	// no método buscar... para depois remover.
	
	public void remover(Restaurante restaurante) {
		restaurante = buscar(restaurante.getId());
		manager.remove(restaurante);
	}


}

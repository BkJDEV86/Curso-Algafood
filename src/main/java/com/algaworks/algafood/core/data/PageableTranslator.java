package com.algaworks.algafood.core.data;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableTranslator {

	public static Pageable translate(Pageable pageable, Map<String, String> fieldsMapping) {
		var orders = pageable.getSort().stream()
			.filter(order -> fieldsMapping.containsKey(order.getProperty()))// Tem que filtrar antes pra não aparecer nada nulo.
			.map(order -> new Sort.Order(order.getDirection(), 
					fieldsMapping.get(order.getProperty())))
			.collect(Collectors.toList());// Retorna uma lista de sort.order
							
		return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
				Sort.by(orders));
	}
	
}
package com.algaworks.algafood.core.jackson;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent// Criamos um serializador json para um classe page customizando o payload.
public class PageJsonSerializer extends JsonSerializer<Page<?>> {

	@Override
	public void serialize(Page<?> page, JsonGenerator gen, 
			SerializerProvider serializers) throws IOException {
		
		gen.writeStartObject();
		
		// aqui abaixo está extraindo o conteudo de page e colocando em content
		gen.writeObjectField("content", page.getContent());
		gen.writeNumberField("size", page.getSize());
		gen.writeNumberField("totalElements", page.getTotalElements());
		gen.writeNumberField("totalPages", page.getTotalPages());
		gen.writeNumberField("number", page.getNumber());
		
		gen.writeEndObject();
	}

}
package com.algaworks.algafood.infrastructure.service.storage;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.algaworks.algafood.domain.service.FotoStorageService;

@Service
public class LocalFotoStorageService implements FotoStorageService {

	@Value("${algafood.storage.local.diretorio-fotos}")
	private Path diretorioFotos;
	
	//O OutputStream é usado para enviar dados do cliente ao servidor, enquanto 
	//o InputStream é usado para ler os dados que chegaram no servidor.
	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			Path arquivoPath = getArquivoPath(novaFoto.getNomeAquivo());
			
			FileCopyUtils.copy(novaFoto.getInputStream(), 
					Files.newOutputStream(arquivoPath));
		} catch (Exception e) {
			throw new StorageException("Não foi possível armazenar arquivo.", e);
		}
	}
	
	// Resolve retorna o caminho completo do path
	private Path getArquivoPath(String nomeArquivo) {
		return diretorioFotos.resolve(Path.of(nomeArquivo));
	
	}

	@Override
	public void remover(String nomeArquivo) {
		try {
			Path arquivoPath = getArquivoPath(nomeArquivo);
			
			Files.deleteIfExists(arquivoPath);
		} catch (Exception e) {
			throw new StorageException("Não foi possível excluir arquivo.", e);
		}
	}

	@Override
	public InputStream recuperar(String nomeArquivo) {
	    try {
	        Path arquivoPath = getArquivoPath(nomeArquivo);

	        return Files.newInputStream(arquivoPath);
	    } catch (Exception e) {
	        throw new StorageException("Não foi possível recuperar arquivo.", e);
	    }
	}     

}
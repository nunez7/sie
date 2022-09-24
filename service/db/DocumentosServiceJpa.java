package edu.mx.utdelacosta.service.db;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Documento;
import edu.mx.utdelacosta.repository.DocumentosRepository;
import edu.mx.utdelacosta.service.IDocumentosService;

@Service
public class DocumentosServiceJpa implements IDocumentosService{
	
	@Autowired
	private DocumentosRepository documentoRepoitory;

	@Override
	@Transactional(readOnly = true)
	public Documento buscarPorId(Integer id) {
		Optional<Documento> optional = documentoRepoitory.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Documento buscarPorNombre(String nombre) {
		return documentoRepoitory.findByNombre(nombre);
	}

}

package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Documento;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.PersonaDocumento;
import edu.mx.utdelacosta.model.dto.DocumentoDTO;
import edu.mx.utdelacosta.repository.PersonaDocumentoRepository;
import edu.mx.utdelacosta.service.IPersonaDocumentoService;

@Service
public class PersonaDocumentoServiceJpa implements IPersonaDocumentoService{
	
	@Autowired
	private PersonaDocumentoRepository perDocRepo;
	
	@Override
	public PersonaDocumento buscarPorPersonaYdocumento(Persona persona, Documento documento) {
		// TODO Auto-generated method stub
		return perDocRepo.findByPersonaAndDocumento(persona, documento);
	}
	
	@Override
	public List<PersonaDocumento> buscarActaCurpCerbachiPorPersona(Integer idPersona) {
		// TODO Auto-generated method stub
		return perDocRepo.findActaCurpCerbachiByPersona(idPersona);
	}
	
	@Override
	public List<DocumentoDTO> buscarActaCurpCerbachiPorPersonaParaDto(Integer idPersona) {
		// TODO Auto-generated method stub
		return perDocRepo.findActaCurpCerbachiByPersonaDto(idPersona);
	}
	
	@Override
	public List<PersonaDocumento> buscarPorPersona(Persona persona) {
		// TODO Auto-generated method stub
		return perDocRepo.findByPersona(persona);
	}
	
	@Override
	public void guardar(PersonaDocumento personaDocumento) {
		// TODO Auto-generated method stub
		perDocRepo.save(personaDocumento);
	}

	@Override
	public PersonaDocumento buscarPorId(Integer idPerDoc) {
		// TODO Auto-generated method stub
		Optional<PersonaDocumento> optional = perDocRepo.findById(idPerDoc);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public PersonaDocumento buscarPorPersonaYIdDoc(Integer idPeronsa, int idDoc) {
		// TODO Auto-generated method stub
		return perDocRepo.findPersonaDocByPersonaAndIdDoc(idPeronsa, idDoc);
	}

	@Override
	public Integer documentosValidosPorPersona(Integer idPersona) {
		// TODO Auto-generated method stub
		return perDocRepo.findPersonaDocValidadoByPersona(idPersona);
	}
	
	@Override
	public List<DocumentoDTO> buscarPrestadosPorPersona(Integer idPersona) {
		// TODO Auto-generated method stub
		return perDocRepo.findDocumentosPrestadoByPersona(idPersona);
	}

}

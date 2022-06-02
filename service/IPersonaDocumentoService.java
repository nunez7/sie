package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Documento;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.PersonaDocumento;
import edu.mx.utdelacosta.model.dto.DocumentoDTO;

public interface IPersonaDocumentoService {
	PersonaDocumento buscarPorPersonaYdocumento(Persona persona, Documento documento);
	PersonaDocumento buscarPorPersonaYIdDoc(Integer idPeronsa, int idDoc);
	PersonaDocumento buscarPorId(Integer idPerDoc);
	
	List<DocumentoDTO> buscarActaCurpCerbachiPorPersonaParaDto(Integer idPersona);
	List<PersonaDocumento> buscarPorPersona(Persona persona);
	List<PersonaDocumento> buscarActaCurpCerbachiPorPersona(Integer idPersona);
	
	void guardar(PersonaDocumento  personaDocumento);
	Integer documentosValidosPorPersona(Integer idPersona);
	
	List<DocumentoDTO> buscarPrestadosPorPersona(Integer idPersona);
}

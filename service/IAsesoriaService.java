package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Asesoria;
import edu.mx.utdelacosta.model.dto.AsesoriaDTO;

public interface IAsesoriaService {
	void guardar (Asesoria asesoria);
	List<AsesoriaDTO> buscarPorIdGrupo(Integer idGrupo);
	List<AsesoriaDTO> buscarPorPersonaCarrera(Integer idPersona);
}

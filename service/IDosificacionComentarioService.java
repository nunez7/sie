package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.DosificacionComentario;

public interface IDosificacionComentarioService {
	List<DosificacionComentario> buscarPorIdPersona(Integer idPersona);
	List<DosificacionComentario> buscarPorIdCargaHoraria(Integer idCargaHoraria);
	void guardar(DosificacionComentario dosificacionComentario);
}

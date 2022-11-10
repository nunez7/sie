package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.MotivoTutoria;

public interface IMotivoTutoriaService {
	void guardar(MotivoTutoria motivoTutoria);
	void eliminar(Integer idMotivo);
	List<MotivoTutoria> findByIdTutoria(Integer id);
}

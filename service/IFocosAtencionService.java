package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.FocosAtencion;
import edu.mx.utdelacosta.model.Grupo;

public interface IFocosAtencionService {
	void guardar(FocosAtencion focosAtencion);
	void eliminar(FocosAtencion focosAtencion);
	List<FocosAtencion> buscarPorGrupo(Grupo grupo);
}

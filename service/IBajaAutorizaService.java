package edu.mx.utdelacosta.service;

import edu.mx.utdelacosta.model.Baja;
import edu.mx.utdelacosta.model.BajaAutoriza;

public interface IBajaAutorizaService {
	void guardar(BajaAutoriza bajaAutorizada);
	BajaAutoriza buscarPorId(Integer id);
	BajaAutoriza buscarPorBaja(Baja baja);
	void eliminar(BajaAutoriza bajaAutorizada);
}

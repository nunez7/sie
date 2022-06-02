package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Area;

public interface IAreaService {
	void guardar(Area area);
	List<Area> buscarTodas();
	Area buscarPorId(Integer id);
}

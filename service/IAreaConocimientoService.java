package edu.mx.utdelacosta.service;

import java.util.List;
import edu.mx.utdelacosta.model.AreaConocimiento;

public interface IAreaConocimientoService {
	
	List<AreaConocimiento> buscarAreasActivas();
	AreaConocimiento buscarPorId(Integer id);

}

package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.DosificacionImportada;
import edu.mx.utdelacosta.model.dto.DosificacionImportarDTO;

public interface IDosificacionImportadaService {
	public void guardar(DosificacionImportada dosiImportada);
	
	List<DosificacionImportarDTO> buscarImportarPorMateriaYPeriodo(Integer idMateria, Integer idCarga, Integer idPeriodo);
	
}

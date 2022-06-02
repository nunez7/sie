package edu.mx.utdelacosta.service;


import java.util.List;
import edu.mx.utdelacosta.model.PrestamoDocumento;

public interface IPrestamoDocumentoService {
	
	public void guardar(PrestamoDocumento prestamoDocumento);
	
	List<PrestamoDocumento> buscarPorAlumno(Integer idAlumno);
	
	PrestamoDocumento buscarPorId(Integer id);

}

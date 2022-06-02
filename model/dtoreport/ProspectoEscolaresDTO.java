package edu.mx.utdelacosta.model.dtoreport;

import java.util.Date;

public interface ProspectoEscolaresDTO {
	
	Integer getIdAlumno();

	String getMatricula();
	
	String getNombreCompleto();
	
	String getCarrera();
	
	Integer getEntregoDocumentos();
	
	Integer getCeneval();
	
	Integer getPago();
	
	String getEmail();
	
	String getCelular();
	
	Date getFechaRegistro();
	
	String getCurp();
	
	Double getPromedio();
	
	String getEstadoNacimiento();
	
	String getNombreBachillerato();
	
	String getEstadoBachillerato();
	
	String getMunicipioBachillerato();
	
	String getLocalidadBachillerato();

}

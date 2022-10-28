package edu.mx.utdelacosta.model.dtoreport;

public interface AlumnoRegularDTO {

	Integer getIdAlumno();

	String getMatricula();

	String getGrupoAnterior();
	
	String getGrupoActual();
	
	Integer getIdPersona();
	
	String getNombre();
	
	String getPrimerApellido();
	
	String getSegundoApellido();
	
	String getCarrera();
	
	String getSiglasCarrera();
	
	String getSexo();
	
	Boolean getIndigena();
	
	Boolean getDiscapacitado();
	
	String getCurp();
	
	String getCuatrimestre();
	
	String getCorreo();
}

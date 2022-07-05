package edu.mx.utdelacosta.model.dto;

public interface AlumnoPersonalDTO {
	//se usa para buscar alumno o personal para pago persona en modulo caja
	Integer getIdAlumno();
	
	Integer getIdEmpleado();
	
	Integer getIdPersona();
	
	//será interpretado como matrícula de alumno o noEmpleado
	String getMatricula();
	
	String getNombre();
	
	//para saber si es alumno o empleado a travez de los id 
	Integer getTipo();
}

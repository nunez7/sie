package edu.mx.utdelacosta.model.dto;

public interface AlumnoActivoDTO {
	
	//DTO usado para traer informacion de los alumnos en el panel del profesor/tutor
	Integer getIdAlumno();
	
	String getNombre();
	
	String getMatricula();
	
	String getGrupo();
	
	Boolean getActivo();
	
	Boolean getEstatus();
	
	String getCorreo();
	
	String getTelefono();

}

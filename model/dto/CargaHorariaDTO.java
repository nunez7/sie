package edu.mx.utdelacosta.model.dto;

import java.io.Serializable;


public class CargaHorariaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer idCargaHoraria;
	private Integer idProfesor;
	private Integer idMateria;
	
	public CargaHorariaDTO() {

	}
	
	public CargaHorariaDTO(Integer idCargaHoraria, Integer idProfesor, Integer idMateria) {
		this.idCargaHoraria = idCargaHoraria;
		this.idProfesor = idProfesor;
		this.idMateria = idMateria;
	}

	public Integer getIdCargaHoraria() {
		return idCargaHoraria;
	}
	public void setIdCargaHoraria(Integer idCargaHoraria) {
		this.idCargaHoraria = idCargaHoraria;
	}
	public Integer getIdProfesor() {
		return idProfesor;
	}
	public void setIdProfesor(Integer idProfesor) {
		this.idProfesor = idProfesor;
	}
	public Integer getIdMateria() {
		return idMateria;
	}
	public void setIdMateria(Integer idMateria) {
		this.idMateria = idMateria;
	}
	
}

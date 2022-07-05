package edu.mx.utdelacosta.model.dtoreport;

import java.util.List;

public class MateriaAsistenciaDTO {
	
	private Integer idMateria;
	private String materia;
	private Integer totalAsistencias;
	private Integer totalFaltas;
	//se añade unalista de asistencia corte
	private List<AsistenciasCorteDTO> cortes;
	
	public Integer getIdMateria() {
		return idMateria;
	}
	public void setIdMateria(Integer idMateria) {
		this.idMateria = idMateria;
	}
	public String getMateria() {
		return materia;
	}
	public void setMateria(String materia) {
		this.materia = materia;
	}
	public Integer getTotalAsistencias() {
		return totalAsistencias;
	}
	public void setTotalAsistencias(Integer totalAsistencias) {
		this.totalAsistencias = totalAsistencias;
	}
	public Integer getTotalFaltas() {
		return totalFaltas;
	}
	public void setTotalFaltas(Integer totalFaltas) {
		this.totalFaltas = totalFaltas;
	}
	public List<AsistenciasCorteDTO> getCortes() {
		return cortes;
	}
	public void setCortes(List<AsistenciasCorteDTO> cortes) {
		this.cortes = cortes;
	}
}

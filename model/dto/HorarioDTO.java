package edu.mx.utdelacosta.model.dto;

public class HorarioDTO {
	private Integer idHorario;

	private String horaInicio;

	private String horaFin;

	private String dia;

	private String profesor;

	private String materia;

	private String abreviaturaMateria;

	// cambios de horario DTO
	private String grupo;

	private Integer idCargaHoraria;

	private Integer idActividad;

	private Integer idDia;

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getProfesor() {
		return profesor;
	}

	public void setProfesor(String profesor) {
		this.profesor = profesor;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public String getAbreviaturaMateria() {
		return abreviaturaMateria;
	}

	public void setAbreviaturaMateria(String abreviaturaMateria) {
		this.abreviaturaMateria = abreviaturaMateria;
	}

	public Integer getIdHorario() {
		return idHorario;
	}

	public void setIdHorario(Integer idHorario) {
		this.idHorario = idHorario;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public Integer getIdCargaHoraria() {
		return idCargaHoraria;
	}

	public void setIdCargaHoraria(Integer idCargaHoraria) {
		this.idCargaHoraria = idCargaHoraria;
	}

	public Integer getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(Integer idActividad) {
		this.idActividad = idActividad;
	}

	public Integer getIdDia() {
		return idDia;
	}

	public void setIdDia(Integer idDia) {
		this.idDia = idDia;
	}

}

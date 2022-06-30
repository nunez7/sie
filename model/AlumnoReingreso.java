package edu.mx.utdelacosta.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "alumno_reingreso")
public class AlumnoReingreso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name="id_alumno")
	private Alumno alumno;
	
	@OneToOne
	@JoinColumn(name = "ultimo_periodo")
	private Periodo ultimoPeriodo;
	
	@OneToOne
	@JoinColumn(name="periodo_reingreso")
	private Periodo periodoReingreso;
	
	@OneToOne
	@JoinColumn(name="id_grupo")
	private Grupo grupo;
	
	private String generacion;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public Periodo getUltimoPeriodo() {
		return ultimoPeriodo;
	}

	public void setUltimoPeriodo(Periodo ultimoPeriodo) {
		this.ultimoPeriodo = ultimoPeriodo;
	}

	public Periodo getPeriodoReingreso() {
		return periodoReingreso;
	}

	public void setPeriodoReingreso(Periodo periodoReingreso) {
		this.periodoReingreso = periodoReingreso;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public String getGeneracion() {
		return generacion;
	}

	public void setGeneracion(String generacion) {
		this.generacion = generacion;
	}
}
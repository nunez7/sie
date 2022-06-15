package edu.mx.utdelacosta.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "canalizaciones")
public class Canalizacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_alumno")
	private Alumno alumno;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tutoria_individual")
	private TutoriaIndividual tutoriaIndividual;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_periodo")
	private Periodo periodo;
	
	@Column(name = "fecha_registro")
	private Date fechaRegistro;
	
	@Column(name = "fecha_canalizar")
	private Date fechaCanalizar;
	
	@Column(name = "hora_canalizar")
	private Date horaCanalizar;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_servicio")
	private Servicio servicio;
	
	private String razones;
	
	private String comentarios;
	
	private Integer status;
	
	public Canalizacion() {

	}
	
	public Canalizacion(Integer id) {
		this.id = id;
	}


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

	public TutoriaIndividual getTutoriaIndividual() {
		return tutoriaIndividual;
	}

	public void setTutoriaIndividual(TutoriaIndividual tutoriaIndividual) {
		this.tutoriaIndividual = tutoriaIndividual;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaCanalizar() {
		return fechaCanalizar;
	}

	public void setFechaCanalizar(Date fechaCanalizar) {
		this.fechaCanalizar = fechaCanalizar;
	}

	public Date getHoraCanalizar() {
		return horaCanalizar;
	}

	public void setHoraCanalizar(Date horaCanalizar) {
		this.horaCanalizar = horaCanalizar;
	}

	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	public String getRazones() {
		return razones;
	}

	public void setRazones(String razones) {
		this.razones = razones;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}

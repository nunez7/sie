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
import javax.persistence.Table;

@Entity
@Table(name = "tutoria_individual")
public class TutoriaIndividual {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_alumno")
	private Alumno alumno;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_persona")
	private Persona tutor;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_grupo")
	private Grupo grupo;
	
	@Column(name = "fecha_registro")
	private Date fechaRegistro;
	
	@Column(name = "fecha_tutoria")
	private Date fechaTutoria;
	
	@Column(name = "hora_inicio")
	private Date horaInicio;
	
	@Column(name = "hora_fin")
	private Date horaFin;
	
	@Column(name = "puntos_importantes")
	private String puntosImportantes;
	
	@Column(name = "compromisos_acuerdos")
	private String compromisosAcuerdos;
	
	@Column(name = "nivel_atencion")
	private String nivelAtencion;
	
	private Boolean validada;

	public TutoriaIndividual() {

	}
	
	public TutoriaIndividual(Integer id) {
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
	
	public Persona getTutor() {
		return tutor;
	}

	public void setTutor(Persona tutor) {
		this.tutor = tutor;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaTutoria() {
		return fechaTutoria;
	}

	public void setFechaTutoria(Date fechaTutoria) {
		this.fechaTutoria = fechaTutoria;
	}

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(Date horaFin) {
		this.horaFin = horaFin;
	}

	public String getPuntosImportantes() {
		return puntosImportantes;
	}

	public void setPuntosImportantes(String puntosImportantes) {
		this.puntosImportantes = puntosImportantes;
	}

	public String getCompromisosAcuerdos() {
		return compromisosAcuerdos;
	}

	public void setCompromisosAcuerdos(String compromisosAcuerdos) {
		this.compromisosAcuerdos = compromisosAcuerdos;
	}

	public String getNivelAtencion() {
		return nivelAtencion;
	}

	public void setNivelAtencion(String nivelAtencion) {
		this.nivelAtencion = nivelAtencion;
	}

	public Boolean getValidada() {
		return validada;
	}

	public void setValidada(Boolean validada) {
		this.validada = validada;
	}
}

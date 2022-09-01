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
@Table(name = "bajas")
public class Baja {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_periodo")
	private Periodo periodo; 
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_grupo")
	private Grupo grupo; 
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_persona")
	private Persona persona;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_alumno")
	private Alumno alumno;
	
	@Column(name = "tipo_baja")
	private Integer tipoBaja;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_causa")
	private CausaBaja causaBaja;
	
	@Column(name="otra_causa")
	private String otraCausa;
	
	@Column(name="fecha_asistencia")
	private Date fechaAsistencia;

	@Column(name="fecha_solicitud")
	private Date fechaSolicitud;
	
	@Column(name="fecha_autorizacion")
	private Date fechaAutorizacion;
	
	private String descripcion;
	
	private Integer estatus;
	
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	public Baja() {
		
	}
	
	public Baja(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public Integer getTipoBaja() {
		return tipoBaja;
	}

	public void setTipoBaja(Integer tipoBaja) {
		this.tipoBaja = tipoBaja;
	}

	public CausaBaja getCausaBaja() {
		return causaBaja;
	}

	public void setCausaBaja(CausaBaja causaBaja) {
		this.causaBaja = causaBaja;
	}

	public String getOtraCausa() {
		return otraCausa;
	}

	public void setOtraCausa(String otraCausa) {
		this.otraCausa = otraCausa;
	}

	public Date getFechaAsistencia() {
		return fechaAsistencia;
	}

	public void setFechaAsistencia(Date fechaAsistencia) {
		this.fechaAsistencia = fechaAsistencia;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Date getFechaAutorizacion() {
		return fechaAutorizacion;
	}

	public void setFechaAutorizacion(Date fechaAutorizacion) {
		this.fechaAutorizacion = fechaAutorizacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
}

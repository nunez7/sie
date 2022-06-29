package edu.mx.utdelacosta.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "cortes_evaluativos")
public class CorteEvaluativo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_periodo", referencedColumnName = "id")
	private Periodo periodo;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_carrera", referencedColumnName = "id")
	private Carrera carrera;

	private Integer consecutivo;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "fecha_inicio")
	private Date fechaInicio;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "fecha_fin")
	private Date fechaFin;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "inicio_remedial")
	private Date inicioRemedial;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "fin_remedial")
	private Date finRemedial;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "inicio_extraordinario")
	private Date inicioExtraordinario;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "fin_extraordinario")
	private Date finExtraordinario;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "fecha_asistencia")
	private Date fechaAsistencia;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "inicio_evaluaciones")
	private Date inicioEvaluaciones;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "fin_evaluaciones")
	private Date finEvaluaciones;
	
	@Temporal(TemporalType.DATE) 
	@DateTimeFormat(pattern = "dd/MM/yyyy") 
	@Column(name = "fecha_dosificacion") 
	private Date fechaDosificacion;

	public CorteEvaluativo() {
	}

	public CorteEvaluativo(Integer id) {
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

	public Carrera getCarrera() {
		return carrera;
	}

	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}

	public Integer getConsecutivo() {
		return consecutivo;
	}

	public void setConsecutivo(Integer consecutivo) {
		this.consecutivo = consecutivo;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getInicioRemedial() {
		return inicioRemedial;
	}

	public void setInicioRemedial(Date inicioRemedial) {
		this.inicioRemedial = inicioRemedial;
	}

	public Date getFinRemedial() {
		return finRemedial;
	}

	public void setFinRemedial(Date finRemedial) {
		this.finRemedial = finRemedial;
	}

	public Date getInicioExtraordinario() {
		return inicioExtraordinario;
	}

	public void setInicioExtraordinario(Date inicioExtraordinario) {
		this.inicioExtraordinario = inicioExtraordinario;
	}

	public Date getFinExtraordinario() {
		return finExtraordinario;
	}

	public void setFinExtraordinario(Date finExtraordinario) {
		this.finExtraordinario = finExtraordinario;
	}

	public Date getFechaAsistencia() {
		return fechaAsistencia;
	}

	public void setFechaAsistencia(Date fechaAsistencia) {
		this.fechaAsistencia = fechaAsistencia;
	}

	public Date getInicioEvaluaciones() {
		return inicioEvaluaciones;
	}

	public void setInicioEvaluaciones(Date inicioEvaluaciones) {
		this.inicioEvaluaciones = inicioEvaluaciones;
	}

	public Date getFinEvaluaciones() {
		return finEvaluaciones;
	}

	public void setFinEvaluaciones(Date finEvaluaciones) {
		this.finEvaluaciones = finEvaluaciones;
	}
	
	public Date getFechaDosificacion() {
		return fechaDosificacion;
	}

	public void setFechaDosificacion(Date fechaDosificacion) {
		this.fechaDosificacion = fechaDosificacion;
	}

}

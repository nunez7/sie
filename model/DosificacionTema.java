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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "dosificacion_tema")
public class DosificacionTema {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "id_tema")
	private TemaUnidad tema;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_dosificacion")
	private Dosificacion dosificacion;

	@Column(name = "horas_teoricas")
	private Integer horasTeoricas;
	
	@Column(name = "horas_practicas")
	private Integer horasPracticas;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="dd-MM-yyyy")
	@Column(name = "fehca_inicio")
	private Date fechaInicio;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="dd-MM-yyyy")
	@Column(name = "fecha_fin")
	private Date fechaFin;
	
	public DosificacionTema() {
	}
	
	public DosificacionTema(int id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Dosificacion getDosificacion() {
		return dosificacion;
	}

	public void setDosificacion(Dosificacion dosificacion) {
		this.dosificacion = dosificacion;
	}

	public Integer getHorasTeoricas() {
		return horasTeoricas;
	}

	public void setHorasTeoricas(Integer horasTeoricas) {
		this.horasTeoricas = horasTeoricas;
	}

	public Integer getHorasPracticas() {
		return horasPracticas;
	}

	public void setHorasPracticas(Integer horasPracticas) {
		this.horasPracticas = horasPracticas;
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

	public TemaUnidad getTema() {
		return tema;
	}

	public void setTema(TemaUnidad tema) {
		this.tema = tema;
	}
	
	
	
}

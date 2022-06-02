package edu.mx.utdelacosta.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "estadias")
public class Estadia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name = "id_nivel_estudio", referencedColumnName = "id")
	private NivelEstudio nivelEstudio;
	
	@Column(name="carta_liberacion")
	private Boolean cartaLiberacion;
	
	@Column(name="ruta_carta_liberacion")
	private String rutaCartaLiberacion;
	
	@Column(name="fecha_inicio")
	private Date fechaInicio;
	
	@Column(name="fecha_final")
	private Date fechaFinal;
	
	@Column(name="fecha_liberacion")
	private Date fechaLiberacion;
	
	@ManyToOne
    @JoinColumn(name = "id_alumno")
	private Alumno alumno;
	
	@OneToOne(mappedBy = "estadia", cascade = CascadeType.ALL)
	private Titulacion titulacion;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public NivelEstudio getNivelEstudio() {
		return nivelEstudio;
	}

	public void setNivelEstudio(NivelEstudio nivelEstudio) {
		this.nivelEstudio = nivelEstudio;
	}

	public Boolean getCartaLiberacion() {
		return cartaLiberacion;
	}

	public void setCartaLiberacion(Boolean cartaLiberacion) {
		this.cartaLiberacion = cartaLiberacion;
	}

	public String getRutaCartaLiberacion() {
		return rutaCartaLiberacion;
	}

	public void setRutaCartaLiberacion(String rutaCartaLiberacion) {
		this.rutaCartaLiberacion = rutaCartaLiberacion;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Date getFechaLiberacion() {
		return fechaLiberacion;
	}

	public void setFechaLiberacion(Date fechaLiberacion) {
		this.fechaLiberacion = fechaLiberacion;
	}

	public Titulacion getTitulacion() {
		return titulacion;
	}

	public void setTitulacion(Titulacion titulacion) {
		this.titulacion = titulacion;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}
	
}

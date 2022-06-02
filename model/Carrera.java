package edu.mx.utdelacosta.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "carreras")
public class Carrera {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String clave;
	
	private String codigo;
	
	private String nombre;
	
	@Column(name = "director_carrera")
	private String directorCarrera;
	
	@Column(name = "email_carrera")
	private String emailCarrera;
	
	@OneToOne()
	@JoinColumn(name = "id_nivel_estudio", referencedColumnName = "id")
	private NivelEstudio nivelEstudio;
	
	@Column(name = "created")
	private Date fechaAlta;
		
	private int consecutivo;
	
	@Column(name="nombre_titulacion")
	private String nombreTitulacion;

	public Carrera() {
	}
	
	public Carrera(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDirectorCarrera() {
		return directorCarrera;
	}

	public void setDirectorCarrera(String directorCarrera) {
		this.directorCarrera = directorCarrera;
	}

	public String getEmailCarrera() {
		return emailCarrera;
	}

	public void setEmailCarrera(String emailCarrera) {
		this.emailCarrera = emailCarrera;
	}

	public NivelEstudio getNivelEstudio() {
		return nivelEstudio;
	}

	public void setNivelEstudio(NivelEstudio nivelEstudio) {
		this.nivelEstudio = nivelEstudio;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public int getConsecutivo() {
		return consecutivo;
	}

	public void setConsecutivo(int consecutivo) {
		this.consecutivo = consecutivo;
	}

	public String getNombreTitulacion() {
		return nombreTitulacion;
	}

	public void setNombreTitulacion(String nombreTitulacion) {
		this.nombreTitulacion = nombreTitulacion;
	}
}

package edu.mx.utdelacosta.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "dosificaciones")
public class Dosificacion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "id_corte_evaluativo")
	private Integer idCorteEvaluativo;
	
	@ManyToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "id_persona", referencedColumnName = "id")
	private Persona persona;

	/*
	 * @Column(name = "competencia_desarrollar") private String
	 * competenciaDesarrollar;
	 * 
	 * @Column(name = "actividad_apertura") private String actividadApertura;
	 * 
	 * @Column(name = "actividad_desarrollo") private String actividadDesarrollo;
	 * 
	 * @Column(name = "actividad_cierre") private String actividadCierre;
	 * 
	 * @Column(name = "avance_observaciones") private String avanceObservaciones;
	 */

	@Column(name = "fecha_alta")
	private Date fechaAlta;

	@Column(name = "valida_director")
	private Boolean validaDirector;

	private Boolean terminada;

	private Boolean activo;
	
	@OneToMany(mappedBy = "dosificacion", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderBy("fechaInicio")
	List<DosificacionTema> dosificacionTemas;
	
	@OneToOne(mappedBy = "dosificacion", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private DosificacionValida dosificacionValida;
	
	public Dosificacion() {
	}
	
	public Dosificacion(int id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}

	public Integer getIdCorteEvaluativo() {
		return idCorteEvaluativo;
	}

	public void setIdCorteEvaluativo(Integer idCorteEvaluativo) {
		this.idCorteEvaluativo = idCorteEvaluativo;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	/*
	 * public String getCompetenciaDesarrollar() { return competenciaDesarrollar; }
	 */

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	/*
	 * public void setCompetenciaDesarrollar(String competenciaDesarrollar) {
	 * this.competenciaDesarrollar = competenciaDesarrollar; }
	 * 
	 * public String getActividadApertura() { return actividadApertura; }
	 * 
	 * public void setActividadApertura(String actividadApertura) {
	 * this.actividadApertura = actividadApertura; }
	 * 
	 * public String getActividadDesarrollo() { return actividadDesarrollo; }
	 * 
	 * public void setActividadDesarrollo(String actividadDesarrollo) {
	 * this.actividadDesarrollo = actividadDesarrollo; }
	 * 
	 * public String getActividadCierre() { return actividadCierre; }
	 * 
	 * public void setActividadCierre(String actividadCierre) { this.actividadCierre
	 * = actividadCierre; }
	 * 
	 * public String getAvanceObservaciones() { return avanceObservaciones; }
	 * 
	 * public void setAvanceObservaciones(String avanceObservaciones) {
	 * this.avanceObservaciones = avanceObservaciones; }
	 */

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Boolean getValidaDirector() {
		return validaDirector;
	}

	public void setValidaDirector(Boolean validaDirector) {
		this.validaDirector = validaDirector;
	}

	public Boolean getTerminada() {
		return terminada;
	}

	public void setTerminada(Boolean terminada) {
		this.terminada = terminada;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public List<DosificacionTema> getDosificacionTemas() {
		return dosificacionTemas;
	}

	public void setDosificacionTemas(List<DosificacionTema> dosificacionTemas) {
		this.dosificacionTemas = dosificacionTemas;
	}

	public DosificacionValida getDosificacionValida() {
		return dosificacionValida;
	}

	public void setDosificacionValida(DosificacionValida dosificacionValida) {
		this.dosificacionValida = dosificacionValida;
	}
}

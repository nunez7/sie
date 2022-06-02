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

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "prorroga")
public class Prorroga {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_carga_horaria")
	private CargaHoraria cargaHoraria;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@Column(name = "fecha_alta")
	private Date fechaAlta;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@Column(name = "fecha_limite")
	private Date fechaLimite;

	private String comentario;
	
	private Boolean activo;
	
	private Boolean aceptada;

	@Column(name = "id_tipo_prorroga")
	private Integer idTipoProrroga;
	
	@OneToOne
	@JoinColumn(name = "id_corte_evaluativo")
	@LazyCollection(LazyCollectionOption.FALSE)
	private CorteEvaluativo corteEvaluativo;
	
	@OneToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "id", referencedColumnName = "id_prorroga")
	private ProrrogaAutoriza prorrogaAutoriza;
	
	public Prorroga() {
	}
	
	public Prorroga(int id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CargaHoraria getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(CargaHoraria cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaLimite() {
		return fechaLimite;
	}

	public void setFechaLimite(Date fechaLimite) {
		this.fechaLimite = fechaLimite;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Integer getIdTipoProrroga() {
		return idTipoProrroga;
	}

	public void setIdTipoProrroga(Integer idTipoProrroga) {
		this.idTipoProrroga = idTipoProrroga;
	}
	
	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Boolean getAceptada() {
		return aceptada;
	}

	public void setAceptada(Boolean aceptada) {
		this.aceptada = aceptada;
	}

	public CorteEvaluativo getCorteEvaluativo() {
		return corteEvaluativo;
	}

	public void setCorteEvaluativo(CorteEvaluativo corteEvaluativo) {
		this.corteEvaluativo = corteEvaluativo;
	}

	public ProrrogaAutoriza getProrrogaAutoriza() {
		return prorrogaAutoriza;
	}

	public void setProrrogaAutoriza(ProrrogaAutoriza prorrogaAutoriza) {
		this.prorrogaAutoriza = prorrogaAutoriza;
	}
	
}

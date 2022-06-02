package edu.mx.utdelacosta.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dosificaciones_comentarios")
public class DosificacionComentario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "id_carga_horaria")
	private Integer idCargaHoraria;
	
	@Column(name = "id_persona")
	private Integer idPersona;
	
	@Column(name = "cometario")
	private String comentario;
	
	@Column(name = "fecha_alta")
	private Date fechaAlta;
	
	private Boolean leido;
	
	private Boolean solucionado;
	
	public DosificacionComentario() {
	}
	
	public DosificacionComentario(int id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdCargaHoraria() {
		return idCargaHoraria;
	}

	public void setIdCargaHoraria(Integer idCargaHoraria) {
		this.idCargaHoraria = idCargaHoraria;
	}

	public Integer getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Boolean getLeido() {
		return leido;
	}

	public void setLeido(Boolean leido) {
		this.leido = leido;
	}

	public Boolean getSolucionado() {
		return solucionado;
	}

	public void setSolucionado(Boolean solucionado) {
		this.solucionado = solucionado;
	}
}

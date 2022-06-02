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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="asesorias")
public class Asesoria{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "id_tipo_asesoria")
	private Integer idTipoAsesoria;
	
	@OneToOne()
	@JoinColumn(name = "id_carga_horaria")
	private CargaHoraria cargaHoraria;
	
	private String tema;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@Column(name = "fecha_asesoria")
	private Date fechaAsesoria;
	
	private String comentario;

	public Asesoria() {
	}
	
	public Asesoria(int id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdTipoAsesoria() {
		return idTipoAsesoria;
	}

	public void setIdTipoAsesoria(Integer idTipoAsesoria) {
		this.idTipoAsesoria = idTipoAsesoria;
	}

	public CargaHoraria getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(CargaHoraria cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public Date getFechaAsesoria() {
		return fechaAsesoria;
	}

	public void setFechaAsesoria(Date fechaAsesoria) {
		this.fechaAsesoria = fechaAsesoria;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	
	
}

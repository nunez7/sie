package edu.mx.utdelacosta.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "cargas_horarias")
public class CargaHoraria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "id_materia")
	private Materia materia;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profesor")
	private Persona profesor;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_grupo")
	private Grupo grupo;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_periodo", referencedColumnName = "id")
	private Periodo periodo;

	private Boolean activo;
	
	@OneToMany(mappedBy = "idCargaHoraria", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<DosificacionComentario> dosificacionComentarios;

	@Column(name = "fecha_alta")
	private Date fechaAlta;

	public CargaHoraria() {
	}
	
	public CargaHoraria(Integer id) {
		this.id = id;
	}

	
	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}
	
	public Persona getProfesor() {
		return profesor;
	}

	public void setProfesor(Persona profesor) {
		this.profesor = profesor;
	}


	public List<DosificacionComentario> getDosificacionComentarios() {
		return dosificacionComentarios;
	}


	public void setDosificacionComentarios(List<DosificacionComentario> dosificacionComentarios) {
		this.dosificacionComentarios = dosificacionComentarios;
	}


	public Date getFechaAlta() {
		return fechaAlta;
	}
	
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	
	
}

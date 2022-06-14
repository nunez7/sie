package edu.mx.utdelacosta.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "asistencia_tema_grupal")
public class AsistenciaTemaGrupal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tema_grupal")
	private TemaGrupal temaGrupal;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_alumno")
	private Alumno alumno; 
	
	private String asiencia;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TemaGrupal getTemaGrupal() {
		return temaGrupal;
	}

	public void setTemaGrupal(TemaGrupal temaGrupal) {
		this.temaGrupal = temaGrupal;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public String getAsiencia() {
		return asiencia;
	}

	public void setAsiencia(String asiencia) {
		this.asiencia = asiencia;
	}
	
}

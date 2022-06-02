package edu.mx.utdelacosta.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "calificacion")
public class Calificacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name="id_alumno", referencedColumnName = "id")
	private Alumno alumno;
	
	@OneToOne
	@JoinColumn(name="id_mecanismo_instrumento", referencedColumnName = "id")
	private MecanismoInstrumento mecanismoInstrumento;
	
	private Integer valor;
	
	public Calificacion() {
	}
	
	public Calificacion(int id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public MecanismoInstrumento getMecanismoInstrumento() {
		return mecanismoInstrumento;
	}

	public void setMecanismoInstrumento(MecanismoInstrumento mecanismoInstrumento) {
		this.mecanismoInstrumento = mecanismoInstrumento;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}
	
}

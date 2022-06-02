package edu.mx.utdelacosta.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pago_alumno")
public class PagoAlumno {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="id_pago", referencedColumnName = "id")
	private PagoGeneral pagoGeneral;
	
	@ManyToOne
	@JoinColumn(name="id_alumno", referencedColumnName = "id")
	private Alumno alumno;
	
	public PagoAlumno() {
	}
	
	public PagoAlumno(Integer id) {
		this.id=id;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PagoGeneral getPagoGeneral() {
		return pagoGeneral;
	}

	public void setPagoGeneral(PagoGeneral pagoGeneral) {
		this.pagoGeneral = pagoGeneral;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}
}

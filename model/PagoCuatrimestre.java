package edu.mx.utdelacosta.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pago_cuatrimestre")
public class PagoCuatrimestre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "id_pago", referencedColumnName = "id")
	private PagoGeneral pagoGeneral;

	@OneToOne
	@JoinColumn(name = "id_alumno_grupo", referencedColumnName = "id")
	private AlumnoGrupo alumnoGrupo;

	public PagoCuatrimestre() {

	}

	public PagoCuatrimestre(int id) {
		this.id = id;
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

	public AlumnoGrupo getAlumnoGrupo() {
		return alumnoGrupo;
	}

	public void setAlumnoGrupo(AlumnoGrupo alumnoGrupo) {
		this.alumnoGrupo = alumnoGrupo;
	}

}

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
@Table(name = "motivos_tutoria")
public class MotivoTutoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_tutoria_individual", referencedColumnName = "id")
	TutoriaIndividual tutoriaIndividual;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_motivo", referencedColumnName = "id")
	Motivo motivo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TutoriaIndividual getTutoriaIndividual() {
		return tutoriaIndividual;
	}

	public void setTutoriaIndividual(TutoriaIndividual tutoriaIndividual) {
		this.tutoriaIndividual = tutoriaIndividual;
	}

	public Motivo getMotivo() {
		return motivo;
	}

	public void setMotivo(Motivo motivo) {
		this.motivo = motivo;
	}
	
}

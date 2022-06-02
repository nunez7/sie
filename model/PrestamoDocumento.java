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

@Entity
@Table(name = "prestamo_documento")
public class PrestamoDocumento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name = "id_persona", referencedColumnName = "id")
	private Persona persona;
	
	@OneToOne
	@JoinColumn(name = "id_persona_documento")
	private PersonaDocumento personaDocumento;
	
	@Column(name = "fecha_alta")
	private Date fechaAlta;
	
	private Boolean estatus;
	
	public PrestamoDocumento() {
		
	}
	
	public PrestamoDocumento(int id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public PersonaDocumento getPersonaDocumento() {
		return personaDocumento;
	}

	public void setPersonaDocumento(PersonaDocumento personaDocumento) {
		this.personaDocumento = personaDocumento;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Boolean getEstatus() {
		return estatus;
	}

	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}
	
	
}

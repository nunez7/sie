package edu.mx.utdelacosta.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "personal")
public class Personal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	@JoinColumn(name = "id_persona", referencedColumnName = "id")
	private Persona persona;

	@Column(name = "no_empleado")
	private String noEmpleado;

	private String extension;

	@Column(name = "coordinador_estadia")
	private Boolean coordinador;

	private Boolean jefe;

	@ManyToOne
	@JoinColumn(name = "id_puesto", referencedColumnName = "id")
	private Puesto puesto;

	@ManyToOne
	@JoinColumn(name = "id_jefe", referencedColumnName = "id")
	private Persona jefePersona;

	private String extracto;

	private String expediente;

	private Boolean activo;

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

	public String getExtension() {
		return extension;
	}

	public Boolean getJefe() {
		return jefe;
	}

	public void setJefe(Boolean jefe) {
		this.jefe = jefe;
	}

	public String getNoEmpleado() {
		return noEmpleado;
	}

	public void setNoEmpleado(String noEmpleado) {
		this.noEmpleado = noEmpleado;
	}

	public Boolean getCoordinador() {
		return coordinador;
	}

	public void setCoordinador(Boolean coordinador) {
		this.coordinador = coordinador;
	}

	public Puesto getPuesto() {
		return puesto;
	}

	public void setPuesto(Puesto puesto) {
		this.puesto = puesto;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Persona getJefePersona() {
		return jefePersona;
	}

	public void setJefePersona(Persona jefePersona) {
		this.jefePersona = jefePersona;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getExtracto() {
		return extracto;
	}

	public void setExtracto(String extracto) {
		this.extracto = extracto;
	}

	public String getExpediente() {
		return expediente;
	}

	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}

	public String getNombreCompletoConTipoProfesor() {
		return persona.getNombreCompleto() + ", " + persona.getNivelEstudio().getAbreviatura();
	}

}

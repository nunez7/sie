package edu.mx.utdelacosta.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "alumnos")
public class Alumno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String matricula;
	
	private Integer ceneval;
	
	@Column(name="documentos_ingresos")
	private Integer estadoDocumentosIngreso;
	
	@OneToOne
	@JoinColumn(name="id_carrera", referencedColumnName = "id")
	private Carrera carreraInicio;
	
	@Column(name="estatus")
	private Integer estatusGeneral;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "id_persona", referencedColumnName = "id")
	private Persona persona;
	
	@OneToOne(mappedBy = "alumno", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private AlumnoFamiliar familiar;
	
	@OneToOne(mappedBy = "alumno",fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private DatosAlumno datosAlumno;
	
	@OneToOne(mappedBy = "alumno", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private EscuelaProcedencia escuelaProcedencia;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "alumno")
	private List<Estadia> estadias;
	
	@OneToOne(mappedBy = "alumno", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private FolioCeneval folioCeneval;
	
			
	@Transient
	private Grupo ultimoGrupo;
	
	public Alumno() {
	}
	
	public Alumno(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Integer getCeneval() {
		return ceneval;
	}

	public List<Estadia> getEstadias() {
		return estadias;
	}

	public void setEstadias(List<Estadia> estadias) {
		this.estadias = estadias;
	}

	public void setCeneval(Integer ceneval) {
		this.ceneval = ceneval;
	}

	public Integer getEstadoDocumentosIngreso() {
		return estadoDocumentosIngreso;
	}

	public void setEstadoDocumentosIngreso(Integer estadoDocumentosIngreso) {
		this.estadoDocumentosIngreso = estadoDocumentosIngreso;
	}

	public Carrera getCarreraInicio() {
		return carreraInicio;
	}

	public void setCarreraInicio(Carrera carreraInicio) {
		this.carreraInicio = carreraInicio;
	}

	public Integer getEstatusGeneral() {
		return estatusGeneral;
	}

	public void setEstatusGeneral(Integer estatusGeneral) {
		this.estatusGeneral = estatusGeneral;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public AlumnoFamiliar getFamiliar() {
		return familiar;
	}
	
	public void setFamiliar(AlumnoFamiliar familiar) {
		this.familiar = familiar;
	}

	public DatosAlumno getDatosAlumno() {
		return datosAlumno;
	}

	public void setDatosAlumno(DatosAlumno datosAlumno) {
		this.datosAlumno = datosAlumno;
	}

	public EscuelaProcedencia getEscuelaProcedencia() {
		return escuelaProcedencia;
	}

	public void setEscuelaProcedencia(EscuelaProcedencia escuelaProcedencia) {
		this.escuelaProcedencia = escuelaProcedencia;
	}

	public FolioCeneval getFolioCeneval() {
		return folioCeneval;
	}

	public void setFolioCeneval(FolioCeneval folioCeneval) {
		this.folioCeneval = folioCeneval;
	}
	
	/*VARIABLES QUE NO SON DE ALUMNO*/


	public Grupo getUltimoGrupo() {
		return ultimoGrupo;
	}

	public void setUltimoGrupo(Grupo ultimoGrupo) {
		this.ultimoGrupo = ultimoGrupo;
	}
	
	
}

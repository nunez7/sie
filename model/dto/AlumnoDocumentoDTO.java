package edu.mx.utdelacosta.model.dto;

import edu.mx.utdelacosta.model.PersonaDocumento;

public class AlumnoDocumentoDTO {

	private Integer idAlumno;
	
	private String nombre;

	private String matricula;
	
	private PersonaDocumento actaNacimiento;
	
	private PersonaDocumento curp;
	
	private PersonaDocumento constanciaEstudio;
	
	private PersonaDocumento certificadoBachillerato;
	
	private PersonaDocumento ife;
	
	private PersonaDocumento imss;
	
	private PersonaDocumento requisitoTsu;
	
	private PersonaDocumento cedulaTsu;
	
	private PersonaDocumento convenio;
	
	private PersonaDocumento cedulaIngenieria;

	public Integer getIdAlumno() {
		return idAlumno;
	}

	public void setIdAlumno(Integer idAlumno) {
		this.idAlumno = idAlumno;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public PersonaDocumento getActaNacimiento() {
		return actaNacimiento;
	}

	public void setActaNacimiento(PersonaDocumento actaNacimiento) {
		this.actaNacimiento = actaNacimiento;
	}

	public PersonaDocumento getCurp() {
		return curp;
	}

	public void setCurp(PersonaDocumento curp) {
		this.curp = curp;
	}

	public PersonaDocumento getConstanciaEstudio() {
		return constanciaEstudio;
	}

	public void setConstanciaEstudio(PersonaDocumento constanciaEstudio) {
		this.constanciaEstudio = constanciaEstudio;
	}

	public PersonaDocumento getCertificadoBachillerato() {
		return certificadoBachillerato;
	}

	public void setCertificadoBachillerato(PersonaDocumento certificadoBachillerato) {
		this.certificadoBachillerato = certificadoBachillerato;
	}

	public PersonaDocumento getIfe() {
		return ife;
	}

	public void setIfe(PersonaDocumento ife) {
		this.ife = ife;
	}

	public PersonaDocumento getImss() {
		return imss;
	}

	public void setImss(PersonaDocumento imss) {
		this.imss = imss;
	}

	public PersonaDocumento getRequisitoTsu() {
		return requisitoTsu;
	}

	public void setRequisitoTsu(PersonaDocumento requisitoTsu) {
		this.requisitoTsu = requisitoTsu;
	}

	public PersonaDocumento getCedulaTsu() {
		return cedulaTsu;
	}

	public void setCedulaTsu(PersonaDocumento cedulaTsu) {
		this.cedulaTsu = cedulaTsu;
	}

	public PersonaDocumento getConvenio() {
		return convenio;
	}

	public void setConvenio(PersonaDocumento convenio) {
		this.convenio = convenio;
	}

	public PersonaDocumento getCedulaIngenieria() {
		return cedulaIngenieria;
	}

	public void setCedulaIngenieria(PersonaDocumento cedulaIngenieria) {
		this.cedulaIngenieria = cedulaIngenieria;
	}
	
	
}

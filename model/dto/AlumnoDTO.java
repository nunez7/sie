package edu.mx.utdelacosta.model.dto;

import java.util.List;

public class AlumnoDTO extends PersonaDTO{
	
	private Integer idAlumno;
	private String matricula;
	private Integer ceneval;
	private Integer documentosIngresos;
	private Integer idCarrera;
	private Integer idTurno;
	
	private String nombreFamiliar;
	private String edoNacimiento;
	private String telefonoFamiliar;
	private String domicilioFamiliar;
	private String cpFamiliar;
	private String coloniaFamiliar;
	private Integer idLocalidadFamiliar;
	private Integer hijos;
	private Boolean discapacitado;
	private String tipoDiscapacidad;
	private Boolean indigena;
	private Boolean dialecto;
	private String tipoBeca;
	private String promocion;
	private String observaciones;
	private Boolean otraUt;
	private Integer ext1;
	private Integer ext2;
	private String grupo;
	
	private int idEscuelaProcedencia;
	private String promedio; 
	private int idGrupo;
	private Integer paginaDatos;
	private int idAlumnoGrupo;
	
	private List<MateriaDTO> materias;
	
	public AlumnoDTO() {
	}
	
	public AlumnoDTO(Integer idAlumno) {
		this.idAlumno = idAlumno;
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
	public void setCeneval(Integer ceneval) {
		this.ceneval = ceneval;
	}
	public Integer getIdTurno() {
		return idTurno;
	}

	public void setIdTurno(Integer idTurno) {
		this.idTurno = idTurno;
	}

	public Integer getDocumentosIngresos() {
		return documentosIngresos;
	}
	public void setDocumentosIngresos(Integer documentosIngresos) {
		this.documentosIngresos = documentosIngresos;
	}
	public Integer getIdCarrera() {
		return idCarrera;
	}
	public void setIdCarrera(Integer idCarrera) {
		this.idCarrera = idCarrera;
	}
	public String getNombreFamiliar() {
		return nombreFamiliar;
	}
	public void setNombreFamiliar(String nombreFamiliar) {
		this.nombreFamiliar = nombreFamiliar;
	}
	public String getEdoNacimiento() {
		return edoNacimiento;
	}
	public void setEdoNacimiento(String edoNacimiento) {
		this.edoNacimiento = edoNacimiento;
	}
	public String getTelefonoFamiliar() {
		return telefonoFamiliar;
	}
	public void setTelefonoFamiliar(String telefonoFamiliar) {
		this.telefonoFamiliar = telefonoFamiliar;
	}
	public String getDomicilioFamiliar() {
		return domicilioFamiliar;
	}
	public void setDomicilioFamiliar(String domicilioFamiliar) {
		this.domicilioFamiliar = domicilioFamiliar;
	}
	public String getCpFamiliar() {
		return cpFamiliar;
	}
	public void setCpFamiliar(String cpFamiliar) {
		this.cpFamiliar = cpFamiliar;
	}
	public String getColoniaFamiliar() {
		return coloniaFamiliar;
	}
	public void setColoniaFamiliar(String coloniaFamiliar) {
		this.coloniaFamiliar = coloniaFamiliar;
	}
	public Integer getIdLocalidadFamiliar() {
		return idLocalidadFamiliar;
	}
	public void setIdLocalidadFamiliar(Integer idLocalidadFamiliar) {
		this.idLocalidadFamiliar = idLocalidadFamiliar;
	}
	public Integer getHijos() {
		return hijos;
	}
	public void setHijos(Integer hijos) {
		this.hijos = hijos;
	}
	public Boolean getDiscapacitado() {
		return discapacitado;
	}
	public void setDiscapacitado(Boolean discapacitado) {
		this.discapacitado = discapacitado;
	}
	public String getTipoDiscapacidad() {
		return tipoDiscapacidad;
	}
	public void setTipoDiscapacidad(String tipoDiscapacidad) {
		this.tipoDiscapacidad = tipoDiscapacidad;
	}
	public Boolean getIndigena() {
		return indigena;
	}
	public void setIndigena(Boolean indigena) {
		this.indigena = indigena;
	}
	public Boolean getDialecto() {
		return dialecto;
	}
	public void setDialecto(Boolean dialecto) {
		this.dialecto = dialecto;
	}
	public String getTipoBeca() {
		return tipoBeca;
	}
	public void setTipoBeca(String tipoBeca) {
		this.tipoBeca = tipoBeca;
	}
	public String getPromocion() {
		return promocion;
	}
	public void setPromocion(String promocion) {
		this.promocion = promocion;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public Boolean getOtraUt() {
		return otraUt;
	}
	public void setOtraUt(Boolean otraUt) {
		this.otraUt = otraUt;
	}
	public Integer getExt1() {
		return ext1;
	}
	public void setExt1(Integer ext1) {
		this.ext1 = ext1;
	}
	public Integer getExt2() {
		return ext2;
	}
	public void setExt2(Integer ext2) {
		this.ext2 = ext2;
	}
	public Integer getIdAlumno() {
		return idAlumno;
	}
	public void setIdAlumno(Integer idAlumno) {
		this.idAlumno = idAlumno;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public List<MateriaDTO> getMaterias() {
		return materias;
	}

	public void setMaterias(List<MateriaDTO> materias) {
		this.materias = materias;
	}

	public int getIdEscuelaProcedencia() {
		return idEscuelaProcedencia;
	}

	public void setIdEscuelaProcedencia(int idEscuelaProcedencia) {
		this.idEscuelaProcedencia = idEscuelaProcedencia;
	}

	public String getPromedio() {
		return promedio;
	}

	public void setPromedio(String promedio) {
		this.promedio = promedio;
	}

	public int getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(int idGrupo) {
		this.idGrupo = idGrupo;
	}

	public Integer getPaginaDatos() {
		return paginaDatos;
	}

	public void setPaginaDatos(Integer paginaDatos) {
		this.paginaDatos = paginaDatos;
	}

	public int getIdAlumnoGrupo() {
		return idAlumnoGrupo;
	}

	public void setIdAlumnoGrupo(int idAlumnoGrupo) {
		this.idAlumnoGrupo = idAlumnoGrupo;
	}
	
}

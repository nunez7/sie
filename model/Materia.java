package edu.mx.utdelacosta.model;


import java.util.Date;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "materias")
public class Materia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "id_plan_estudio",  nullable = false)
	private PlanEstudio planEstudio;
	
	private String nombre;
	
	@Column(name = "hoja_asignatura") 
	private String hojaAsignatura;
		
	@Column(name = "fecha_alta")
	private Date fechaAlta;
	
	private Boolean integradora;
	 
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cuatrimestre",  nullable = false)
	private Cuatrimestre cuatrimestre;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_area_conocimiento",  nullable = false)
	private AreaConocimiento areaConocimiento;
	 
	private String abreviatura;
	 
	@Column(name = "horas_practica") 
	private Integer horasPractica;
	  
	@Column(name = "horas_teoria") 
	private Integer horasTeoria;
	 
	@Column(name = "horas_semanales") 
	private Integer horasSemanales;
	
	@Column(name = "horas_totales")
	private Integer horasTotales;
	  
	private String objetivo;
	 
	private String competencia;
	
	private Boolean activo;
	
	private boolean curricular;
	
	private boolean calificacion;
	
	private boolean extracurricular;
	
	//lista de unidades tematicas
	@OneToMany(mappedBy = "materia" , cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderBy("consecutivo")
	List<UnidadTematica> unidadesTematicas;
	
	public Materia() {
		
	}
	
	public Materia(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PlanEstudio getPlanEstudio() {
		return planEstudio;
	}

	public void setPlanEstudio(PlanEstudio planEstudio) {
		this.planEstudio = planEstudio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getHojaAsignatura() {
		return hojaAsignatura;
	}

	public void setHojaAsignatura(String hojaAsignatura) {
		this.hojaAsignatura = hojaAsignatura;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Boolean getIntegradora() {
		return integradora;
	}

	public void setIntegradora(Boolean integradora) {
		this.integradora = integradora;
	}

	public Cuatrimestre getCuatrimestre() {
		return cuatrimestre;
	}

	public void setCuatrimestre(Cuatrimestre cuatrimestre) {
		this.cuatrimestre = cuatrimestre;
	}

	public AreaConocimiento getAreaConocimiento() {
		return areaConocimiento;
	}

	public void setAreaConocimiento(AreaConocimiento areaConocimiento) {
		this.areaConocimiento = areaConocimiento;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public Integer getHorasPractica() {
		return horasPractica;
	}

	public void setHorasPractica(Integer horasPractica) {
		this.horasPractica = horasPractica;
	}

	public Integer getHorasTeoria() {
		return horasTeoria;
	}

	public void setHorasTeoria(Integer horasTeoria) {
		this.horasTeoria = horasTeoria;
	}

	public Integer getHorasSemanales() {
		return horasSemanales;
	}

	public void setHorasSemanales(Integer horasSemanales) {
		this.horasSemanales = horasSemanales;
	}

	public Integer getHorasTotales() {
		return horasTotales;
	}

	public void setHorasTotales(Integer horasTotales) {
		this.horasTotales = horasTotales;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getCompetencia() {
		return competencia;
	}

	public void setCompetencia(String competencia) {
		this.competencia = competencia;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public boolean isCurricular() {
		return curricular;
	}

	public void setCurricular(boolean curricular) {
		this.curricular = curricular;
	}

	public boolean isCalificacion() {
		return calificacion;
	}

	public void setCalificacion(boolean calificacion) {
		this.calificacion = calificacion;
	}

	public boolean isExtracurricular() {
		return extracurricular;
	}

	public void setExtracurricular(boolean extracurricular) {
		this.extracurricular = extracurricular;
	}

	public List<UnidadTematica> getUnidadesTematicas() {
		return unidadesTematicas;
	}

	public void setUnidadesTematicas(List<UnidadTematica> unidadesTematicas) {
		this.unidadesTematicas = unidadesTematicas;
	}
	
}

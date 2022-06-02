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

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
@Table(name = "grupos")
public class Grupo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "id_carrera", referencedColumnName = "id")
	private Carrera carrera;
	
	@OneToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "id_periodo", referencedColumnName = "id")
	private Periodo periodo;

	@OneToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "id_profesor", referencedColumnName = "id")
	private Persona profesor;

	@OneToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "id_jefe_grupo", referencedColumnName = "id")
	private Persona jefeGrupo;

	@OneToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "id_subjefe_grupo", referencedColumnName = "id")
	private Persona subjefeGrupo;

	@OneToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "id_cuatrimestre", referencedColumnName = "id")
	private Cuatrimestre cuatrimestre;

	private String nombre;

	private String horario;

	@Column(name = "fecha_alta")
	private Date fechaAlta;

	@Column(name = "capacidad_maxima")
	private Integer capacidadMaxima;

	private Integer consecutivo;

	private Boolean activo;
			
	public Grupo() {
	}

	public Grupo(int id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Carrera getCarrera() {
		return carrera;
	}

	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public Persona getProfesor() {
		return profesor;
	}

	public void setProfesor(Persona profesor) {
		this.profesor = profesor;
	}

	public Persona getJefeGrupo() {
		return jefeGrupo;
	}

	public void setJefeGrupo(Persona jefeGrupo) {
		this.jefeGrupo = jefeGrupo;
	}

	public Persona getSubjefeGrupo() {
		return subjefeGrupo;
	}

	public void setSubjefeGrupo(Persona subjefeGrupo) {
		this.subjefeGrupo = subjefeGrupo;
	}

	public Cuatrimestre getCuatrimestre() {
		return cuatrimestre;
	}

	public void setCuatrimestre(Cuatrimestre cuatrimestre) {
		this.cuatrimestre = cuatrimestre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Integer getCapacidadMaxima() {
		return capacidadMaxima;
	}

	public void setCapacidadMaxima(Integer capacidadMaxima) {
		this.capacidadMaxima = capacidadMaxima;
	}

	public Integer getConsecutivo() {
		return consecutivo;
	}

	public void setConsecutivo(Integer consecutivo) {
		this.consecutivo = consecutivo;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
		
}

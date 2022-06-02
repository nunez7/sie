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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "unidades_tematicas")
public class UnidadTematica {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_materia")
	private Materia materia;
	
	private String nombre;
	
	private Integer consecutivo;
	
	@Column(name = "horas_practicas")
	private Integer horasPracticas;
	
	@Column(name = "horas_teoricas")
	private Integer horasTeoricas;
	
	@Column(name = "horas_totales")
	private Integer horasTotales;
	
	@OneToMany(mappedBy = "unidadTematica" ,fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@OrderBy("consecutivo")
	List<TemaUnidad> temasUnidad;
	
	public UnidadTematica() {
	}
	
	public UnidadTematica(int id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getConsecutivo() {
		return consecutivo;
	}

	public void setConsecutivo(Integer consecutivo) {
		this.consecutivo = consecutivo;
	}

	public Integer getHorasPracticas() {
		return horasPracticas;
	}

	public void setHorasPracticas(Integer horasPracticas) {
		this.horasPracticas = horasPracticas;
	}

	public Integer getHorasTeoricas() {
		return horasTeoricas;
	}

	public void setHorasTeoricas(Integer horasTeoricas) {
		this.horasTeoricas = horasTeoricas;
	}

	public Integer getHorasTotales() {
		return horasTotales;
	}

	public void setHorasTotales(Integer horasTotales) {
		this.horasTotales = horasTotales;
	}

	public List<TemaUnidad> getTemasUnidad() {
		return temasUnidad;
	}

	public void setTemasUnidad(List<TemaUnidad> temasUnidad) {
		this.temasUnidad = temasUnidad;
	}
	
}

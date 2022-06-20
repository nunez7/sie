package edu.mx.utdelacosta.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "datos_alumno")
public class DatosAlumno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
		
	private Integer hijos;
	
	private Boolean discapacitado;
	
	@Column(name="tipo_discapacidad")
	private String tipoDiscapacidad;
	
	private Boolean indigena;
	
	private String promocion;
	
	private String observaciones;
	
	@Column(name = "tipo_beca")
	private String tipoBeca;
	
	@Column(name="otra_ut")
	private Boolean otraUt;
	
	@Column(name="ext_1")
	private Integer ext1;
	
	@Column(name="ext_2")
	private Integer ext2;
	
	@OneToOne
    @JoinColumn(name = "id_alumno")
	private Alumno alumno;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Boolean getIndigena() {
		return indigena;
	}

	public void setIndigena(Boolean indigena) {
		this.indigena = indigena;
	}
	
	public String getTipoBeca() {
		return tipoBeca;
	}

	public void setTipoBeca(String tipoBeca) {
		this.tipoBeca = tipoBeca;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getTipoDiscapacidad() {
		return tipoDiscapacidad;
	}

	public void setTipoDiscapacidad(String tipoDiscapacidad) {
		this.tipoDiscapacidad = tipoDiscapacidad;
	}

	public String getPromocion() {
		return promocion;
	}

	public void setPromocion(String promocion) {
		this.promocion = promocion;
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

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}
	
}

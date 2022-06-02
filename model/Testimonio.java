package edu.mx.utdelacosta.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "testimonios")
public class Testimonio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer numero;
	
	private String alfabetico;
	
	private String abreviatura;
	
	private Boolean integradora;
	
	public Testimonio() {
	}
	
	public Testimonio(int id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getAlfabetico() {
		return alfabetico;
	}

	public void setAlfabetico(String alfabetico) {
		this.alfabetico = alfabetico;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public Boolean getIntegradora() {
		return integradora;
	}

	public void setIntegradora(Boolean integradora) {
		this.integradora = integradora;
	}
	
}

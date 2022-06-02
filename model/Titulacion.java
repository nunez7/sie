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
@Table(name = "titulacion")
public class Titulacion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="liberacion_memoria")
	private Boolean memoriaLiberada;
	
	@Column(name="pago_libro")
	private Boolean pagoLibro;
	
	@Column(name="documentos_egreso")
	private Boolean documentosEgreso;
	
	@Column(name="documentos_estadia")
	private Boolean documentosEstadia;
	
	@Column(name="fecha_expedicion")
	private Date fechaExpedicion;
	
	@Column(name="fecha_exencion")
	private Date fechaExencion;
	
	@Column(name="usuario_autoriza")
	private Integer autoriza;
	
	@OneToOne
    @JoinColumn(name = "id_estadia")
	private Estadia estadia;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getMemoriaLiberada() {
		return memoriaLiberada;
	}

	public void setMemoriaLiberada(Boolean memoriaLiberada) {
		this.memoriaLiberada = memoriaLiberada;
	}

	public Boolean getPagoLibro() {
		return pagoLibro;
	}

	public void setPagoLibro(Boolean pagoLibro) {
		this.pagoLibro = pagoLibro;
	}

	public Boolean getDocumentosEgreso() {
		return documentosEgreso;
	}

	public void setDocumentosEgreso(Boolean documentosEgreso) {
		this.documentosEgreso = documentosEgreso;
	}

	public Boolean getDocumentosEstadia() {
		return documentosEstadia;
	}

	public void setDocumentosEstadia(Boolean documentosEstadia) {
		this.documentosEstadia = documentosEstadia;
	}

	public Date getFechaExpedicion() {
		return fechaExpedicion;
	}

	public void setFechaExpedicion(Date fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}

	public Date getFechaExencion() {
		return fechaExencion;
	}

	public void setFechaExencion(Date fechaExencion) {
		this.fechaExencion = fechaExencion;
	}

	public Integer getAutoriza() {
		return autoriza;
	}

	public void setAutoriza(Integer autoriza) {
		this.autoriza = autoriza;
	}

	public Estadia getEstadia() {
		return estadia;
	}

	public void setEstadia(Estadia estadia) {
		this.estadia = estadia;
	}
	
}

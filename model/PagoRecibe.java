package edu.mx.utdelacosta.model;

import java.util.Date;

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
@Table(name = "pago_recibe")
public class PagoRecibe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name="id_pago", referencedColumnName = "id")
	private PagoGeneral pagoGeneral;
	
	@ManyToOne
	@JoinColumn(name="id_cajero", referencedColumnName = "id")
	private Persona cajero;
	
	@Column(name = "fecha_cobro")
	private Date fechaCobro;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PagoGeneral getPagoGeneral() {
		return pagoGeneral;
	}

	public void setPagoGeneral(PagoGeneral pagoGeneral) {
		this.pagoGeneral = pagoGeneral;
	}

	public Persona getCajero() {
		return cajero;
	}

	public void setCajero(Persona cajero) {
		this.cajero = cajero;
	}

	public Date getFechaCobro() {
		return fechaCobro;
	}

	public void setFechaCobro(Date fechaCobro) {
		this.fechaCobro = fechaCobro;
	}
}

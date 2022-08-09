package edu.mx.utdelacosta.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pago_cliente")
public class PagoCliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="id_pago", referencedColumnName = "id")
	private PagoGeneral pagoGeneral;
	
	@ManyToOne
	@JoinColumn(name="id_cliente", referencedColumnName = "id")
	private Cliente cliente;

	public PagoCliente() {
		
	}
	
	public PagoCliente(Integer id) {
		this.id = id;
	}

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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}

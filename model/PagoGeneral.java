package edu.mx.utdelacosta.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "pagos_generales")
public class PagoGeneral {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name="id_concepto", referencedColumnName = "id")
	private Concepto concepto;
	
	private Integer cantidad;
	
	@Column(name="concepto")
	private String descripcion;
	
	private String folio;
	
	private Integer tipo;
	
	private String referencia;
	
	@Column(name = "ref_reconciliacion")
	private String refReconciliacion;
	
	@Column(name = "firma_digital")
	private String firmaDigital;
	
	@Column(name = "fecha_limite")
	private Date fechaLimite;
	
	@Column(name = "fecha_importacion")
	private Date fechaImportacion;
	
	@Column(name = "monto_unitario")
	private Double montoUnitario;
	
	private Double monto;
	
	private Integer status;
	
	private Integer cliente;
	
	@Column(name="created")
	private Date fechaAlta;
	
	private Boolean activo;
	
	@Column(name = "referencia_fondos")
	private String referenciaFondos;
	
	private Double descuento;
	
	private String comentario;
	
	private Boolean factura;
	
	private Boolean sistemaAnterior;
	
	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER, mappedBy = "pagoGeneral")
	private PagoAlumno pagoAlumno;
	
	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER, mappedBy = "pagoGeneral")
	private PagoRecibe pagoRecibe;
	
	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "pagoGeneral")
	@LazyCollection(LazyCollectionOption.FALSE)
	private PagoAsignatura pagoAsignatura;
	
	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "pagoGeneral")
	@LazyCollection(LazyCollectionOption.FALSE)
	private PagoCliente pagoCliente;
	
	//pago de un personal
	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "pagoGeneral")
	@LazyCollection(LazyCollectionOption.FALSE)
	private PagoPersona pagoPersona;
	
	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER, mappedBy = "pagoGeneral")
	private PagoCuatrimestre pagoCuatrimestre;
	
	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "pagoGeneral")
	@LazyCollection(LazyCollectionOption.FALSE)
	private PagoArea pagoArea;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getRefReconciliacion() {
		return refReconciliacion;
	}

	public void setRefReconciliacion(String refReconciliacion) {
		this.refReconciliacion = refReconciliacion;
	}

	public String getFirmaDigital() {
		return firmaDigital;
	}

	public void setFirmaDigital(String firmaDigital) {
		this.firmaDigital = firmaDigital;
	}

	public Date getFechaLimite() {
		return fechaLimite;
	}

	public void setFechaLimite(Date fechaLimite) {
		this.fechaLimite = fechaLimite;
	}

	public Date getFechaImportacion() {
		return fechaImportacion;
	}

	public void setFechaImportacion(Date fechaImportacion) {
		this.fechaImportacion = fechaImportacion;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double double1) {
		this.monto = double1;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCliente() {
		return cliente;
	}

	public void setCliente(Integer cliente) {
		this.cliente = cliente;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getReferenciaFondos() {
		return referenciaFondos;
	}

	public void setReferenciaFondos(String referenciaFondos) {
		this.referenciaFondos = referenciaFondos;
	}

	public Concepto getConcepto() {
		return concepto;
	}

	public void setConcepto(Concepto concepto) {
		this.concepto = concepto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getMontoUnitario() {
		return montoUnitario;
	}

	public void setMontoUnitario(Double montoUnitario) {
		this.montoUnitario = montoUnitario;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Double getDescuento() {
		return descuento;
	}

	public void setDescuento(Double descuento) {
		this.descuento = descuento;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Boolean getFactura() {
		return factura;
	}

	public void setFactura(Boolean factura) {
		this.factura = factura;
	}

	public PagoAlumno getPagoAlumno() {
		return pagoAlumno;
	}

	public void setPagoAlumno(PagoAlumno pagoAlumno) {
		this.pagoAlumno = pagoAlumno;
	}

	public PagoRecibe getPagoRecibe() {
		return pagoRecibe;
	}

	public void setPagoRecibe(PagoRecibe pagoRecibe) {
		this.pagoRecibe = pagoRecibe;
	}

	public PagoAsignatura getPagoAsignatura() {
		return pagoAsignatura;
	}

	public void setPagoAsignatura(PagoAsignatura pagoAsignatura) {
		this.pagoAsignatura = pagoAsignatura;
	}

	public PagoCliente getPagoCliente() {
		return pagoCliente;
	}

	public void setPagoCliente(PagoCliente pagoCliente) {
		this.pagoCliente = pagoCliente;
	}

	public PagoPersona getPagoPersona() {
		return pagoPersona;
	}

	public void setPagoPersona(PagoPersona pagoPersona) {
		this.pagoPersona = pagoPersona;
	}

	public PagoCuatrimestre getPagoCuatrimestre() {
		return pagoCuatrimestre;
	}

	public void setPagoCuatrimestre(PagoCuatrimestre pagoCuatrimestre) {
		this.pagoCuatrimestre = pagoCuatrimestre;
	}

	public PagoArea getPagoArea() {
		return pagoArea;
	}

	public void setPagoArea(PagoArea pagoArea) {
		this.pagoArea = pagoArea;
	}

	public Boolean getSistemaAnterior() {
		return sistemaAnterior;
	}

	public void setSistemaAnterior(Boolean sistemaAnterior) {
		this.sistemaAnterior = sistemaAnterior;
	}
	
}

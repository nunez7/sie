package edu.mx.utdelacosta.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sesiones")
public class Sesion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario", referencedColumnName = "id")
	private Usuario usuario;
	
	@Column(name = "ip_address")
	private String ipAddress;
	
	@Column(name = "sesion_id")
	private String sesionId;
	
	@Column(name = "fecha_inicio")
	private Date fechaInicio;
	
	@Column(name = "request_url")
	private String requestUrl;
	
	public Sesion() {}
	
	public Sesion(Usuario usuario, String ipAddress, String sesionId, Date fechaInicio, String requestUrl) {
		this.usuario = usuario;
		this.ipAddress = ipAddress;
		this.sesionId = sesionId;
		this.fechaInicio = fechaInicio;
		this.requestUrl = requestUrl;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getSesionId() {
		return sesionId;
	}
	public void setSesionId(String sesionId) {
		this.sesionId = sesionId;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getRequestUrl() {
		return requestUrl;
	}
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	
}

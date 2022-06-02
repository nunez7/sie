package edu.mx.utdelacosta.model.dto;

public class UsuarioDTO {
	
	private int idUsuario;
	
	private int idPersona;
	
	private int idRol;
	
	private String usuario;
	
	private String contrasenia;
	
	private boolean restablecer;

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public boolean isRestablecer() {
		return restablecer;
	}

	public void setRestablecer(boolean restablecer) {
		this.restablecer = restablecer;
	}

	public int getIdRol() {
		return idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}
	
}

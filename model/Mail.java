package edu.mx.utdelacosta.model;

import java.util.Map;

public class Mail {
	private String de;
	private String [] para;
	private String titulo;
	private Map<String, Object> variables;

	public Mail() {

	}

	public String getDe() {
		return de;
	}

	public void setDe(String de) {
		this.de = de;
	}

	public String[] getPara() {
		return para;
	}

	public void setPara(String[] para) {
		this.para = para;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> modelos) {
		this.variables = modelos;
	}
}

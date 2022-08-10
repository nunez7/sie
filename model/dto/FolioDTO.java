package edu.mx.utdelacosta.model.dto;

import java.util.Date;

public interface FolioDTO {	
	
	// numero de folio de 1 o mas pagos
	String getFolio();
	
	//nombre de empresa o nombre de alumno
	String getNombre();
	
	//apellido del alumno, vacio en caso de ser empresa
	String getPrimerApellido();
	
	//apellido del alumno, vacio en caso de ser empresa
	String getSegundoApellido();
	
	//matricula del alumno o rfc de empresa
	String getMatricula();
	
	//carrera del alumno o clave de empresa
	String getCarrera();
	
	//suma de los montos de los pagos relacionados al folio
	Double getMonto();
	
	//estado de activo del folio, se parseo de boolean a integer.
	Integer getActivo();
	
	//fecha de pagado
	Date getFecha();
	
	//tipo de pago realizado: banco, caja, corresponsalias
	Integer getTipoPago();
	
	//grupo del alumno o sector de la empresa
	String getGrupo();
	
	//ciclo del alumno o tipo de empresa
	String getCiclos();
	
	String getCuatrimestre();

	Date getFechaPago();
	
	String getPagoRecibe();
	
	Integer getIdAlumno();
	
	Integer getFactura();
	
}

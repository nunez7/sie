package edu.mx.utdelacosta.service;

import java.util.Date;
import java.util.List;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.model.dto.FolioDTO;
import edu.mx.utdelacosta.model.dto.PagoConceptoDTO;
import edu.mx.utdelacosta.model.dtoreport.CajaConcentradoDTO;
import edu.mx.utdelacosta.model.dtoreport.PagosGeneralesDTO;

public interface IPagoGeneralService {
	List<PagoGeneral> buscarPorAlumno (Integer idAlumno, Integer status);
	
	List<PagoGeneral> buscarPorAlumnoYFolio(Integer idAlumno, String folio, Integer status);
	
	String findUltimaReferenciaSEP(String referencia);
	
	String findUltimaReferencia(String referencia);
	
	void guardar(PagoGeneral pagoGeneral);
	
	PagoGeneral buscarPorId(Integer idPago);
	
	void eliminar(PagoGeneral pagoGeneral);
	
	PagoGeneral buscarPorAlumnoYConceptoYActivo(Alumno alumno, Integer concepto);
	//PagoGeneral buscarUltimoPagoGeneral();
	
	PagoGeneral buscarPorAlumnoYConceptoYCargaHoraria(Integer idAlumno, Integer idConcepto, Integer idCargaHoraria);
	
	PagoGeneral buscarPorAlumnoYConceptoYCargaHorariaYCorte(Integer idAlumno, Integer idConcepto, Integer idCargaHoraria, Integer idCorteEvaluativo);
	
	Integer contarPorAlumnoYStatus(Integer idAlumno, Integer status);
	
	//lista de pagos para reporte detallado con fechas de inicio, fin y un cajero
	List<PagosGeneralesDTO> buscarPorFechaInicioYFechaFinYCajero(Date fechaInicio, Date fechaFin, Integer idCajero);
	
	//lista de pagos para reporte detallado con fechas de inicio, fin y todos los cajeros
	List<PagosGeneralesDTO> buscarPorFechaInicioYFechaFinYTodosCajeros(Date fechaInicio, Date fechaFin);

	Integer validarPagoExamenAdmision(Integer idPersona);
	
	/*metodos de donaji */
	List<FolioDTO> buscarFolioPorFolioONombreOCliente (String folio);
	
	List<PagoGeneral> buscarTodosPorFolio(String folio);
	
	FolioDTO buscarPorFolio(String folio);
	
	FolioDTO buscarReciboPorFolio(String folio);
	
	PagoGeneral buscarUltimoPorFolio(String folio);
	
	//obtiene el ultimo folio en string 
	String generarFolio();
	
	//busca pagos o adeduos de personal (pagosPersona)
	List<PagoGeneral> buscarPorPersona (Integer idPersona, Integer status);
	
	//busca los pagos o adeduos de un cliente (pagoCliente)
	List<PagoGeneral> buscarPorCliente(Integer idCliente, Integer status);
	
	//busca los adeudos por referencia
	List<PagoGeneral> buscarPorReferencia(String referencia);
	
	List<CajaConcentradoDTO> findCajaConcentradoByFechaInicioAndFechaFinAndCajero (Date fechaInicio, Date fechaFin, Integer cajero);
	
	List<CajaConcentradoDTO> findCajaConcentradoByFechaInicioAndFechaFin(Date fechaInicio, Date fechaFin);
	
	List<String> buscarFoliosPorFechaInicioYFechaFinTodosCajeros(Date fechaInicio, Date fechaFin);
	
	List<PagoConceptoDTO> buscarConceptoPagoPorFolio(String folio);
	
	Double sumarTotalMontoPorFolio(String folio);
	
	List<PagosGeneralesDTO> buscarPorFechaInicioYFechaFinYTipoPagoYCajero(Date fechaInicio, Date fechaFin, Integer tipo ,Integer cajero);
	
	List<PagosGeneralesDTO> buscarPorFechaInicioYFechaFinYTipoPagoYTodosCajeros(Date fechaInicio, Date fechaFin, Integer tipo);

}

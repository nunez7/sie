package edu.mx.utdelacosta.service.db;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.model.dto.FolioDTO;
import edu.mx.utdelacosta.model.dto.PagoConceptoDTO;
import edu.mx.utdelacosta.model.dtoreport.CajaConcentradoDTO;
import edu.mx.utdelacosta.model.dtoreport.PagosGeneralesDTO;
import edu.mx.utdelacosta.repository.PagoGeneralRepository;
import edu.mx.utdelacosta.service.IPagoGeneralService;

@Service
public class PagoGeneralServiceJpa implements IPagoGeneralService{
	
	@Autowired
	private PagoGeneralRepository pagoRepo;

	@Override
	public PagoGeneral buscarPorId(Integer idPago) {
		Optional<PagoGeneral> optional = pagoRepo.findById(idPago);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}	

	@Override
	public List<PagoGeneral> buscarPorAlumno(Integer idAlumno, Integer status) {
		// TODO Auto-generated method stub
		return pagoRepo.findByAlumnoAndStatusOrderByCreatedDesc(idAlumno, status);
	}

	@Override
	public List<PagoGeneral> buscarPorAlumnoYFolio(Integer idAlumno, String folio, Integer status) {
		// TODO Auto-generated method stub
		return pagoRepo.findByAlumnoAndStatusAndFolioOrderByCreatedDesc(idAlumno, folio, status);
	}	

	@Override
	public String findUltimaReferenciaSEP(String referencia) {
		// TODO Auto-generated method stub
		return pagoRepo.findUltimaReferenciaSEP(referencia);
	}

	@Override
	public void guardar(PagoGeneral pagoGeneral) {
		// TODO Auto-generated method stub
		pagoRepo.save(pagoGeneral);
	}

	@Override
	public String findUltimaReferencia(String referencia) {
		// TODO Auto-generated method stub
		return pagoRepo.findUltimaReferencia(referencia);
	}

	@Override
	public void eliminar(PagoGeneral pagoGeneral) {
		pagoRepo.delete(pagoGeneral);
	}

	@Override
	public PagoGeneral buscarPorAlumnoYConceptoYActivo(Alumno alumno, Integer concepto) {
		return pagoRepo.findByAlumnoAndConceptosAndActivo(alumno, concepto);
	}
	
	@Override
	public PagoGeneral buscarPorAlumnoYConceptoYCargaHoraria(Integer idAlumno, Integer idConcepto, Integer idCargaHoraria) {
		return pagoRepo.findByAlumnoAndConceptoAndCargaHoraria(idAlumno, idConcepto, idCargaHoraria);
	}

	@Override
	public PagoGeneral buscarPorAlumnoYConceptoYCargaHorariaYCorte(Integer idAlumno, Integer idConcepto,
			Integer idCargaHoraria, Integer idCorteEvaluativo) {
		return pagoRepo.findByAlumnoAndConceptoAndCargaHorariaAndCorteEvaluativo(idAlumno, idConcepto, idCargaHoraria, idCorteEvaluativo);
	}
	
	@Override
	public Integer contarPorAlumnoYStatus(Integer idAlumno, Integer status) { 
		return pagoRepo.countByAlumnoAndStatus(idAlumno, status);
	}

	@Override
	public List<PagosGeneralesDTO> buscarPorFechaInicioYFechaFinYCajero(Date fechaInicio, Date fechaFin,
			Integer idCajero) {
		// TODO Auto-generated method stub
		return pagoRepo.findByFechaInicioAndFechaFinAndCajero(fechaInicio, fechaFin, idCajero);
	}

	@Override
	public List<PagosGeneralesDTO> buscarPorFechaInicioYFechaFinYTodosCajeros(Date fechaInicio, Date fechaFin) {
		// TODO Auto-generated method stub
		return pagoRepo.findByFechaInicioAndFechaFinAndAllCajeros(fechaInicio, fechaFin);
	}	

	@Override
	public Integer validarPagoExamenAdmision(Integer idPersona) {
		return pagoRepo.countPagoExamenAdmision(idPersona);
	}
	
	@Override
	public List<FolioDTO> buscarFolioPorFolioONombreOCliente(String folio) {
		return pagoRepo.FindByFolioOrNombreOrCliente(folio);
	}

	@Override
	public List<PagoGeneral> buscarTodosPorFolio(String folio) {
		return pagoRepo.findByFolio(folio);
	}

	@Override
	public FolioDTO buscarPorFolio(String folio) {
		return pagoRepo.findFolio(folio);
	}

	@Override
	public FolioDTO buscarReciboPorFolio(String folio) {
		return pagoRepo.findFolioRecibo(folio);
	}

	@Override
	public PagoGeneral buscarUltimoPorFolio(String folio) {
		return pagoRepo.findLastByFolio(folio);
	}

	@Override
	public String generarFolio() {
		// TODO Auto-generated method stub
		return pagoRepo.generateFolio();
	}

	@Override
	public List<PagoGeneral> buscarPorPersona(Integer idPersona, Integer status) {
		// TODO Auto-generated method stub
		return pagoRepo.findByPersonaAndStatusOrderByCreatedDesc(idPersona, status);
	}

	@Override
	public List<PagoGeneral> buscarPorCliente(Integer idCliente, Integer status) {
		// TODO Auto-generated method stub
		return pagoRepo.findByClienteAndStatusOrderByCreatedDesc(idCliente, status);
	}

	@Override
	public List<PagoGeneral> buscarPorReferencia(String referencia) {
		// TODO Auto-generated method stub
		return pagoRepo.findByReferencia(referencia);
	}	
	
	@Override
	public List<CajaConcentradoDTO> findCajaConcentradoByFechaInicioAndFechaFinAndCajero(Date fechaInicio,
			Date fechaFin, Integer cajero) {
		return pagoRepo.findCajaConcentradoByFechaInicioAndFechaFinAndCajero(fechaInicio, fechaFin, cajero);
	}

	@Override
	public List<CajaConcentradoDTO> findCajaConcentradoByFechaInicioAndFechaFin(Date fechaInicio, Date fechaFin) {
		return pagoRepo.findCajaConcentradoByFechaInicioAndFechaFin(fechaInicio, fechaFin);
	}
	
	@Override
	public List<String> buscarFoliosPorFechaInicioYFechaFinTodosCajeros(Date fechaInicio, Date fechaFin) {
		return pagoRepo.findFoliosByFechaInicioAndFechaFinAllCajero(fechaInicio, fechaFin);
	}
	
	@Override
	public List<PagoConceptoDTO> buscarConceptoPagoPorFolio(String folio) {
		return pagoRepo.findConceptoPagoByFolio(folio);
	}
	
	@Override
	public Double sumarTotalMontoPorFolio(String folio) {
		return pagoRepo.sumTotalMontoByFolio(folio);
	}

	@Override
	public List<PagosGeneralesDTO> buscarPorFechaInicioYFechaFinYTipoPagoYCajero(Date fechaInicio, Date fechaFin,
			Integer tipo, Integer cajero) {
		// TODO Auto-generated method stub
		return pagoRepo.findByTipoPagoAndFechaInicioAndFechaFinAndCajero(fechaInicio, fechaFin, tipo, cajero);
	}

	@Override
	public List<PagosGeneralesDTO> buscarPorFechaInicioYFechaFinYTipoPagoYTodosCajeros(Date fechaInicio, Date fechaFin,
			Integer tipo) {
		// TODO Auto-generated method stub
		return pagoRepo.findByTipoPagoAndFechaInicioAndFechaFinAndAllCajeros(fechaInicio, fechaFin, tipo);
	}
}

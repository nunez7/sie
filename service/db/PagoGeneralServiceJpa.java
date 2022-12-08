package edu.mx.utdelacosta.service.db;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional(readOnly = true)
	public PagoGeneral buscarPorId(Integer idPago) {
		Optional<PagoGeneral> optional = pagoRepo.findById(idPago);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}	

	@Override
	@Transactional(readOnly = true)
	public List<PagoGeneral> buscarPorAlumno(Integer idAlumno, Integer status) {
		// TODO Auto-generated method stub
		return pagoRepo.findByAlumnoAndStatusOrderByCreatedDesc(idAlumno, status);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PagoGeneral> buscarPorAlumnoYFolio(Integer idAlumno, String folio, Integer status) {
		// TODO Auto-generated method stub
		return pagoRepo.findByAlumnoAndStatusAndFolioOrderByCreatedDesc(idAlumno, folio, status);
	}	

	@Override
	@Transactional(readOnly = true)
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
	@Transactional(readOnly = true)
	public String findUltimaReferencia(String referencia) {
		// TODO Auto-generated method stub
		return pagoRepo.findUltimaReferencia(referencia);
	}

	@Override
	@Transactional
	public void eliminar(PagoGeneral pagoGeneral) {
		pagoRepo.delete(pagoGeneral);
	}

	@Override
	@Transactional(readOnly = true)
	public PagoGeneral buscarPorAlumnoYConceptoYActivo(Alumno alumno, Integer concepto) {
		return pagoRepo.findByAlumnoAndConceptosAndActivo(alumno, concepto);
	}
	
	@Override
	@Transactional(readOnly = true)
	public PagoGeneral buscarPorAlumnoYConceptoYCargaHoraria(Integer idAlumno, Integer idConcepto, Integer idCargaHoraria) {
		return pagoRepo.findByAlumnoAndConceptoAndCargaHoraria(idAlumno, idConcepto, idCargaHoraria);
	}

	@Override
	@Transactional(readOnly = true)
	public PagoGeneral buscarPorAlumnoYConceptoYCargaHorariaYCorte(Integer idAlumno, Integer idConcepto,
			Integer idCargaHoraria, Integer idCorteEvaluativo) {
		return pagoRepo.findByAlumnoAndConceptoAndCargaHorariaAndCorteEvaluativo(idAlumno, idConcepto, idCargaHoraria, idCorteEvaluativo);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Integer contarPorAlumnoYStatus(Integer idAlumno, Integer status) { 
		return pagoRepo.countByAlumnoAndStatus(idAlumno, status);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PagosGeneralesDTO> buscarPorFechaInicioYFechaFinYCajero(Date fechaInicio, Date fechaFin,
			Integer idCajero) {
		// TODO Auto-generated method stub
		return pagoRepo.findByFechaInicioAndFechaFinAndCajero(fechaInicio, fechaFin, idCajero);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PagosGeneralesDTO> buscarPorFechaInicioYFechaFinYTodosCajeros(Date fechaInicio, Date fechaFin) {
		// TODO Auto-generated method stub
		return pagoRepo.findByFechaInicioAndFechaFinAndAllCajeros(fechaInicio, fechaFin);
	}	

	@Override
	@Transactional(readOnly = true)
	public Integer validarPagoExamenAdmision(Integer idPersona) {
		return pagoRepo.countPagoExamenAdmision(idPersona);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<FolioDTO> buscarFolioPorFolioONombreOCliente(String folio) {
		return pagoRepo.FindByFolioOrNombreOrCliente(folio);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PagoGeneral> buscarTodosPorFolio(String folio) {
		return pagoRepo.findByFolio(folio);
	}

	@Override
	@Transactional(readOnly = true)
	public FolioDTO buscarPorFolio(String folio) {
		return pagoRepo.findFolio(folio);
	}

	@Override
	@Transactional(readOnly = true)
	public FolioDTO buscarReciboPorFolio(String folio) {
		return pagoRepo.findFolioRecibo(folio);
	}

	@Override
	@Transactional(readOnly = true)
	public PagoGeneral buscarUltimoPorFolio(String folio) {
		return pagoRepo.findLastByFolio(folio);
	}

	@Override
	@Transactional(readOnly = true)
	public String generarFolio() {
		// TODO Auto-generated method stub
		return pagoRepo.generateFolio();
	}

	@Override
	@Transactional(readOnly = true)
	public List<PagoGeneral> buscarPorPersona(Integer idPersona, Integer status) {
		// TODO Auto-generated method stub
		return pagoRepo.findByPersonaAndStatusOrderByCreatedDesc(idPersona, status);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PagoGeneral> buscarPorCliente(Integer idCliente, Integer status) {
		// TODO Auto-generated method stub
		return pagoRepo.findByClienteAndStatusOrderByCreatedDesc(idCliente, status);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PagoGeneral> buscarPorReferencia(String referencia) {
		// TODO Auto-generated method stub
		return pagoRepo.findByReferencia(referencia);
	}	
	
	@Override
	@Transactional(readOnly = true)
	public List<CajaConcentradoDTO> findCajaConcentradoByFechaInicioAndFechaFinAndCajero(Date fechaInicio,
			Date fechaFin, Integer cajero) {
		return pagoRepo.findCajaConcentradoByFechaInicioAndFechaFinAndCajero(fechaInicio, fechaFin, cajero);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CajaConcentradoDTO> findCajaConcentradoByFechaInicioAndFechaFin(Date fechaInicio, Date fechaFin) {
		return pagoRepo.findCajaConcentradoByFechaInicioAndFechaFin(fechaInicio, fechaFin);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<String> buscarFoliosPorFechaInicioYFechaFinTodosCajeros(Date fechaInicio, Date fechaFin) {
		return pagoRepo.findFoliosByFechaInicioAndFechaFinAllCajero(fechaInicio, fechaFin);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<PagoConceptoDTO> buscarConceptoPagoPorFolio(String folio) {
		return pagoRepo.findConceptoPagoByFolio(folio);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Double sumarTotalMontoPorFolio(String folio) {
		return pagoRepo.sumTotalMontoByFolio(folio);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PagosGeneralesDTO> buscarPorFechaInicioYFechaFinYTipoPagoYCajero(Date fechaInicio, Date fechaFin,
			Integer tipo, Integer cajero) {
		// TODO Auto-generated method stub
		return pagoRepo.findByTipoPagoAndFechaInicioAndFechaFinAndCajero(fechaInicio, fechaFin, tipo, cajero);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PagosGeneralesDTO> buscarPorFechaInicioYFechaFinYTipoPagoYTodosCajeros(Date fechaInicio, Date fechaFin,
			Integer tipo) {
		// TODO Auto-generated method stub
		return pagoRepo.findByTipoPagoAndFechaInicioAndFechaFinAndAllCajeros(fechaInicio, fechaFin, tipo);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Integer contarAdeudoCutrimestreAlumno(Integer alumnoGrupo) {
		return pagoRepo.countAdeudoCutrimestreAlumno(alumnoGrupo);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PagosGeneralesDTO> buscarPorFechaInicioYFechaFinYCajeroPaginado(Date fechaInicio, Date fechaFin,
			Integer idCajero, Pageable pageable) {
		return pagoRepo.findByFechaInicioAndFechaFinAndCajeroPaginable(fechaInicio, fechaFin, idCajero, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PagosGeneralesDTO> buscarPorFechaInicioYFechaFinYTodosCajerosPaginado(Date fechaInicio, Date fechaFin,
			Pageable pageable) {
		return pagoRepo.findByFechaInicioAndFechaFinAndAllCajerosPaginable(fechaInicio, fechaFin, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Double ObtenerTotalMontoPorFechaInicioYFechaFinTodosCajeros(Date fechaInicio, Date fechaFin) {
		return pagoRepo.findSumByFechaInicioAndFechaFindAllCajeros(fechaInicio, fechaFin);
	}

	@Override
	@Transactional(readOnly = true)
	public Double ObtenerTotalMontoPorFechaInicioYFechaFinYCajero(Date fechaInicio, Date fechaFin, Integer cajero) {
		return pagoRepo.findSumByFechaInicioAndFechaFindAndCajero(fechaInicio, fechaFin, cajero);
	}
}

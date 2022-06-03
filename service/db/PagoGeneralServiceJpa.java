package edu.mx.utdelacosta.service.db;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.PagoGeneral;
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
	
	

	/*@Override
	public PagoGeneral buscarUltimoPagoGeneral() {
		return pagoRepo.findLastPagoGeneral();
	}

	@Override
	public PagoGeneral buscarPorAlumnoYConceptoYAsignatura(Integer idAlumno, Integer idConcepto, Integer idAsignatura) {
		return pagoRepo.findByAlumnoAndConceptoAndAsignatura(idAlumno, idConcepto, idAsignatura);
	}

	@Override
	public PagoGeneral buscarPorAlumnoYConceptoYAsignaturaYCorte(Integer idAlumno, Integer idConcepto,
			Integer idAsignatura, Integer idCorteEvaluativo) {
		return pagoRepo.findByAlumnoAndConceptoAndAsignaturaAndCorteEvaluativo(idAlumno, idConcepto, idAsignatura, idCorteEvaluativo);
	}
	*/	
	
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
	public List<PagoGeneral> buscarPorFechaInicioYFechaFinYCajero(Date fechaInicio, Date fechaFin,
			Integer idCajero) {
		// TODO Auto-generated method stub
		return pagoRepo.findByFechaInicioAndFechaFinAndCajero(fechaInicio, fechaFin, idCajero);
	}

	@Override
	public List<PagoGeneral> buscarPorFechaInicioYFechaFinYTodosCajeros(Date fechaInicio, Date fechaFin) {
		// TODO Auto-generated method stub
		return pagoRepo.findByFechaInicioAndFechaFinAndAllCajeros(fechaInicio, fechaFin);
	}	
	

}

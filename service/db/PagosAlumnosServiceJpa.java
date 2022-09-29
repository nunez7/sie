package edu.mx.utdelacosta.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.PagoAlumno;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.repository.PagosAlumnoRepository;
import edu.mx.utdelacosta.service.IPagoAlumnoService;

@Service
public class PagosAlumnosServiceJpa implements IPagoAlumnoService{

	@Autowired
	private PagosAlumnoRepository pagosAlumnoRepo;
	
	@Override
	@Transactional
	public void guardar(PagoAlumno pagoAlumno) {
		pagosAlumnoRepo.save(pagoAlumno);
	}
	
	@Override
	@Transactional
	public void eliminar(PagoAlumno pagoAlumno) {
		pagosAlumnoRepo.delete(pagoAlumno);
		
	}

	@Override
	@Transactional(readOnly = true)
	public PagoAlumno buscarPorPagoGeneral(PagoGeneral pagoGeneral) {
		return pagosAlumnoRepo.findByPagoGeneral(pagoGeneral);
	}

}

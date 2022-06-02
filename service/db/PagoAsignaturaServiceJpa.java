package edu.mx.utdelacosta.service.db;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.PagoAsignatura;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.repository.PagoAsignaturaRepository;
import edu.mx.utdelacosta.service.IPagoAsignaturaService;

@Service
public class PagoAsignaturaServiceJpa implements IPagoAsignaturaService{
	
	@Autowired
	private PagoAsignaturaRepository pagoAsignaturaRepository;

	@Override
	public PagoAsignatura buscarPorId(Integer id) {
		Optional<PagoAsignatura> optional = pagoAsignaturaRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public PagoAsignatura buscarPorPago(PagoGeneral pago) {
		return pagoAsignaturaRepository.findByPagoGeneral(pago);
	}

	@Override
	public void guardar(PagoAsignatura pagoAsignatura) {
		pagoAsignaturaRepository.save(pagoAsignatura);
		
	}

	@Override
	public void eliminar(PagoAsignatura pagoAsignatura) {
		pagoAsignaturaRepository.delete(pagoAsignatura);
		
	}

}

package edu.mx.utdelacosta.service.db;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.NotaCredito;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.model.dto.NotaCreditoDTO;
import edu.mx.utdelacosta.repository.NotaCreditoRepository;
import edu.mx.utdelacosta.service.INotaCreditoService;

@Service
public class NotaCreditoService implements INotaCreditoService{

	@Autowired
	private NotaCreditoRepository ncRepository;
	
	@Override
	@Transactional(readOnly = true)
	public NotaCredito buscarPorPagoGeneral(PagoGeneral pagoGeneral) {
		return ncRepository.findByPagoGeneral(pagoGeneral);
	}

	@Override
	public void guardar(NotaCredito notaCredito) {
		ncRepository.save(notaCredito);
		
	}

	@Override
	@Transactional(readOnly = true)
	public NotaCreditoDTO BuscarPorFolio(String folio) {
		return ncRepository.findByFolio(folio);
	}

	@Override
	@Transactional(readOnly = true)
	public Double buscarTotalPorFechaInicioYFechaFin(Date fechaInicio, Date fechaFin) {
		return ncRepository.findTotalByFechaInicioAndFechaFin(fechaInicio, fechaFin);
	}

	@Override
	@Transactional(readOnly = true)
	public Double buscarTotalPorFechaInicioYFechaFinYCajero(Date fechaInicio, Date fechaFin, Integer cajero) {
		return ncRepository.findTotalByFechaInicioAndFechaFinAndCajero(fechaInicio, fechaFin, cajero);
	}

	@Override
	@Transactional(readOnly = true)
	public NotaCreditoDTO buscarConcentradoPorFechaInicioYFechaFin(Date fechaInicio, Date fechaFin) {
		return ncRepository.findConcentradoByFechaInicioAndFechaFin(fechaInicio, fechaFin);
	}

	@Override
	@Transactional(readOnly = true)
	public NotaCreditoDTO buscarConcentradoPorFechaInicioYFechaFinYCajero(Date fechaInicio, Date fechaFin, Integer cajero) {
		return ncRepository.findConcentradoByFechaInicioAndFechaFinAndCajero(fechaInicio, fechaFin, cajero);
	}

}

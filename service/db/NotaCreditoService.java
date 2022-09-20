package edu.mx.utdelacosta.service.db;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public NotaCredito buscarPorPagoGeneral(PagoGeneral pagoGeneral) {
		return ncRepository.findByPagoGeneral(pagoGeneral);
	}

	@Override
	public void guardar(NotaCredito notaCredito) {
		ncRepository.save(notaCredito);
		
	}

	@Override
	public NotaCreditoDTO BuscarPorFolio(String folio) {
		return ncRepository.findByFolio(folio);
	}

	@Override
	public Double buscarTotalPorFechaInicioYFechaFin(Date fechaInicio, Date fechaFin) {
		return ncRepository.findTotalByFechaInicioAndFechaFin(fechaInicio, fechaFin);
	}

	@Override
	public Double buscarTotalPorFechaInicioYFechaFinYCajero(Date fechaInicio, Date fechaFin, Integer cajero) {
		return ncRepository.findTotalByFechaInicioAndFechaFinAndCajero(fechaInicio, fechaFin, cajero);
	}

	@Override
	public NotaCreditoDTO buscarConcentradoPorFechaInicioYFechaFin(Date fechaInicio, Date fechaFin) {
		return ncRepository.findConcentradoByFechaInicioAndFechaFin(fechaInicio, fechaFin);
	}

	@Override
	public NotaCreditoDTO buscarConcentradoPorFechaInicioYFechaFinYCajero(Date fechaInicio, Date fechaFin, Integer cajero) {
		return ncRepository.findConcentradoByFechaInicioAndFechaFinAndCajero(fechaInicio, fechaFin, cajero);
	}

}

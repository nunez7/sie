package edu.mx.utdelacosta.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.NotaCredito;
import edu.mx.utdelacosta.model.PagoGeneral;
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

}

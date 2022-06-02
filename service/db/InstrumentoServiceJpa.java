package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Instrumento;
import edu.mx.utdelacosta.repository.InstrumentosRepository;
import edu.mx.utdelacosta.service.IInstrumentoService;

@Service
public class InstrumentoServiceJpa implements IInstrumentoService{

	@Autowired
	private InstrumentosRepository instrumentoRepository;
	
	@Override
	public List<Instrumento> buscarTodos() {
		return instrumentoRepository.findAll();
	}

	@Override
	public Instrumento buscarPorId(Integer id) {
		Optional<Instrumento> optional = instrumentoRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
}

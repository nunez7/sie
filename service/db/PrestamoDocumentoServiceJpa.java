package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.PrestamoDocumento;
import edu.mx.utdelacosta.repository.PrestamoDocumentoRepository;
import edu.mx.utdelacosta.service.IPrestamoDocumentoService;

@Service
public class PrestamoDocumentoServiceJpa implements IPrestamoDocumentoService{
	@Autowired
	private PrestamoDocumentoRepository prestamoRepository;

	@Override
	public void guardar(PrestamoDocumento prestamoDocumento) {
		prestamoRepository.save(prestamoDocumento);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<PrestamoDocumento> buscarPorAlumno(Integer idAlumno) {
		return prestamoRepository.findByAlumonOrderByFechaAlta(idAlumno);
	}

	@Override
	@Transactional(readOnly = true)
	public PrestamoDocumento buscarPorId(Integer id) {
		Optional<PrestamoDocumento> optional = prestamoRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	
}

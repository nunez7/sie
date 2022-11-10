package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.FocosAtencion;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.repository.FocosAtencionRepository;
import edu.mx.utdelacosta.service.IFocosAtencionService;

@Service
public class FocosAtencionServiceJpa implements IFocosAtencionService{
	
	@Autowired
	private FocosAtencionRepository focosRepo;

	@Override
	public void guardar(FocosAtencion focosAtencion) {
		focosRepo.save(focosAtencion);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FocosAtencion> buscarPorGrupo(Grupo grupo) {
		return focosRepo.findByGrupoOrderByDescripcion(grupo);
	}

	@Override
	@Transactional
	public void eliminar(FocosAtencion focosAtencion) {
		focosRepo.delete(focosAtencion);
	}
	
	@Override
	@Transactional(readOnly = true)
	public FocosAtencion buscarPorId(Integer id) {
		Optional<FocosAtencion> optional = focosRepo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

}

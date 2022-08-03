package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<FocosAtencion> buscarPorGrupo(Grupo grupo) {
		return focosRepo.findByGrupo(grupo);
	}

	@Override
	public void eliminar(FocosAtencion focosAtencion) {
		focosRepo.delete(focosAtencion);
	}

}

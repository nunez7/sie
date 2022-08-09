package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.UnidadTematica;
import edu.mx.utdelacosta.repository.UnidadesTematicasRepository;
import edu.mx.utdelacosta.service.IUnidadTematicaService;

@Service
public class UnidadesTematicasServicesJpa implements IUnidadTematicaService{

	@Autowired
	private UnidadesTematicasRepository unidadesTematicasRepository;
	
	@Override
	public UnidadTematica buscarPorId(Integer id) {
		Optional<UnidadTematica> unidadTematica = unidadesTematicasRepository.findById(id);
		if(unidadTematica.isPresent()) {
			return unidadTematica.get();
		}
		return null;
	}

	@Override
	public void guardar(UnidadTematica unidadTematica) {
		unidadesTematicasRepository.save(unidadTematica);
	}
	
	@Override
	public List<UnidadTematica> buscarPorDosificacion(Integer idDosificacion) {
		return unidadesTematicasRepository.findByDosificacion(idDosificacion);
	}

	@Override
	public List<UnidadTematica> buscarPorDosificacion(Integer idDosificacion) {
		return unidadesTematicasRepository.findByDosificacion(idDosificacion);
	}

}

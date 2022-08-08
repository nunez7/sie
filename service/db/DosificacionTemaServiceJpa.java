package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Dosificacion;
import edu.mx.utdelacosta.model.DosificacionTema;
import edu.mx.utdelacosta.model.TemaUnidad;
import edu.mx.utdelacosta.repository.DosificacionesTemaRepository;
import edu.mx.utdelacosta.service.IDosificacionTemaService;

@Service
public class DosificacionTemaServiceJpa implements IDosificacionTemaService{
	
	@Autowired
	private DosificacionesTemaRepository dosiTemaRepository;
	
	@Override
	public DosificacionTema buscarPorTemaYDosificacion(TemaUnidad tema, Dosificacion dosificacion) {
		return dosiTemaRepository.findByTemaAndDosificacion(tema, dosificacion);
	}

	@Override
	public void guardar(DosificacionTema dosificacionTema) {
		dosiTemaRepository.save(dosificacionTema);
		
	}

	@Override
	public List<DosificacionTema> buscarPorDosificacion(Dosificacion dosificacion) {
		return dosiTemaRepository.findByDosificacion(dosificacion);
	}
	
	@Override
	public List<DosificacionTema> buscarPorUnidadTematicaYDosificacion(Integer unidadTematica, Integer dosificacion) {
		return dosiTemaRepository.findByUnidadTematicaAndDosificacion(unidadTematica, dosificacion);
	}

}

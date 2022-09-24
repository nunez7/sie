package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Carrera;
import edu.mx.utdelacosta.repository.CarrerasRepository;
import edu.mx.utdelacosta.service.ICarrerasServices;

@Service
public class CarrerasServiceJpa implements ICarrerasServices{

	@Autowired
	private CarrerasRepository carrerasRepository;
	

	@Override
	@Transactional(readOnly = true)
	public Carrera buscarPorId(Integer id) {
		Optional<Carrera> optional = carrerasRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Carrera> buscarTodasMenosIngles() {
		return carrerasRepository.findAllExceptEnglishOrderByNombre();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Carrera> buscarCarrerasPorIdPersona(Integer id) {
		return carrerasRepository.findCarrerasByIdPersona(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Carrera> buscarTodas() {
		return carrerasRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Carrera> buscarTodasTSUMenosIngles() {
		return carrerasRepository.findAllTSUExceptEnglishOrderByNombre();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Carrera> buscarCarrerasPorPersonaYPeriodo(Integer idPersona, Integer idPeriodo) {
		return carrerasRepository.findCarrerasByPersonaAndPeriodo(idPersona, idPeriodo) ;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Integer> buscarCarreraAnterior(Integer idCarrera) {
		return carrerasRepository.findCarreraAnterior(idCarrera);
	}

}

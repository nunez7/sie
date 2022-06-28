package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Carrera;
import edu.mx.utdelacosta.repository.CarrerasRepository;
import edu.mx.utdelacosta.service.ICarrerasServices;

@Service
public class CarrerasServiceJpa implements ICarrerasServices{

	@Autowired
	private CarrerasRepository carrerasRepository;
	

	@Override
	public Carrera buscarPorId(Integer id) {
		Optional<Carrera> optional = carrerasRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public List<Carrera> buscarTodasMenosIngles() {
		return carrerasRepository.findAllExceptEnglishOrderByNombre();
	}
	
	@Override
	public List<Carrera> buscarCarrerasPorIdPersona(Integer id) {
		return carrerasRepository.findCarrerasByIdPersona(id);
	}

	@Override
	public List<Carrera> buscarTodas() {
		return carrerasRepository.findAll();
	}

	@Override
	public List<Carrera> buscarTodasTSUMenosIngles() {
		return carrerasRepository.findAllTSUExceptEnglishOrderByNombre();
	}

}

package edu.mx.utdelacosta.service.db;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.FolioCeneval;
import edu.mx.utdelacosta.repository.FolioCenevalRepository;
import edu.mx.utdelacosta.service.IFolioCenevalService;

@Service
public class FolioCenevalServiceJpa implements IFolioCenevalService{
	
	@Autowired
	private FolioCenevalRepository cenevalRepository;

	@Override
	public void guardar(FolioCeneval folio) {
		// TODO Auto-generated method stub
		cenevalRepository.save(folio);
	}

	@Override
	public FolioCeneval buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		Optional<FolioCeneval> optional = cenevalRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public FolioCeneval buscarPorAlumno(Alumno alumno) {
		// TODO Auto-generated method stub
		return cenevalRepository.findByAlumno(alumno);
	}

	@Override
	public FolioCeneval buscarPorMatriculaAlumno(String matricula) {
		// TODO Auto-generated method stub
		return cenevalRepository.findByMatricula(matricula);
	}

}

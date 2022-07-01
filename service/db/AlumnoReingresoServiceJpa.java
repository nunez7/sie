package edu.mx.utdelacosta.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.AlumnoReingreso;
import edu.mx.utdelacosta.repository.AlumnoReingresoRepository;
import edu.mx.utdelacosta.service.IAlumnoReingresoService;

@Service
public class AlumnoReingresoServiceJpa implements IAlumnoReingresoService{

	@Autowired
	private AlumnoReingresoRepository alumnoReingresoRepository;
	
	@Override
	public void guardar(AlumnoReingreso alumnoReingreso) {
		alumnoReingresoRepository.save(alumnoReingreso);
	}

}

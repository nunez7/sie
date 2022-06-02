package edu.mx.utdelacosta.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.AsesoriaAlumno;
import edu.mx.utdelacosta.repository.AsesoriaAlumnoRepository;
import edu.mx.utdelacosta.service.IAsesoriaAlumnoService;

@Service
public class AsesoriaAlumnoServiceJpa implements IAsesoriaAlumnoService{
	
	@Autowired
	private AsesoriaAlumnoRepository asesoriaAlumnoRepository;

	@Override
	public void guardar(AsesoriaAlumno asesoriaAlumno) {
		asesoriaAlumnoRepository.save(asesoriaAlumno);		
	}
	
	
}

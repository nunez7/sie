package edu.mx.utdelacosta.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.MecanismoAlumno;
import edu.mx.utdelacosta.repository.MecanismoAlumnoRepository;
import edu.mx.utdelacosta.service.IMecanismoAlumnoService;

@Service
public class MecanismoAlumnoServiceJpa implements IMecanismoAlumnoService{
	
	@Autowired
	private MecanismoAlumnoRepository mecaAluRepo;
	
	@Override
	@Transactional(readOnly = true)
	public MecanismoAlumno buscarPorAlumnoYCargaHoraria(Alumno alumno, CargaHoraria cargaHoraria) {
		return mecaAluRepo.findByAlumnoAndCargaHoraria(alumno, cargaHoraria);
	}

	@Override
	public void guardar(MecanismoAlumno mecanismoAlumno) {
		mecaAluRepo.save(mecanismoAlumno);		
	}

}

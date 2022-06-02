package edu.mx.utdelacosta.repository;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.MecanismoAlumno;

public interface MecanismoAlumnoRepository extends CrudRepository<MecanismoAlumno, Integer>{
	MecanismoAlumno findByAlumnoAndCargaHoraria(Alumno alumno, CargaHoraria cargaHoraria);
}

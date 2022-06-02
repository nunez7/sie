package edu.mx.utdelacosta.service;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.MecanismoAlumno;

public interface IMecanismoAlumnoService {
	MecanismoAlumno buscarPorAlumnoYCargaHoraria(Alumno alumno, CargaHoraria cargaHoraria);
	void guardar (MecanismoAlumno mecanismoAlumno);
}

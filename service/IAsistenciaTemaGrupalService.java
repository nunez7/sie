package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.AsistenciaTemaGrupal;
import edu.mx.utdelacosta.model.TemaGrupal;
import edu.mx.utdelacosta.model.dto.AlumnoAsistenciaDTO;

public interface IAsistenciaTemaGrupalService {

	List<AlumnoAsistenciaDTO> buscarPorTemaGrupalYGrupo(Integer idGrupo, Integer idTemaGrupal);
	
	void guardar(AsistenciaTemaGrupal asistencia);
	
	AsistenciaTemaGrupal buscarPorTemaGrupalYAlumno(TemaGrupal temaGrupal, Alumno alumno);
	
}

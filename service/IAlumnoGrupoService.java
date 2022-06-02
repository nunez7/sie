package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.AlumnoGrupo;
import edu.mx.utdelacosta.model.Grupo;

public interface IAlumnoGrupoService {
	AlumnoGrupo buscarPorId(Integer id);
	AlumnoGrupo buscarPorAlumnoYGrupo(Alumno alumno, Grupo grupo);
	void guardar(AlumnoGrupo alumnogrupo);
	AlumnoGrupo checkInscrito(Integer idAlumno, Integer idPeriodo);
	AlumnoGrupo buscarPorIdAlumnoYidGrupo(Integer idAlumno, Integer idGrupo);
	List<AlumnoGrupo> buscarPorIdAlumnoDesc(Integer idAlumno);
	void eliminar(AlumnoGrupo alumnoGrupo);
	List<AlumnoGrupo> buscarPorAlumnoYPeriodo(Integer idAlumno, Integer idPeriodo);
}

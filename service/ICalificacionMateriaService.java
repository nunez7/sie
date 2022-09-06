package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.CalificacionMateria;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.dtoreport.MateriaPromedioDTO;

public interface ICalificacionMateriaService {
	CalificacionMateria buscarPorCargayAlumno(CargaHoraria cargaHoraria, Alumno alumno);
	
	List<MateriaPromedioDTO> buscarPorGrupoAlumno(Integer idGrupo, Integer idAlumno);
	
	void guardar (CalificacionMateria calificacionMateria);
	//busca por grupo y periodo
	List<CalificacionMateria> buscarPorIdAlumnoEIdPeriodo(Integer idAlumno,Integer idPeriodo);
	//busca estatus de la materia
	String buscarPorAlumnoYCarga(Integer idAlumno, Integer idCargaHoraria);
			
	List<CalificacionMateria> buscarPorIdGrupoEIdPersona(Integer idGrupo,Integer idPeriodo);
	
	Float buscarCalificacionPorAlumnoYCargaHoraria(Integer idAlumno, Integer idCargaHoraria);
	
	CalificacionMateria buscarPorAlumnoYGrupoYMateria(Integer alumno, Integer grupo, Integer materia);

}

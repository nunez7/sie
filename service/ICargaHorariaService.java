package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;

public interface ICargaHorariaService {
	List<CargaHoraria> buscarPorGrupo(Grupo grupo);
	
	CargaHoraria buscarPorIdCarga(Integer id);
	
	void guardar(CargaHoraria cargaHoraria);
	
	List<CargaHoraria> buscarPorProfesorYPeriodoYActivo(Persona profesor, Periodo periodo, Boolean activo);
	
	CargaHoraria buscarPorMateriaYPeriodoYGrupo(Integer materia, Integer periodo, Integer grupo);
	//busca por grupo y periodo
	List<CargaHoraria> buscarPorGrupoYPeriodo(Integer grupo, Integer periodo);
	
	List<CargaHoraria> buscarPorProfesorYPeriodo(Persona profesor, Periodo periodo);
	
	CargaHoraria buscarPorIdMateriaEIdPersona(Integer idMateria, Integer idPersona);
	
	List<CargaHoraria> buscarPorGrupoYProfesorYPeriodo(Integer idGrupo, Integer idProfesor, Integer idPeriodo);
	
	List<CargaHoraria> buscarPorCarreraProfesorMateriaYPeriodo(Integer idCarrera, Integer idProfesor, Integer idMateria, Integer idPeriodo);
	
	List<CargaHoraria> buscarPorCarreraProfesorYPeriodo(Integer idCarrera, Integer idProfesor, Integer idPeriodo);
	
	// busca cargas aptas para copiar instrumentos
	List<CargaHoraria> buscarPorProfesorYPeriodoYCalendarioEvaluacion(Integer idProfesor, Integer idPeriodo, Integer idCarga);
	
	//materias que tengan la propiedad de calificacion true
	List<CargaHoraria> buscarPorGrupoYPeriodoYCalificacionSi(Integer idGrupo, Integer idPeriodo);
}

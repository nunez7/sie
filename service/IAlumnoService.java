package edu.mx.utdelacosta.service;
import java.util.List;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.dto.AlumnoInfoDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoAdeudoDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoMatriculaInicialDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoNoReinscritoDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoPromedioEscolaresDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoRegularDTO;
import edu.mx.utdelacosta.model.dto.ProspectoDTO;
import edu.mx.utdelacosta.model.dto.RemedialAlumnoDTO;
import edu.mx.utdelacosta.model.dtoreport.ProspectoEscolaresDTO;

public interface IAlumnoService {
	Alumno buscarPorPersona(Persona persona);
	
	Alumno buscarPorMatricula(String matricula);
	
	Alumno buscarPorId(Integer id);
	
	void guardar(Alumno alumno);
	
	List<Alumno> buscarPorGrupo (Integer idGrupo);
	
	List<Alumno> buscarPorCarreraYActivo(Integer idCarrera);
	
	List<AlumnoAdeudoDTO> obtenerAlumnosAdeudoPorPersonaCarreraYPeriodo(Integer idPersona, Integer idPeriodo);
	
	List<AlumnoAdeudoDTO> obtenerAlumnosAdeudoPorCarreraYPeriodo(Integer idCarrera, Integer idPeriodo);
	
	Integer contarAlumnosInscritosPorGrupoYActivo(Integer idCargaHoraria);
	
	Integer contarAlumnosBajasPorGrupoYActivo(Integer idCargaHoraria);
	
	List<AlumnoRegularDTO> obtenerRegulares(Integer idCarrera, Integer idPeriodo, Integer cuatrimestre);
	
	List<AlumnoRegularDTO> obtenerRegularesReinscribir(Integer idCarreraAnterior, Integer idPeriodo, Integer cuatrimestre, Integer idCarreraActual);
	
	List<AlumnoRegularDTO> obtenerRegularesByCarreraPeriodo(Integer idCarrera, Integer idPeriodo);
	
	List<Alumno> buscarTodosAlumnosPorGrupoOrdenPorNombreAsc(Integer idGrupo);
	
	List<ProspectoEscolaresDTO> buscarProspectosPorGeneracion(String generacion);
	
	List<AlumnoPromedioEscolaresDTO> buscarTodoPromedioEscolaresPorPeriodo(Integer periodo);
	
	List<AlumnoPromedioEscolaresDTO> buscarTodoPromedioEscolaresPorPeriodoyCarrera(Integer periodo, Integer carrera);
	
	List<AlumnoMatriculaInicialDTO> buscarMatriculaInicial(Integer periodo, Integer carrera);
	
	String buscarMatriculaSiguiente(String matricula);
	
	Integer buscarCurpExistente(String curp);
	
	List<Alumno> buscarProspectosRegular();
	
	void insertarMatriculaEnReserva(String matricula);
	
	List<Alumno> buscarTodoPorCarreraYPeriodo(Integer carrera, Integer idPeriodo);
	
	List<Alumno> buscarTodoAceptarPorCarreraYPeriodo(Integer carrera, Integer idPeriodo);
	
	Integer countAlumnosByCarrera(Integer idCarrera, Integer idPeriodo);
	
	Integer countInscritosByCarreraAndPeriodo(Integer idCarrera, Integer idPeriodo);
	
	Integer countBajaByCarreraAndPeriodo(Integer idCarrera, Integer idPeriodo);
	
	List<Alumno> buscarPorNombreOMatricula(String nombre);
	
	List<AlumnoInfoDTO> buscarPorProfesorPeriodoYNombreOMatricula(Integer idProfesor,Integer idPeriodo,String nombre);
	
	List<AlumnoInfoDTO> buscarPorProfesorYPeriodo(Integer idProfesor,Integer idPeriodo);
	
	List<Alumno> buscarTodos();
	
	List<Alumno> buscarPorGrupoYPeriodo(Integer idGrupo, Integer idPeriodo);
  
	List<Alumno> buscarPorPersonaCarreraAndActivo(Integer idPersona, Integer idPeriodo);
	
	List<Alumno> buscarPorCarreraAndPeriodoAndActivo(Integer idCarrera, Integer idPeriodo);

	Integer contarAlumnosRegularesPorGrupo(Integer idGrupo);
	
	// busca prospectos activos --modulo de captacion de prospectos
	List<ProspectoDTO> buscarProspectosActivos();
	
	List<Alumno> buscarProspectosAceptados(Integer idCarrera, Integer idPeriodo);
	
	List<AlumnoNoReinscritoDTO> buscarNoReinscritosPorPersonaCarreraYPeriodo(Integer idPersona, Integer idPeriodo);
	
	List<AlumnoNoReinscritoDTO> BuscarNoReinscritosPorPeriodo(Integer idPeriodo);
	
	List<AlumnoAdeudoDTO> obtenerAlumnosAdeudoPorTodasCarreraYPeriodo(Integer idPeriodo);
	
	Integer contarAlumnosPorSexoYGrupo (String sexo, Integer idGrupo);
	
	Integer contarAlumnosPorSexoYPersonaCarreraYPeriodo(String sexo, Integer idPersona, Integer idPeriodo);
	
	Integer contarAlumnosPorCarreraYTurno(Integer idCarrera, Integer idPeriodo, Integer idTurno);
	
	Integer contarInscritosPorCarreraYPeriodoYTurno(Integer idCarrera, Integer idPeriodo, Integer idTurno);
	
	Integer contarBajasPorCarreraYPeriodoYTurno(Integer idCarrera, Integer idPeriodo, Integer idTurno);
	
	List<AlumnoRegularDTO> obtenerRegularesPorCarreraYPeriodoYTurno(Integer idCarrera, Integer idPeriodo, Integer idTurno);
	
	List<AlumnoInfoDTO> buscarPorPersonaCarreraYPeriodoYNombreOMatricula(Integer idPersona,Integer idPeriodo,String nombre);
	
	List<AlumnoInfoDTO> buscarPorPersonaCarreraYPeriodoYActivos(Integer idPersona, Integer idPeriodo);
	
	List<AlumnoInfoDTO> buscarTodosPorNombreOMatriculaYPeriodoYActivos(String Nombre, Integer idPeriodo);
	
	List<AlumnoInfoDTO> buscarTodosPorPeriodo(Integer idPeriodo);
	
	List<AlumnoRegularDTO> buscarTodosProspectoReinscripcion(Integer carrera, Integer periodo);
	
	List<RemedialAlumnoDTO> buscarTodosRemedial();
	
	List<AlumnoRegularDTO> buscarTodosPorCarreraYCuatrimestreYPeriodo(Integer idCarrera, Integer idCuatrimestre, Integer idPeriodo);

}

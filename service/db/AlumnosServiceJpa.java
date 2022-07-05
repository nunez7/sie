package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.dto.ProspectoDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoAdeudoDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoMatriculaInicialDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoPromedioEscolaresDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoRegularDTO;
import edu.mx.utdelacosta.model.dtoreport.ProspectoEscolaresDTO;
import edu.mx.utdelacosta.repository.AlumnosRepository;
import edu.mx.utdelacosta.service.IAlumnoService;

@Service
public class AlumnosServiceJpa implements IAlumnoService{
	
	@Autowired
	private AlumnosRepository alumnosRepo;
	
	@Override
	public Alumno buscarPorPersona(Persona persona) {
		return alumnosRepo.findByPersona(persona);
	}

	@Override
	public void guardar(Alumno alumno) {
		// TODO Auto-generated method stub
		alumnosRepo.save(alumno);
	}

	@Override
	public Alumno buscarPorMatricula(String matricula) {
		// TODO Auto-generated method stub
		return  alumnosRepo.findByMatricula(matricula);
	}

	@Override
	public List<Alumno> buscarPorGrupo(Integer idGrupo) {
		return alumnosRepo.findAllAlumnosByGrupoOrderByNombreAsc(idGrupo);
	}

	@Override
	public List<Alumno> buscarPorCarreraYActivo(Integer idCarrera) {
		// TODO Auto-generated method stub
		return alumnosRepo.findAllAlumnosByCarreraAndActivo(idCarrera);
	}

	@Override
	public List<AlumnoAdeudoDTO> obtenerAlumnosAdeudoPorPersonaCarreraYPeriodo(Integer idPersona, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return alumnosRepo.getAllAlumnoAdeudoByPersonaCarreraAndPeriodo(idPersona, idPeriodo);
	}
	
	@Override
	public String buscarMatriculaSiguiente(String matricula) {
		return alumnosRepo.findNextMatricula(matricula);
	}
	
	@Override
	public Integer buscarCurpExistente(String curp) {
		return alumnosRepo.findExitenciaCurp(curp);
	}
	
	@Override
	public List<Alumno> buscarProspectosRegular() {
		return alumnosRepo.findAllByStatusPagoGeneralWithoutGrupoAndWithDocumentos();
	}

	@Override
	public List<AlumnoRegularDTO> obtenerRegulares(Integer idCarrera, Integer idPeriodo, Integer cuatrimestre) {
		// TODO Auto-generated method stub
		return alumnosRepo.findAllRegular(idCarrera, idPeriodo, cuatrimestre);
	}
	
	@Override
	public void insertarMatriculaEnReserva(String matricula) {
		alumnosRepo.insertMatriculaFromAlumnoInReservaClave(matricula);
	}
	
	@Override
	public List<Alumno> buscarTodoPorCarreraYPeriodo(Integer idCarrera, Integer idPeriodo) {
		return alumnosRepo.findAllByCarreraAndPeriodo(idCarrera, idPeriodo);
	}

	@Override
	public Alumno buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		Optional<Alumno> optional = alumnosRepo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	//REPORTES
	@Override
	public List<Alumno> buscarTodosAlumnosPorGrupoOrdenPorNombreAsc(Integer idGrupo) {
		// TODO Auto-generated method stub
		return alumnosRepo.findAllAlumnosByGrupoOrderByNombreAsc(idGrupo);
	}

	@Override
	public List<ProspectoEscolaresDTO> buscarProspectosPorGeneracion(String generacion) {
		// TODO Auto-generated method stub
		return alumnosRepo.findAllByGeneracion(generacion);
	}

	@Override
	public List<AlumnoPromedioEscolaresDTO> buscarTodoPromedioEscolaresPorPeriodo(Integer periodo) {
		// TODO Auto-generated method stub
		return alumnosRepo.findAllPromedioEscolaresByPeriodo(periodo);
	}

	@Override
	public List<AlumnoPromedioEscolaresDTO> buscarTodoPromedioEscolaresPorPeriodoyCarrera(Integer periodo,
			Integer carrera) {
		// TODO Auto-generated method stub
		return alumnosRepo.findAllPromedioEscolaresByPeriodoAndCarrera(periodo, carrera);
	}

	@Override
	public List<AlumnoMatriculaInicialDTO> buscarMatriculaInicial(Integer periodo, Integer carrera) {
		// TODO Auto-generated method stub
		return alumnosRepo.findAllMatriculaInicial(periodo, carrera);
	}

	@Override
	public List<Alumno> buscarTodoAceptarPorCarreraYPeriodo(Integer carrera, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return alumnosRepo.findAllAceptarByCarreraAndPeriodo(carrera, idPeriodo);
	}
	
	@Override
	public Integer contarAlumnosInscritosPorGrupoYActivo(Integer idGrupo ) {
		return alumnosRepo.countAlumnosInscritosByGrupoAndActivo(idGrupo);
	}

	@Override
	public Integer contarAlumnosBajasPorGrupoYActivo(Integer idCargaHoraria) {
		return alumnosRepo.countAlumnosBajaByGrupoAndActivo(idCargaHoraria);
	}

	@Override
	public Integer countAlumnosByCarrera(Integer idCarrera, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return alumnosRepo.countAlumnosByCarrera(idCarrera, idPeriodo);
	}

	@Override
	public Integer countInscritosByCarreraAndPeriodo(Integer idCarrera, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return alumnosRepo.countInscritosByCarreraAndPeriodo(idCarrera, idPeriodo);
	}

	@Override
	public Integer countBajaByCarreraAndPeriodo(Integer idCarrera, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return alumnosRepo.countBajaByCarreraAndPeriodo(idCarrera, idPeriodo);
	}

	@Override
	public List<AlumnoRegularDTO> obtenerRegularesByCarreraPeriodo(Integer carrera, Integer periodo) {
		// TODO Auto-generated method stub
		return alumnosRepo.obtenerRegularesByCarreraPeriodo(carrera, periodo);
	}
	
	@Override
	public List<Alumno> buscarPorNombreOMatricula(String nombre) {
		return alumnosRepo.findByNombreOrMatricula(nombre);
	}
	
	@Override
	public List<Alumno> buscarTodos() {
		return (List<Alumno>) alumnosRepo.findAll();
	}

	@Override
	public List<AlumnoAdeudoDTO> obtenerAlumnosAdeudoPorCarreraYPeriodo(Integer idCarrera, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return alumnosRepo.getAllAlumnoAdeudoByCarreraAndPeriodo(idCarrera, idPeriodo);
	}
	
	@Override
	public Integer contarAlumnosRegularesPorGrupo(Integer idGrupo) {
		return alumnosRepo.countAlumnosRegularesByGrupo(idGrupo);
	
	@Override
	public List<ProspectoDTO> buscarProspectosActivos() {
		return alumnosRepo.findAllActiveProspectos();
	}

	@Override
	public List<Alumno> buscarProspectosAceptados(Integer idCarrera, Integer idPeriodo) {
		return alumnosRepo.findAllAceptedProspectos(idCarrera, idPeriodo);
	}

}

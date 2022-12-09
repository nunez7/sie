package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.dto.AlumnoActivoDTO;
import edu.mx.utdelacosta.model.dto.AlumnoInfoDTO;
import edu.mx.utdelacosta.model.dto.ProspectoDTO;
import edu.mx.utdelacosta.model.dto.RemedialAlumnoDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoAdeudoDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoMatriculaInicialDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoNoReinscritoDTO;
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
	@Transactional(readOnly = true)
	public Alumno buscarPorPersona(Persona persona) {
		return alumnosRepo.findByPersona(persona);
	}

	@Override
	public void guardar(Alumno alumno) {
		// TODO Auto-generated method stub
		alumnosRepo.save(alumno);
	}

	@Override
	@Transactional(readOnly = true)
	public Alumno buscarPorMatricula(String matricula) {
		// TODO Auto-generated method stub
		return  alumnosRepo.findByMatricula(matricula);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Alumno> buscarPorGrupo(Integer idGrupo) {
		return alumnosRepo.findAllAlumnosByGrupoOrderByNombreAsc(idGrupo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Alumno> buscarPorCarreraYActivo(Integer idCarrera) {
		// TODO Auto-generated method stub
		return alumnosRepo.findAllAlumnosByCarreraAndActivo(idCarrera);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AlumnoAdeudoDTO> obtenerAlumnosAdeudoPorPersonaCarreraYPeriodo(Integer idPersona, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return alumnosRepo.getAllAlumnoAdeudoByPersonaCarreraAndPeriodo(idPersona, idPeriodo);
	}
	
	@Override
	@Transactional(readOnly = true)
	 public List<AlumnoAdeudoDTO> obtenerAlumnosAdeudoPorCarreraYPeriodo(Integer idCarrera, Integer idPeriodo) {
	  // TODO Auto-generated method stub
	  return alumnosRepo.getAllAlumnoAdeudoByCarreraAndPeriodo(idCarrera, idPeriodo);
	 }
	
	@Override
	@Transactional(readOnly = true)
	public String buscarMatriculaSiguiente(String matricula) {
		return alumnosRepo.findNextMatricula(matricula);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Integer buscarCurpExistente(String curp) {
		return alumnosRepo.findExitenciaCurp(curp);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Alumno> buscarProspectosRegular() {
		return alumnosRepo.findAllByStatusPagoGeneralWithoutGrupoAndWithDocumentos();
	}

	@Override
	@Transactional(readOnly = true)
	public List<AlumnoRegularDTO> obtenerRegulares(Integer idCarrera, Integer idPeriodo, Integer cuatrimestre) {
		// TODO Auto-generated method stub
		return alumnosRepo.findAllRegular(idCarrera, idPeriodo, cuatrimestre);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AlumnoRegularDTO> obtenerRegularesReinscribir(Integer idCarreraAnterior, Integer idPeriodo, Integer cuatrimestre, 
			Integer idCarreraActual) {
		// TODO Auto-generated method stub
		return alumnosRepo.findAllRegularReinscribir(idCarreraAnterior, idPeriodo, cuatrimestre, idCarreraActual);
	}
	
	@Override
	@Transactional
	public void insertarMatriculaEnReserva(String matricula) {
		alumnosRepo.insertMatriculaFromAlumnoInReservaClave(matricula);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Alumno> buscarTodoPorCarreraYPeriodo(Integer idCarrera, Integer idPeriodo) {
		return alumnosRepo.findAllByCarreraAndPeriodo(idCarrera, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
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
	@Transactional(readOnly = true)
	public List<Alumno> buscarTodosAlumnosPorGrupoOrdenPorNombreAsc(Integer idGrupo) {
		// TODO Auto-generated method stub
		return alumnosRepo.findAllAlumnosByGrupoOrderByNombreAsc(idGrupo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProspectoEscolaresDTO> buscarProspectosPorGeneracion(String generacion) {
		// TODO Auto-generated method stub
		return alumnosRepo.findAllByGeneracion(generacion);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AlumnoPromedioEscolaresDTO> buscarTodoPromedioEscolaresPorPeriodo(Integer periodo) {
		// TODO Auto-generated method stub
		return alumnosRepo.findAllPromedioEscolaresByPeriodo(periodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AlumnoPromedioEscolaresDTO> buscarTodoPromedioEscolaresPorPeriodoyCarrera(Integer periodo,
			Integer carrera) {
		// TODO Auto-generated method stub
		return alumnosRepo.findAllPromedioEscolaresByPeriodoAndCarrera(periodo, carrera);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AlumnoMatriculaInicialDTO> buscarMatriculaInicial(Integer periodo, Integer carrera) {
		// TODO Auto-generated method stub
		return alumnosRepo.findAllMatriculaInicial(periodo, carrera);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Alumno> buscarTodoAceptarPorCarreraYPeriodo(Integer carrera, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return alumnosRepo.findAllAceptarByCarreraAndPeriodo(carrera, idPeriodo);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Integer contarAlumnosInscritosPorGrupoYActivo(Integer idGrupo ) {
		return alumnosRepo.countAlumnosInscritosByGrupoAndActivo(idGrupo);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer contarAlumnosBajasPorGrupoYActivo(Integer idCargaHoraria) {
		return alumnosRepo.countAlumnosBajaByGrupoAndActivo(idCargaHoraria);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countAlumnosByCarrera(Integer idCarrera, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return alumnosRepo.countAlumnosByCarrera(idCarrera, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countInscritosByCarreraAndPeriodo(Integer idCarrera, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return alumnosRepo.countInscritosByCarreraAndPeriodo(idCarrera, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countBajaByCarreraAndPeriodo(Integer idCarrera, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return alumnosRepo.countBajaByCarreraAndPeriodo(idCarrera, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AlumnoRegularDTO> obtenerRegularesByCarreraPeriodo(Integer carrera, Integer periodo) {
		// TODO Auto-generated method stub
		return alumnosRepo.obtenerRegularesByCarreraPeriodo(carrera, periodo);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Alumno> buscarPorNombreOMatricula(String nombre) {
		return alumnosRepo.findByNombreOrMatricula(nombre);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AlumnoInfoDTO> buscarPorProfesorPeriodoYNombreOMatricula(Integer idProfesor, Integer idPeriodo, String nombre) {
		return alumnosRepo.findByProfesorAndPeriodoAndNombreOrMatricula(idProfesor, idPeriodo, nombre);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AlumnoInfoDTO> buscarPorProfesorYPeriodo(Integer idProfesor, Integer idPeriodo) {
		return alumnosRepo.findByProfesorAndPeriodo(idProfesor, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Alumno> buscarTodos() {
		return (List<Alumno>) alumnosRepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Alumno> buscarPorGrupoYPeriodo(Integer idGrupo, Integer idPeriodo) {
		return alumnosRepo.findByGrupoAndPeriodo(idGrupo, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Alumno> buscarPorPersonaCarreraAndActivo(Integer idPersona, Integer idPeriodo) {
		return alumnosRepo.findAllAlumnosByPersonaCarreraAndActivoAndPeriodo(idPersona, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Alumno> buscarPorCarreraAndPeriodoAndActivo(Integer idCarrera, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return alumnosRepo.findAllAlumnosByCarreraAndActivoAndPeriodo(idCarrera, idPeriodo);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Integer contarAlumnosRegularesPorGrupo(Integer idGrupo) {
		return alumnosRepo.countAlumnosRegularesByGrupo(idGrupo);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ProspectoDTO> buscarProspectosActivos() {
		return alumnosRepo.findAllActiveProspectos();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Alumno> buscarProspectosAceptados(Integer idCarrera, Integer idPeriodo) {
		return alumnosRepo.findAllAceptedProspectos(idCarrera, idPeriodo);
	}
	
	@Override
	@Transactional(readOnly = true)
	 public List<AlumnoNoReinscritoDTO> buscarNoReinscritosPorPersonaCarreraYPeriodo(Integer idPersona,
	   Integer idPeriodo) {
	  return alumnosRepo.findNoReinscritosByPersonaCarreraAndPeriodo(idPersona, idPeriodo);
	 }

	@Override
	@Transactional(readOnly = true)
	public List<AlumnoNoReinscritoDTO> BuscarNoReinscritosPorPeriodo(Integer idPeriodo) {
		return alumnosRepo.findNoReinscritosByPeriodo(idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer contarAlumnosPorSexoYGrupo(String sexo, Integer idGrupo) {
		return alumnosRepo.countAlumnosBySexoAndGrupo(sexo, idGrupo);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AlumnoAdeudoDTO> obtenerAlumnosAdeudoPorTodasCarreraYPeriodo(Integer idPeriodo) {
		return alumnosRepo.getAllAlumnoAdeudoByAllCarreraAndPeriodo(idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer contarAlumnosPorSexoYPersonaCarreraYPeriodo(String sexo, Integer idPersona, Integer idPeriodo) {
		return alumnosRepo.countAlumnosBySexoAndPersonaCarrera(sexo, idPersona, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer contarAlumnosPorCarreraYTurno(Integer idCarrera, Integer idPeriodo, Integer idTurno) {
		return alumnosRepo.countAlumnosByCarreraAndCarrera(idCarrera, idPeriodo, idTurno);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer contarInscritosPorCarreraYPeriodoYTurno(Integer idCarrera, Integer idPeriodo, Integer idTurno) {
		return alumnosRepo.countInscritosByCarreraAndPeriodoAndTurno(idCarrera, idPeriodo, idTurno);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer contarBajasPorCarreraYPeriodoYTurno(Integer idCarrera, Integer idPeriodo, Integer idTurno) {
		return alumnosRepo.countBajasByCarreraAndPeriodoAndTurno(idCarrera, idPeriodo, idTurno);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AlumnoRegularDTO> obtenerRegularesPorCarreraYPeriodoYTurno(Integer idCarrera, Integer idPeriodo,
			Integer idTurno) {
		return alumnosRepo.obtenerRegularesByCarreraAndPeriodoAndTurno(idCarrera, idPeriodo, idTurno);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AlumnoInfoDTO> buscarPorPersonaCarreraYPeriodoYNombreOMatricula(Integer idPersona, Integer idPeriodo,
			String nombre) {
		// TODO Auto-generated method stub
		return alumnosRepo.findByPersonaCarreraAndNombreAndPeriodo(idPersona, idPeriodo, nombre);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AlumnoInfoDTO> buscarPorPersonaCarreraYPeriodoYActivos(Integer idPersona, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return alumnosRepo.findByPersonaCarreraAndPeriodo(idPersona, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AlumnoInfoDTO> buscarTodosPorNombreOMatriculaYPeriodoYActivos(String Nombre, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return alumnosRepo.findAllByNombreOrMatriculaAndPeriodo(Nombre, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AlumnoInfoDTO> buscarTodosPorPeriodo(Integer idPeriodo) {
		// TODO Auto-generated method stub
		return alumnosRepo.findAllByPeriodo(idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AlumnoRegularDTO> buscarTodosProspectoReinscripcion(Integer carrera, Integer periodo) {
		return alumnosRepo.findAllRegularProspecto(carrera, periodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<RemedialAlumnoDTO> buscarTodosRemedial() {
		return alumnosRepo.findAllRemedial();
	}

	@Override
	@Transactional(readOnly = true)
	public List<AlumnoRegularDTO> buscarTodosPorCarreraYCuatrimestreYPeriodo(Integer idCarrera, Integer idCuatrimestre,
			Integer idPeriodo) {
		// TODO Auto-generated method stub
		return alumnosRepo.findAllByCarreraAndCuatrimestreAndPeriodo(idCarrera, idCuatrimestre, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer contarAlumnosPorGrupoYActivos(Integer idGrupo) {
		// TODO Auto-generated method stub
		return alumnosRepo.countAlumnosByGrupoAndActivo(idGrupo);
  }
	public List<AlumnoActivoDTO> buscarAlumnoYEstatusPorGrupo(Integer grupo) {
		return alumnosRepo.findAlumnoAndStatusByGrupo(grupo);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<AlumnoAdeudoDTO> reporteTodosAlumnosAdeudosPaginado(Integer periodo, Pageable pageable) {
		return alumnosRepo.reportAllAlumnosAdeudosPaginado(periodo, pageable);
	}

	@Override
	public Page<AlumnoAdeudoDTO> reporteAlumnosAdeudosPaginadoPorCarrera(Integer periodo, Integer carrera,
			Pageable pageable) {
		return alumnosRepo.reportAlumnosAdeudosPaginadoByCarrera(periodo, carrera, pageable);
	}

	@Override
	public List<AlumnoPromedioEscolaresDTO> buscarTodoPromedioEscolaresPorPeriodoYGrupo(Integer idPeriodo,
			Integer idGrupo) {
		// TODO Auto-generated method stub
		return alumnosRepo.findPromedioEcolaresByIdPeriodoAndIdGrupo(idPeriodo, idGrupo);
	}
}

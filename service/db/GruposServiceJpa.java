package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.repository.GruposRepository;
import edu.mx.utdelacosta.service.IGrupoService;

@Service
public class GruposServiceJpa implements IGrupoService{
	
	@Autowired
	private GruposRepository gruposRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Grupo> buscarTodosDeAlumnosOrdenPorPeriodoDesc(Integer idAlumno) {
		// TODO Auto-generated method stub
		return gruposRepository.findAllByAlumnoOrderByPeriodoDesc(idAlumno);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Grupo> buscarTodosDeAlumnosOrdenPorPeriodoAsc(Integer idAlumno) {
		// TODO Auto-generated method stub
		return gruposRepository.findAllByAlumnoOrderByPeriodoAsc(idAlumno);
	}

	@Override
	@Transactional(readOnly = true)
	public Grupo buscarUltimoDeAlumno(Integer idAlumno) {
		// TODO Auto-generated method stub
		return gruposRepository.findLastGrupoByAlumno(idAlumno);
	}

	@Override
	@Transactional(readOnly = true)
	public Grupo buscarPorId(Integer cveGrupo) {
		// TODO Auto-generated method stub
		Optional<Grupo> optional = gruposRepository.findById(cveGrupo);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Grupo> buscarTodosDeAlumnosyGruposOrdenPorPeriodoAsc(Integer idAlumno, Integer idCarrera) {
		return gruposRepository.findAllByAlumnoAndCarreraOrderByPeriodoDesc(idAlumno, idCarrera);
	}

	@Override
	@Transactional(readOnly = true)
	public Double obtenerPromedioAlumn(Integer idAlumno, Integer idGrupo) {
		// TODO Auto-generated method stub
		return gruposRepository.findAvgAlumno(idAlumno, idGrupo);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer buscarGrupoConsecutivo(String nombre, Integer periodo) {
		// TODO Auto-generated method stub
		return gruposRepository.findLastGrupoByNombreAndPeriodo(nombre, periodo);
	}

	
	@Override
	@Transactional(readOnly = true)
	public List<Grupo> buscarTodoPorPeriodoOrdenPorId(Integer idPeriodo) {
		// TODO Auto-generated method stub
		return (List<Grupo>)gruposRepository.findAllByPeriodoOrderByIdDesc(idPeriodo);
	}

	@Override
	public void guardar(Grupo grupo) {
		// TODO Auto-generated method stub
		gruposRepository.save(grupo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Grupo> buscarPorPeriodoyCarrera(Integer idPerido, Integer idCarrera) {
		// TODO Auto-generated method stub
		return gruposRepository.findAllByPeriodoAndCarreraOrderByNombre(idPerido, idCarrera);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Integer gruposPorProfesorYPeriodo(Integer idProfesor, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return gruposRepository.countGruposByProfesorAndPeriodo(idProfesor, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Grupo> buscarPorCuatrimestreCarreraYPeriodoYTurno(Integer idCuatrimestre, Integer idCarrera,
			Integer idPeriodo, Integer idTurno) {
		return gruposRepository.findByCuatrimestreAndCarreraAndPeriodoAndTurno(idCuatrimestre, idCarrera, idPeriodo, idTurno);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Grupo> buscarPorCuatrimestreCarreraYPeriodo(Integer idCuatrimestre, Integer idCarrera,
			Integer idPeriodo) {
		// TODO Auto-generated method stub
		return gruposRepository.findByCuatrimestreAndCarreraAndPeriodo(idCuatrimestre, idCarrera, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Grupo> buscarPorCarreraPeriodoAndPersonaCarrera(Integer persona, Integer periodo) {
		// TODO Auto-generated method stub
		return gruposRepository.findByCarreraAndPeriodoAndPersonaCarrera(persona, periodo);
	}

	//modificada
	@Override
	@Transactional(readOnly = true)
	public List<Grupo> buscarPorProfesorYPeriodoAsc(Persona profesor, Periodo periodo) {		
		return gruposRepository.findByProfesorAndPeriodoAndActivoTrueOrderByPeriodoAsc(profesor, periodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Grupo> buscarPorProfesorYPeriodo(Integer idProfesor, Integer idPersona) {
		return gruposRepository.findByProfesorAndPeriodo(idProfesor, idPersona);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Grupo buscarPorAlumnoPenultimoGrupo(Integer idAlumno) {
		List<Grupo> grupos = gruposRepository.findByAlumnoPenultimoGrupo(idAlumno);
		Grupo grupo;
		switch (grupos.size()) {
        case 2:
            grupo = grupos.get(1);
            break;
        case 1:
            grupo = grupos.get(0);
            break;
        default:
            grupo = new Grupo();
            break;
		}
		return grupo;
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean buscarPorGrupoYPeriodo(Integer idGrupo, Integer idPeriodo) {
		return gruposRepository.findByGrupoYPeriodo(idGrupo, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public Grupo buscarPorAlumnoYPeriodo(Integer idAlumno, Integer idPeriodo) {
		return gruposRepository.findByAlumnoYPeriodo(idAlumno, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public Grupo buscarUltimoGrupoLibre(Integer idPeriodo, Integer idCarrera, Integer idTurno) {
		return gruposRepository.findLastEmptyGrupo(idPeriodo, idCarrera, idTurno);
	}
	
	@Override
	@Transactional(readOnly = true)
	 public Grupo buscarUltimoGrupoCompatibleLibre(Integer idPeriodo, Integer idCarrera, Integer idTurno,
	   Integer consecutivo, Integer idCuatrimestre) {
	  return gruposRepository.findLastEmptyCompatibleGrupo(idPeriodo, idCarrera, idTurno, consecutivo, idCuatrimestre);
	 }
	
	@Override
	@Transactional(readOnly = true)
	public List<Grupo> buscarUltimosPorAlumno(Integer idAlumno) {
		return gruposRepository.findLast3ByAlumno(idAlumno);
	}

}

package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.repository.CargaHorariaRepository;
import edu.mx.utdelacosta.service.ICargaHorariaService;

@Service
public class CargaHorariaServiceJpa implements  ICargaHorariaService{
	
	@Autowired
	private CargaHorariaRepository cargaHorariasRepository;

	@Override
	@Transactional(readOnly = true)
	public List<CargaHoraria> buscarPorGrupo(Grupo grupo) {
		return cargaHorariasRepository.findByGrupoAndActivoTrueAndMateriaCurricularTrueAndMateriaCalificacionTrue(grupo);
	}

	@Override
	@Transactional(readOnly = true)
	public CargaHoraria buscarPorIdCarga(Integer id) {
		// TODO Auto-generated method stub
		Optional<CargaHoraria> optional = cargaHorariasRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}	
	
	@Override
	@Transactional(readOnly = true)
	public List<CargaHoraria> buscarPorProfesorYPeriodoYActivo(Persona profesor, Periodo periodo, Boolean activo) {
		return cargaHorariasRepository.findByProfesorAndPeriodoAndActivo(profesor, periodo, activo);
	}

	@Override
	@Transactional(readOnly = true)
	public void guardar(CargaHoraria cargaHoraria) {
		// TODO Auto-generated method stub
		cargaHorariasRepository.save(cargaHoraria);
	}

	@Override
	@Transactional(readOnly = true)
	public CargaHoraria buscarPorMateriaYPeriodoYGrupo(Integer materia, Integer periodo, Integer grupo) {
		// TODO Auto-generated method stub
		return cargaHorariasRepository.findByMateriaAndPeriodoAndGrupo(materia, periodo, grupo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CargaHoraria> buscarPorGrupoYPeriodo(Integer grupo, Integer periodo) {
		// TODO Auto-generated method stub
		return cargaHorariasRepository.findByGrupoAndPeriodo(grupo, periodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CargaHoraria> buscarPorProfesorYPeriodo(Persona profesor, Periodo periodo) {
		return cargaHorariasRepository.findByProfesorAndPeriodo(profesor, periodo);
	}

	@Override
	@Transactional(readOnly = true)
	public CargaHoraria buscarPorIdMateriaEIdPersona(Integer idMateria, Integer idPersona) {
		return cargaHorariasRepository.findByIdMateriaAndIdPersona(idMateria, idPersona);
	}
	
	@Override
	@Transactional(readOnly = true)
	 public List<CargaHoraria> buscarPorGrupoYProfesorYPeriodo(Integer idGrupo, Integer idProfesor, Integer idPeriodo) {
	  return cargaHorariasRepository.findByGrupoAndProfesorAndPeriodo(idGrupo, idProfesor, idPeriodo);
	 }
	
	@Override
	@Transactional(readOnly = true)
	public List<CargaHoraria> buscarPorCarreraProfesorMateriaYPeriodo(Integer idCarrera, Integer idProfesor, Integer idMateria, Integer idPeriodo) {
		return cargaHorariasRepository.findByCarreraProfesorAndMateriaAndPeriodo(idCarrera, idProfesor, idMateria, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CargaHoraria> buscarPorCarreraProfesorYPeriodo(Integer idCarrera, Integer idProfesor,Integer idPeriodo) {
		return cargaHorariasRepository.findByCarreraAndProfesorAndPeriodo(idCarrera, idProfesor, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CargaHoraria> buscarPorProfesorYPeriodoYCalendarioEvaluacion(Integer idProfesor, Integer idPeriodo,
			Integer idCarga) {
		return cargaHorariasRepository.findByPersonaAndPeriodoAndCalendarioEvaluacion(idProfesor, idPeriodo, idCarga);
	}

	@Override
	public List<CargaHoraria> buscarPorGrupoYPeriodoYCalificacionSi(Integer idGrupo, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return cargaHorariasRepository.findByGrupoAndPeriodoAndCalificacionSi(idGrupo, idPeriodo);
	}

	@Override
	public List<CargaHoraria> buscarPorProfesorYCarreraYPeriodo(Integer idProfesor, Integer idCarrera,
			Integer idPeriodo) {
		// TODO Auto-generated method stub
		return cargaHorariasRepository.findByIdProfesorAndIdCarreraAndIdPeriodo(idProfesor, idCarrera, idPeriodo);
	}
}

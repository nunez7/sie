package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Materia;
import edu.mx.utdelacosta.model.PlanEstudio;
import edu.mx.utdelacosta.repository.MateriasRepository;
import edu.mx.utdelacosta.service.IMateriasService;

@Service
public class MateriasServiceJpa  implements IMateriasService{

	@Autowired
	private MateriasRepository materiasRepository;

	@Override
	@Transactional(readOnly = true)
	public Materia buscarPorId(Integer id) {
		Optional<Materia> materia = materiasRepository.findById(id);
		if(materia.isPresent()) {
			return materia.get();
		}
		return null;
	}

	@Override
	public void guardar(Materia materia) {
		// TODO Auto-generated method stub
		materiasRepository.save(materia);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Materia> buscarTodas() {
		// TODO Auto-generated method stub
		return materiasRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Materia buscarPorPlanEstudioYNombre(PlanEstudio plan, String nombre) {
		Optional<Materia> materia = materiasRepository.findByPlanEstudioAndNombre(plan, nombre); 
		if(materia.isPresent()) {
			return materia.get();
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Materia buscarPorPlanEstudioYAbreviatura(PlanEstudio plan, String abreviatura) {
		Optional<Materia> materia = materiasRepository.findByPlanEstudioAndAbreviatura(plan, abreviatura);
		if(materia.isPresent()) {
			return materia.get();
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Materia> buscarPorGrupoYCarreraYActivos(Integer grupo, Integer carrera) {
		// TODO Auto-generated method stub
		return materiasRepository.findByGrupoAndCarreraAndActivo(grupo, carrera);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Materia> buscarPorGrupoYCarrera(Integer grupo, Integer carrera) {
		// TODO Auto-generated method stub
		return materiasRepository.findByGrupoAndCarrera(grupo, carrera);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Materia> buscarPorCargaActivaEnGrupo(Integer grupo, Integer carrera) {
		// TODO Auto-generated method stub
		return materiasRepository.findByCargaActivaGrupo(grupo, carrera);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Materia> buscarPorCarreraProfesorYPeriodo(Integer idCarrera, Integer idProfesor, Integer idPeriodo) {
		return materiasRepository.findByCarreraAndProfesorAndPeriodo(idCarrera, idProfesor, idPeriodo);
	}
	
	

}

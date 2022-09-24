package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.AsistenciaTemaGrupal;
import edu.mx.utdelacosta.model.TemaGrupal;
import edu.mx.utdelacosta.model.dto.AlumnoAsistenciaDTO;
import edu.mx.utdelacosta.repository.AsistenciaTemaGrupalRepository;
import edu.mx.utdelacosta.service.IAsistenciaTemaGrupalService;

@Service
public class AsistenciaTemaGrupalServiceJpa implements IAsistenciaTemaGrupalService{

	@Autowired
	private AsistenciaTemaGrupalRepository asiTemaGruRepo;

	@Override
	@Transactional(readOnly = true)
	public List<AlumnoAsistenciaDTO> buscarPorTemaGrupalYGrupo(Integer idGrupo, Integer idTemaGrupal) {
		return asiTemaGruRepo.findByTemaGrupalAndGrupo(idGrupo, idTemaGrupal);
	}

	@Override
	public void guardar(AsistenciaTemaGrupal asistencia) {
		asiTemaGruRepo.save(asistencia);
	}

	@Override
	@Transactional(readOnly = true)
	public AsistenciaTemaGrupal buscarPorTemaGrupalYAlumno(TemaGrupal temaGrupal, Alumno alumno) {
		return asiTemaGruRepo.findByTemaGrupalAndAlumno(temaGrupal, alumno);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AsistenciaTemaGrupal> buscarPorTemaGrupal(TemaGrupal temaGrupal) {
		return asiTemaGruRepo.findByTemaGrupal(temaGrupal);
	}

	@Override
	@Transactional
	public void eliminar(AsistenciaTemaGrupal asistencia) {
		asiTemaGruRepo.delete(asistencia);
	}
	
}

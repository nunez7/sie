package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.AlumnoGrupo;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.repository.AlumnoGrupoRepository;
import edu.mx.utdelacosta.service.IAlumnoGrupoService;

@Service
public class AlumnoGrupoServiceJpa implements IAlumnoGrupoService{
	
	@Autowired
	private AlumnoGrupoRepository agRepositorio;

	@Override
	public AlumnoGrupo buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		Optional<AlumnoGrupo> optional =agRepositorio.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public void guardar(AlumnoGrupo alumnogrupo) {
		// TODO Auto-generated method stub
		agRepositorio.save(alumnogrupo);
	}

	@Override
	public AlumnoGrupo checkInscrito(Integer idAlumno, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return agRepositorio.checkInscrito(idAlumno, idPeriodo);
	}

	@Override
	public AlumnoGrupo buscarPorAlumnoYGrupo(Alumno alumno, Grupo grupo) {
		// TODO Auto-generated method stub
		return agRepositorio.findByAlumnoAndGrupo(alumno, grupo);
	}
	
	@Override
	public AlumnoGrupo buscarPorIdAlumnoYidGrupo(Integer idAlumno, Integer idGrupo) {
		// TODO Auto-generated method stub
		return agRepositorio.findByIdAlumnoAndIdGrupo(idAlumno, idGrupo);
	}
	
	@Override
	public List<AlumnoGrupo> buscarPorIdAlumnoDesc(Integer idAlumno) {
		// TODO Auto-generated method stub
		return agRepositorio.findAllByIdAlumnoDesc(idAlumno);
	}

	@Override
	public void eliminar(AlumnoGrupo alumnoGrupo) {
		agRepositorio.delete(alumnoGrupo);
		
	}
	
	@Override
	 public List<AlumnoGrupo> buscarPorAlumnoYPeriodo(Integer idAlumno, Integer idPeriodo) {
	  // TODO Auto-generated method stub
	  return agRepositorio.findByIdAlumnoAndIdPeriodo(idAlumno, idPeriodo);
	 }
	

}

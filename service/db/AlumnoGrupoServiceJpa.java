package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional(readOnly = true)
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
	@Transactional(readOnly = true)
	public AlumnoGrupo checkInscrito(Integer idAlumno, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return agRepositorio.checkInscrito(idAlumno, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public AlumnoGrupo buscarPorAlumnoYGrupo(Alumno alumno, Grupo grupo) {
		// TODO Auto-generated method stub
		return agRepositorio.findByAlumnoAndGrupo(alumno, grupo);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AlumnoGrupo buscarPorIdAlumnoYidGrupo(Integer idAlumno, Integer idGrupo) {
		// TODO Auto-generated method stub
		return agRepositorio.findByIdAlumnoAndIdGrupo(idAlumno, idGrupo);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AlumnoGrupo> buscarPorIdAlumnoDesc(Integer idAlumno) {
		// TODO Auto-generated method stub
		return agRepositorio.findAllByIdAlumnoDesc(idAlumno);
	}

	@Override
	@Transactional
	public void eliminar(AlumnoGrupo alumnoGrupo) {
		agRepositorio.delete(alumnoGrupo);
		
	}
	
	@Override
	@Transactional(readOnly = true)
	 public List<AlumnoGrupo> buscarPorAlumnoYPeriodo(Integer idAlumno, Integer idPeriodo) {
	  // TODO Auto-generated method stub
	  return agRepositorio.findByIdAlumnoAndIdPeriodo(idAlumno, idPeriodo);
	 }

	 @Override
	 @Transactional(readOnly = true)
	public Integer contarAlumnosGruposPorGrupo(Integer idGrupo) {
		return agRepositorio.countAlumnosByGrupo(idGrupo);
	}

	@Override
	@Transactional(readOnly = true)
	public AlumnoGrupo buscarPrimerGrupoProspecto(Integer idAlumno) {
		return agRepositorio.findFirstGrupoProspecto(idAlumno);
	}	
	
	@Override
	@Transactional(readOnly = true)
	public Integer contarPorPeriodoYCuatrimestreYPagoGenerado(Integer idPeriodo, Integer idCuatrimestre) {
		return agRepositorio.countByPeriodoAndCuatrimestreAndPagoGenerado(idPeriodo, idCuatrimestre);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AlumnoGrupo> buscarPorPeriodoYCuatrimestre(Integer idPeriodo, Integer idCuatrimestre) {
		return agRepositorio.findByPeriodoAndCuatrimestre(idPeriodo, idCuatrimestre);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Integer contarPorPeriodoAndCuatrimestre(Integer idPeriodo, Integer idCuatrimestre) {
		return agRepositorio.contarPorPeriodoAndCuatrimestre(idPeriodo, idCuatrimestre);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer contarGruposActivosPorAlumno(Integer idAlumno) {
		// TODO Auto-generated method stub
		return agRepositorio.countByAlumnoAndGrupoActivo(idAlumno);
	}

	@Override
	public AlumnoGrupo buscarPorIdAlumnoYIdPeriodo(Integer idAlumno, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return agRepositorio.findByAlumnoAndPeriodo(idAlumno, idPeriodo);
	}

}

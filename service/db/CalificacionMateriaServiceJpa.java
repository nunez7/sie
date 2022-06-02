package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.CalificacionMateria;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.dtoreport.MateriaPromedioDTO;
import edu.mx.utdelacosta.repository.CalificacionMateriaRepository;
import edu.mx.utdelacosta.service.ICalificacionMateriaService;

@Service
public class CalificacionMateriaServiceJpa implements ICalificacionMateriaService {

	@Autowired
	private CalificacionMateriaRepository calificacionMateriaRepository;

	@Override
	public CalificacionMateria buscarPorCargayAlumno(CargaHoraria cargaHoraria, Alumno alumno) {
		// TODO Auto-generated method stub
		return calificacionMateriaRepository.findByCargaHorariaAndAlumno(cargaHoraria, alumno);
	}

	@Override
	public List<MateriaPromedioDTO> buscarPorGrupoAlumno(Integer idGrupo, Integer idAlumno) {
		// TODO Auto-generated method stub
		return calificacionMateriaRepository.findByGrupoAlumno(idGrupo, idAlumno);
	}

	@Override
	public void guardar(CalificacionMateria calificacionMateria) {
		calificacionMateriaRepository.save(calificacionMateria);
	}

	@Override
	public List<CalificacionMateria> buscarPorIdAlumnoEIdPeriodo(Integer idAlumno, Integer idPeriodo) {
		return calificacionMateriaRepository.findByIdAlumnoAndIdPeriodo(idAlumno, idPeriodo);
	}

	@Override
	public String buscarPorAlumnoYCarga(Integer idAlumno, Integer idCargaHoraria) {
		String estatus = calificacionMateriaRepository.findEstatusByAlumnoAndCargaHoraria(idAlumno, idCargaHoraria);
		if (estatus != null) {
			return estatus;
		}
		return "NA";
	}

	@Override
	public List<CalificacionMateria> buscarPorIdGrupoEIdPersona(Integer idGrupo, Integer idPeriodo) {
		return calificacionMateriaRepository.findByIdGrupoAndIdPeriodo(idGrupo, idPeriodo);
	}

	@Override
	public Float buscarCalificacionPorAlumnoYCargaHoraria(Integer idAlumno, Integer idCargaHoraria) {
		return calificacionMateriaRepository.findCalificacionByAlumnoAndCargaHoraria(idAlumno, idCargaHoraria);
	}

}

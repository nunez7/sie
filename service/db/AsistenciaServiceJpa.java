package edu.mx.utdelacosta.service.db;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Asistencia;
import edu.mx.utdelacosta.model.Horario;
import edu.mx.utdelacosta.repository.AsistenciaRepository;
import edu.mx.utdelacosta.service.IAsistenciaService;

@Service
public class AsistenciaServiceJpa implements IAsistenciaService{
	
	@Autowired
	private AsistenciaRepository asistenciaRepo;

	@Override
	public List<Date> mesesEntreFechaInicioYFechaFinAsc(Date fechaInicio, Date fechaFin) {
		// TODO Auto-generated method stub
		return asistenciaRepo.findAllByFechaInicioFechafinOrderByFechaAsc(fechaInicio, fechaFin);
	}

	@Override
	public List<Date> diasEntreFechaInicioYFechaFin(String fechaInicio, String fechaFin) {
		// TODO Auto-generated method stub
		return asistenciaRepo.findAllByFechaInicioFechafin(fechaInicio, fechaFin);
	}

	@Override
	public List<Asistencia> buscarPorGrupoYalumno(Integer idGrupo, Integer idAlumno) {
		// TODO Auto-generated method stub
		return asistenciaRepo.findAllByGrupoAndAlumno(idGrupo, idAlumno);
	}
	
	@Override
	 public List<Asistencia> buscarPorFechaInicioYFechaFinEIdCargaHorariaEIdGrupo(Date fechaInicio, Date fechaFin, Integer idCargaHoraria, Integer idGrupo) {
	  return asistenciaRepo.findByFechaInicioAndFechaFinAndIdCargaHorariaAndIdGrupo(fechaInicio, fechaFin, idCargaHoraria, idGrupo);
	 }
	
	@Override
	public List<Asistencia> buscarTodasPorHorario(Horario horario) {
			return asistenciaRepo.findAllByHorario(horario);
	}

	@Override
	public void guardar(Asistencia asistencia) {
		asistenciaRepo.save(asistencia);
	}

	@Override
	public Integer contarPorFechaInicioYFechaFindYCargaHoraria(Date fechaInicio, Date fechaFin,
			Integer idCargaHoraria) {
		return asistenciaRepo.countByFechaInicioAndFechaFindAndCargaHoraria(fechaInicio, fechaFin, idCargaHoraria);
	}

	@Override
	public List<Asistencia> buscarPorFechaYHorario(Date fecha, Horario horario) {
		return asistenciaRepo.findByFechaAndHorario(fecha, horario);
	}

	@Override
	public Asistencia buscarPorFechaYAlumnoYHorario(Date fecha, Alumno alumno, Horario horario) {
		return asistenciaRepo.findByFechaAndAlumnoAndHorario(fecha, alumno, horario);
	}

	@Override
	public Asistencia buscarPorId(Integer id) {
		Optional <Asistencia> optional = asistenciaRepo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public List<Asistencia> buscarFaltasPorIdAlumnoYIdCargaHoraria(Integer idAlumno, Integer idCargaHoraria, Date fechaInicio, Date fechaFin) {
		return asistenciaRepo.findFaltasByAlumnoAndCargaHoraria(idAlumno, idCargaHoraria, fechaInicio, fechaFin);
	}

	@Override
	public List<Asistencia> buscarRetardosPorIdAlumnoYIdCargaHoraria(Integer idAlumno, Integer idCargaHoraria) {
		return asistenciaRepo.findRetardosByAlumnoAndCargaHoraria(idAlumno, idCargaHoraria);
	}

	@Override
	public Integer contarAsistenciasPorAlumnoYCargaHorariaYCorteEvaluativo(Integer idAlumno, Integer idCargaHoraria,  Date fechaInicio, Date fechaFin) {
		return asistenciaRepo.countAsistenciasByAlumnoAndCargaHorariaAndCorteEvalutivo(idAlumno, idCargaHoraria, fechaInicio, fechaFin);
	}

	@Override
	public Integer contarFaltasPorAlumnoYCargaHorariaYCorteEvaluativo(Integer idAlumno, Integer idCargaHoraria,
			Date fechaInicio, Date fechaFin) {
		return asistenciaRepo.countFaltasByAlumnoAndCargaHorariaAndCorteEvalutivo(idAlumno, idCargaHoraria, fechaInicio, fechaFin);
	}
}

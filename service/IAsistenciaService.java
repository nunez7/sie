package edu.mx.utdelacosta.service;

import java.util.Date;
import java.util.List;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Asistencia;
import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Horario;

public interface IAsistenciaService {	
	List<Date> mesesEntreFechaInicioYFechaFinAsc(Date fechaInicio, Date fechaFin);	
	
	//List<Date> diasEntreFechaInicioYFechaFin(String fechaInicio, String fechaFin);
	
	List<Asistencia> buscarPorGrupoYalumno(Integer idGrupo, Integer idAlumno);
	
	List<Asistencia> buscarPorFechaInicioYFechaFinEIdCargaHorariaEIdGrupo(Date fechaInicio, Date fechaFin, Integer idCargaHoraria, Integer idGrupo);

	List<Asistencia> buscarTodasPorHorario(Horario horario);
	
	void guardar (Asistencia asistencia);
		
	Integer contarPorFechaInicioYFechaFindYCargaHoraria(Date fechaInicio, Date fechaFin, Integer idCargaHoraria);
	
	List<Asistencia> buscarPorFechaYHorario(Date fecha, Horario horario);
	
	Asistencia buscarPorFechaYAlumnoYHorario(Date fecha, Alumno alumno, Horario horario);
	
	Asistencia buscarPorId (Integer id);
	
	List<Asistencia> buscarFaltasPorIdAlumnoYIdCargaHoraria(Integer idAlumno, Integer idCargaHoraria, Date fechaInicio, Date fechaFin);

	List<Asistencia> buscarRetardosPorIdAlumnoYIdCargaHoraria(Integer idAlumno, Integer idCargaHoraria);
	
	Integer contarAsistenciasPorCargaYCorte(CargaHoraria carga, CorteEvaluativo corte);
	
	Integer contarAsistenciasPorAlumnoYCargaHorariaYCorteEvaluativo(Integer idAlumno, Integer idCargaHoraria, Date fechaInicio, Date fechaFin);
	
	Integer contarFaltasPorAlumnoYCargaHorariaYCorteEvaluativo(Integer idAlumno, Integer idCargaHoraria, Date fechaInicio, Date fechaFin);
	
	Integer contarTotalPorAlumnoYCargaHorariaYFecha(Integer alumno, Integer cargaHoraria, Date fecha);
	
	List<Date> diasEntreFechaInicioYFechaFin(Date fechaInicio, Date fechaFin);
}

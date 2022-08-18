package edu.mx.utdelacosta.service;

import java.util.Date;
import java.util.List;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Baja;

public interface IBajaService {
	void guardar(Baja baja);
	Baja buscarPorId(Integer id);
	Baja buscarPorEstadoAlumnoYFechaAutorizacion(Integer estatus, Alumno alumno, Date fecha);
	List<Baja> buscarPorPersonaYEstatus(Integer idPersona, Integer estatus);
	List<Baja> buscarPorTipoYStatus(Integer tipo, Integer estatus);
	List<Baja> buscarPorTipoStatusGrupoYPeriodo(Integer tipo, Integer estatus, Integer idGrupo, Integer idPeriodo);
	List<Baja> buscarPorTipoStatusCarreraYPeriodo(Integer tipo, Integer estatus, Integer idCarrera, Integer idPeriodo);
	
	List<Baja> buscarPorTipoStatusCarreraEntreFechas(Integer tipo, Integer estatus, Integer idCarrera, Date fechaInicio, Date fechaFin);
	List<Baja> buscarPorTipoStatusPersonaEntreFechas(Integer tipo, Integer estatus, Integer idPersona, Date fechaInicio, Date fechaFin);
	List<Baja> buscarPorTipoStatusEntreFechas(Integer tipo, Integer estatus, Date fechaInicio, Date fechaFin);
	
	List<Baja> buscarPorAlumno(Alumno alumno);
	
	void eliminar(Baja baja);
}

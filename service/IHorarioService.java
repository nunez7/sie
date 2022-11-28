package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Horario;
import edu.mx.utdelacosta.model.dto.HorarioDTO;

public interface IHorarioService {	
	List<Horario> buscarPorGrupoDesc(Integer idGrupo);
	List<Horario> buscarPorGrupoDistinctPorHoraInicio(Integer idGrupo);
	HorarioDTO buscarPorHoraInicioDiaYGrupo(String horaInicio, Integer dia, Integer grupo);
	
	void guardar(Horario horario);
	List<Horario> buscarPorProfesorDistinctPorHoraInicio(Integer idProfesor, Integer idPeriodo);
	Horario buscarPorId(Integer id);
	List<Horario> buscarPorCargaHoraria(CargaHoraria carga);
	//para comparar si ya hay un horario para el profesor en ese dia y hora
	Horario buscarPorHoraInicioHorafinYPeriodoYProfesorYDia(String horaInicio, String horaFin, Integer idPeriodo, Integer idProfesor, Integer idDia);
	//para comparar si el grupo ya hay un horario para el grupo en ese dia y hora
	Horario buscarPorHoraInicioHoraFinYGrupoYdia(String horaInicio, String horaFin, Integer idGrupo, Integer idDia);
	//para sacar los horarios por hora, grupo y dia
	HorarioDTO buscarPorHoraInicioDiaYProfesor(String horaInicio, Integer dia, Integer idProfesor, Integer idPeriodo);

	List<Horario> buscarPorIdCargaHorariaEIdDia (Integer IdCargaHoraria, Integer IdDia);

	List<HorarioDTO> buscarPorProfesor(Integer idProfesor);
}

package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Horario;
import edu.mx.utdelacosta.model.dto.HorarioDTO;
import edu.mx.utdelacosta.repository.HorarioRepository;
import edu.mx.utdelacosta.service.IHorarioService;

@Service
public class HorarioServiceJpa implements IHorarioService{
	
	@Autowired
	private HorarioRepository horariosRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Horario> buscarPorGrupoDesc(Integer idGrupo) {
		return horariosRepository.findAllByGrupoActivoTrueOrderByHoraInicioDesc(idGrupo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Horario> buscarPorGrupoDistinctPorHoraInicio(Integer idGrupo) {
		// TODO Auto-generated method stub
		return horariosRepository.findDistinctHoraInicioByGrupoActivoTrueOrderByHoraInicioAsc(idGrupo);
	}

	@Override
	@Transactional(readOnly = true)
	public HorarioDTO buscarPorHoraInicioDiaYGrupo(String horaInicio, Integer dia, Integer grupo) {
		// TODO Auto-generated method stub
		return horariosRepository.findAllByHoraInicioAndDiaAndGrupoActivoTrue(horaInicio, dia, grupo);
	}

	@Override
	@Transactional
	public void guardar(Horario horario) {
		horariosRepository.save(horario);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Horario> buscarPorProfesorDistinctPorHoraInicio(Integer idProfesor, Integer idPeriodo) {
		// TODO Auto-generated method stub
		return horariosRepository.buscarPorProfesorDistinctPorHoraInicio(idProfesor, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public Horario buscarPorId(Integer id) {
		Optional<Horario> optional = horariosRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Horario> buscarPorCargaHoraria(CargaHoraria carga) {
		// TODO Auto-generated method stub
		return horariosRepository.findByCargaHorariaAndActivoTrue(carga);
	}

	@Override
	@Transactional(readOnly = true)
	public HorarioDTO buscarPorHoraInicioDiaYProfesor(String horaInicio, Integer dia, Integer idProfesor,
			Integer idPeriodo) {
		// TODO Auto-generated method stub
		return horariosRepository.findAllByHoraInicioAndDiaAndProfesorAndPeriodo(horaInicio, dia, idProfesor, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public Horario buscarPorHoraInicioHorafinYPeriodoYProfesorYDia(String horaInicio, String horaFin, Integer idPeriodo,
			Integer idProfesor, Integer idDia) {
		// TODO Auto-generated method stub
		return horariosRepository.findByHoraInicioAndHoraFinAndPeriodoAndProfesorAndDiaAndActivo(horaInicio, horaFin, idPeriodo, idProfesor, idDia);
	}

	@Override
	@Transactional(readOnly = true)
	public Horario buscarPorHoraInicioHoraFinYGrupoYdia(String horaInicio, String horaFin, Integer idGrupo,
			Integer idDia) {
		// TODO Auto-generated method stub
		return horariosRepository.findByHoraInicioandHoraFinAndGrupoAndDiaAndActivo(horaInicio, horaFin, idGrupo, idDia);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Horario> buscarPorIdCargaHorariaEIdDia(Integer IdCargaHoraria, Integer IdDia) {
		return horariosRepository.findByCargaHorariaAndDia(IdCargaHoraria, IdDia);
	}

	@Override
	@Transactional(readOnly = true)
	public List<HorarioDTO> buscarPorProfesor(Integer idProfesor) {
		return horariosRepository.findByIdProfesor(idProfesor);
	}
}

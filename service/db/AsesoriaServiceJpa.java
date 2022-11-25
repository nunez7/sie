package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Asesoria;
import edu.mx.utdelacosta.model.AsesoriaSolicitud;
import edu.mx.utdelacosta.model.Grupo;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.dtoreport.AsesoriaDTO;
import edu.mx.utdelacosta.repository.AsesoriaRepository;
import edu.mx.utdelacosta.repository.AsesoriaSolicitudRepository;
import edu.mx.utdelacosta.service.IAsesoriaService;

@Service
public class AsesoriaServiceJpa implements IAsesoriaService{

	@Autowired
	private AsesoriaRepository asesoriaRepository;
	
	@Autowired
	private AsesoriaSolicitudRepository asesoriaSolicitudRepository;
	
	@Override
	@Transactional
	public void guardar(Asesoria asesoria) {
		asesoriaRepository.save(asesoria);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AsesoriaDTO> buscarPorIdGrupoYPeriodo(Integer idGrupo, Integer idPeriodo) {
		return asesoriaRepository.findByIdGrupo(idGrupo, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AsesoriaDTO> buscarPorPersonaCarreraAndPeriodo(Integer idPersona, Integer idPeriodo) {
		return asesoriaRepository.findByPersonaCarreraAndPeriodo(idPersona, idPeriodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Asesoria> buscarPorGrupoPeriodoYTipo(Integer idGrupo, Integer idPeriodo, Integer tipo) {
		return asesoriaRepository.findByGrupoAndPeriodoAndTipo(idGrupo, idPeriodo, tipo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Asesoria> buscarPorGrupoPeriodoCargaYTipo(Integer idGrupo, Integer idPeriodo, Integer idCarga, Integer tipo) {
		return asesoriaRepository.findByGrupoAndPeriodoAndCargaAndTipo(idGrupo, idPeriodo, idCarga, tipo);
	}

	@Override
	@Transactional
	public void guardarAsesoriaSolicitud(AsesoriaSolicitud asesoria) {
		asesoriaSolicitudRepository.save(asesoria);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AsesoriaSolicitud> buscarAsesoriasSolicitudPorGrupo(Integer idGrupo) {
		return asesoriaSolicitudRepository.findByIdGrupoOrderByFecha(idGrupo);
	}

	@Override
	public List<AsesoriaDTO> buscarIndividualesPorGrupo(Grupo grupo, Persona persona) {
		return asesoriaRepository.findIndividualesByGrupo(grupo, persona);
	}

	@Override
	public List<AsesoriaDTO> buscarGrupalesPorGrupo(Grupo grupo, Persona persona) {
		return asesoriaRepository.findByGrupalesByGrupo(grupo, persona);
	}
}

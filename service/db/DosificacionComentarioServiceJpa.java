package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.DosificacionComentario;
import edu.mx.utdelacosta.repository.DosificacionesComentariosRepository;
import edu.mx.utdelacosta.service.IDosificacionComentarioService;

@Service
public class DosificacionComentarioServiceJpa implements IDosificacionComentarioService{
	
	@Autowired
	private DosificacionesComentariosRepository dosiComenRepo;
	
	@Override
	public List<DosificacionComentario> buscarPorIdPersona(Integer idPersona) {
		return dosiComenRepo.findByIdPersona(idPersona);
	}

	@Override
	public List<DosificacionComentario> buscarPorIdCargaHoraria(Integer idCargaHoraria) {
		return dosiComenRepo.findByIdCargaHoraria(idCargaHoraria);
	}

	@Override
	public void guardar(DosificacionComentario dosificacionComentario) {
		dosiComenRepo.save(dosificacionComentario);
	}
	
	@Override
	public Integer contarPorProfesorYPeriodo(Integer idProfesor, Integer idPeriodo) {
		return dosiComenRepo.countByProfesorAndPeriodo(idPeriodo, idProfesor);
	}

}

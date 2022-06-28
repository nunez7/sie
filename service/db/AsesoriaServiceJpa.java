package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Asesoria;
import edu.mx.utdelacosta.model.dto.AsesoriaDTO;
import edu.mx.utdelacosta.repository.AsesoriaRepository;
import edu.mx.utdelacosta.service.IAsesoriaService;

@Service
public class AsesoriaServiceJpa implements IAsesoriaService{

	@Autowired
	private AsesoriaRepository asesoriaRepository;
	
	@Override
	public void guardar(Asesoria asesoria) {
	asesoriaRepository.save(asesoria);
		
	}

	@Override
	public List<AsesoriaDTO> buscarPorIdGrupo(Integer idGrupo) {
		return asesoriaRepository.findByIdGrupo(idGrupo);
	}

	@Override
	public List<AsesoriaDTO> buscarPorPersonaCarrera(Integer idPersona) {
		// TODO Auto-generated method stub
		return asesoriaRepository.findByPersonaCarrera(idPersona);
	}

}

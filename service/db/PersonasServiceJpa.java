package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.dto.AlumnoPersonalDTO;
import edu.mx.utdelacosta.repository.PersonasRepository;
import edu.mx.utdelacosta.service.IPersonaService;

@Service
public class PersonasServiceJpa implements IPersonaService{
	
	@Autowired
	private PersonasRepository personasRepo;

	@Override
	public void guardar(Persona persona) {
		// TODO Auto-generated method stub
		personasRepo.save(persona);
	}

	@Override
	public List<Persona> buscarTodas() {
		// TODO Auto-generated method stub
		return (List<Persona>) personasRepo.findAll();
	}

	@Override
	public Persona buscarPorId(Integer idPersona) {
		// TODO Auto-generated method stub
		Optional<Persona> optional = personasRepo.findById(idPersona);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public Persona buscarPorEmail(String email) {
		// TODO Auto-generated method stub
		return personasRepo.findByEmail(email);
	}

	@Override
	public Integer buscarEmailExistente(String email) {
		// TODO Auto-generated method stub
		return personasRepo.findExitenciaEmail(email);
	}

	@Override
	 public List<Persona> buscarPorPersonaCarreraAndPeriodo(Integer persona, Integer periodo) {
	  // TODO Auto-generated method stub
	  return personasRepo.findByPersonaCarreraAndPeriodo(persona, periodo);
	 }
	
	@Override
	public List<Persona> buscarProfesoresPorCarreraYPeriodo(Integer idCarrera, Integer idPeriodo) { 
		return personasRepo.findProfesoresByCarreraAndPeriodo(idCarrera, idPeriodo);
	}
	
	@Override
	public List<Persona> buscarCajeros() {
		// TODO Auto-generated method stub
		return personasRepo.findAllCajeros();
	}

	@Override
	public List<AlumnoPersonalDTO> buscarPorMatriculaONoEmpledoONombre(String like) {
		// TODO Auto-generated method stub
		return personasRepo.findByNombreOrNoEmpleadoOrMatricula(like);
	}

	@Override
	public Persona buscarDirectorPorCarga(Integer idCargaHoraria) {
		return personasRepo.findDirectorCarreraByCarga(idCargaHoraria);
	}

	@Override
	public List<Persona> buscarColaboradoresPorDosificacion(Integer dosificacion) {
		return personasRepo.findColaboradoresByDosificacion(dosificacion);
	}
  
}

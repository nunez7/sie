package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Personal;
import edu.mx.utdelacosta.repository.PersonalRepository;
import edu.mx.utdelacosta.service.IPersonalService;

@Service
public class PersonalServiceJpa implements IPersonalService{
	
	@Autowired
	private PersonalRepository personalRepo;
	
	@Override
	public Personal buscarPorPersona(Persona persona) {
		// TODO Auto-generated method stub
		return personalRepo.findByPersona(persona);
	}

	@Override
	public List<Personal> buscarTodos() {
		// TODO Auto-generated method stub
		return (List<Personal>) personalRepo.findAll(Sort.by(Order.desc("id")));
	}

	@Override
	public List<Personal> buscarTodosPorNombre() {
		// TODO Auto-generated method stub
		return personalRepo.findAllByPersonaOrderByPuesto();
	}

	@Override
	public List<Personal> buscarJefes() {
		// TODO Auto-generated method stub
		return personalRepo.findAllBoss();
	}

	@Override
	public void guardar(Personal personal) {
		// TODO Auto-generated method stub
		personalRepo.save(personal);
	}

	@Override
	public Personal buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		Optional<Personal> optional = personalRepo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public List<Personal> buscarProfesores() {
		// TODO Auto-generated method stub
		return personalRepo.listaProfesores();
	}

}

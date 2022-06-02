package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.PlanEstudio;
import edu.mx.utdelacosta.repository.PlanEstudioRepository;
import edu.mx.utdelacosta.service.IPlanEstudioService;

@Service
public class PlanEstudioServiceJpa implements IPlanEstudioService{

	@Autowired
	private PlanEstudioRepository planEstudioRepository;
	
	@Override
	public void guardar(PlanEstudio planEstudio) {
		// TODO Auto-generated method stub
		planEstudioRepository.save(planEstudio);
	}

	@Override
	public PlanEstudio buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		Optional<PlanEstudio> optional = planEstudioRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
		
	}

	@Override
	public List<PlanEstudio> buscarPorPersonaCarrera(Integer idPersona) {
		// TODO Auto-generated method stub
		return planEstudioRepository.findByPersonaAndCarrera(idPersona);
	}

	@Override
	public List<PlanEstudio> buscarPorCarrera(Integer idCarrera) {
		// TODO Auto-generated method stub
		return planEstudioRepository.findByCarrera(idCarrera);
	}

	@Override
	public List<PlanEstudio> buscarTodos() {
		// TODO Auto-generated method stub
		return planEstudioRepository.findAll(Sort.by(Order.desc("id")));
	}

	@Override
	public List<PlanEstudio> buscarTodosOrdenPorCarrerayNivel() {
		// TODO Auto-generated method stub
		return planEstudioRepository.findAllOrderByCarreraNivel();
	}

}

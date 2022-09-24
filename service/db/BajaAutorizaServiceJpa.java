package edu.mx.utdelacosta.service.db;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Baja;
import edu.mx.utdelacosta.model.BajaAutoriza;
import edu.mx.utdelacosta.repository.BajaAutorizaRepository;
import edu.mx.utdelacosta.service.IBajaAutorizaService;

@Service
public class BajaAutorizaServiceJpa implements IBajaAutorizaService{

	@Autowired
	private BajaAutorizaRepository bajaAutoRepo;

	@Override
	public void guardar(BajaAutoriza bajaAutorizada) {
		bajaAutoRepo.save(bajaAutorizada);
	}
	
	@Override
	@Transactional(readOnly = true)
	public BajaAutoriza buscarPorId(Integer id) {
		Optional<BajaAutoriza> optional = bajaAutoRepo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public BajaAutoriza buscarPorBaja(Baja baja) {
		return bajaAutoRepo.findByBaja(baja);
	}

	@Override
	@Transactional
	public void eliminar(BajaAutoriza bajaAutorizada) {
		bajaAutoRepo.delete(bajaAutorizada);
	}
}

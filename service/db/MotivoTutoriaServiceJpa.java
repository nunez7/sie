package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.MotivoTutoria;
import edu.mx.utdelacosta.repository.MotivoTutoriaRepository;
import edu.mx.utdelacosta.service.IMotivoTutoriaService;

@Service
public class MotivoTutoriaServiceJpa implements IMotivoTutoriaService{
	
	@Autowired
	private MotivoTutoriaRepository moTutoRepo;

	@Override
	public void guardar(MotivoTutoria motivoTutoria) {
		moTutoRepo.save(motivoTutoria);		
	}

	@Override
	public void eliminar(Integer idMotivo) {
		moTutoRepo.deleteById(idMotivo);
	}

	@Override
	public List<MotivoTutoria> findByIdTutoria(Integer id) {
		return moTutoRepo.findByIdTutoria(id);
	}

}

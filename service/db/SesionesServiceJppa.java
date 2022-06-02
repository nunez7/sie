package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Sesion;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.repository.SesionesRepository;
import edu.mx.utdelacosta.service.ISesionesService;

@Service
public class SesionesServiceJppa implements ISesionesService {

	@Autowired
	SesionesRepository sesionesRepo;

	@Override
	public void guardar(Sesion sesion) {
		// TODO Auto-generated method stub
		Sesion ses = buscarPorSesion(sesion.getSesionId());
		if (ses == null) {
			sesionesRepo.save(sesion);
		}
	}

	@Override
	public List<Sesion> buscarTodas() {
		// TODO Auto-generated method stub
		return (List<Sesion>) sesionesRepo.findAll();
	}

	@Override
	public Sesion buscarPorId(Integer idSesion) {
		// TODO Auto-generated method stub
		Optional<Sesion> optional = sesionesRepo.findById(idSesion);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public Sesion buscarPorSesion(String idSesion) {
		// TODO Auto-generated method stub
		return sesionesRepo.findBySesionId(idSesion);
	}

	@Override
	public List<Sesion> buscarPorUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return sesionesRepo.findByUsuario(usuario);
	}

}

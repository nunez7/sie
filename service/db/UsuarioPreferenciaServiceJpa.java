package edu.mx.utdelacosta.service.db;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.UsuarioPreferencia;
import edu.mx.utdelacosta.repository.UsuarioPreferenciaRepository;
import edu.mx.utdelacosta.service.IUsuarioPreferenciaService;

@Service
public class UsuarioPreferenciaServiceJpa implements IUsuarioPreferenciaService{
	
	@Autowired
	private UsuarioPreferenciaRepository preferenciaRepo;

	@Override
	public void guardar(UsuarioPreferencia usuario) {
		// TODO Auto-generated method stub
		preferenciaRepo.save(usuario);
	}

	@Override
	@Transactional(readOnly = true)
	public UsuarioPreferencia buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		Optional<UsuarioPreferencia> optional = preferenciaRepo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

}

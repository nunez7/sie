package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Modulo;
import edu.mx.utdelacosta.repository.ModulosRepository;
import edu.mx.utdelacosta.service.IModuloService;

@Service
public class ModulosServiceJpa implements IModuloService{

	@Autowired
	private ModulosRepository modulosRepo;

	@Override
	@Transactional(readOnly = true)
	public List<Modulo> buscarTodos() {
		// TODO Auto-generated method stub
		return (List<Modulo>) modulosRepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Modulo> buscarPorEstatus(boolean estatus){
		return modulosRepo.findByActivoOrderByNombreAsc(estatus);
	}

	@Override
	@Transactional(readOnly = true)
	public Modulo buscarPorId(Integer idModulo) {
		// TODO Auto-generated method stub
		Optional<Modulo> optional = modulosRepo.findById(idModulo);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Modulo> buscarModulosPorRol(Integer idRol) {
		// si es Admin
		if(idRol!=6) {
			return modulosRepo.buscarModulosPorRol(idRol);
		}
		return modulosRepo.findByActivoOrderByNombreAsc(true);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean hasAccess(Integer idUsuario, Integer idModulo) {
		// TODO Auto-generated method stub
		Optional<Modulo> optional =Optional.ofNullable(modulosRepo.hasAccess(idUsuario, idModulo));
		if (optional.isPresent()) {
			return true;
		}
		return false;
	}

}

package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Submodulo;
import edu.mx.utdelacosta.repository.SubmodulosRepository;
import edu.mx.utdelacosta.service.ISubmoduloService;

@Service
public class SubmodulosServiceJpa implements ISubmoduloService {

	@Autowired
	private SubmodulosRepository submodulosRepo;

	@Override
	public List<Submodulo> buscarTodos() {
		// TODO Auto-generated method stub
		return (List<Submodulo>) submodulosRepo.findAll();
	}

	@Override
	public List<Submodulo> buscarPorModuloYEstatus(int modulo, boolean estatus) {
		// TODO Auto-generated method stub
		return submodulosRepo.findByModuloPadreAndActivoOrderByConsecutivoAsc(modulo, estatus);
	}

	@Override
	public Submodulo buscarPorId(Integer idSubmodulo) {
		// TODO Auto-generated method stub
		Optional<Submodulo> optional = submodulosRepo.findById(idSubmodulo);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public Submodulo getPorDefecto(Integer idModulo) {
		// TODO Auto-generated method stub
		List<Submodulo> lista = buscarPorModuloYEstatus(idModulo, true);
		if(lista.size()>1) {
			return lista.get(1);
		}else if(lista.size()==1) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public boolean existeRelacion(Integer modulo, Integer submodulo) {
		// TODO Auto-generated method stub
		Optional<Submodulo> optional = Optional.ofNullable(submodulosRepo.findByModuloPadreAndCveSubmodulo(modulo, submodulo));
		if (optional.isPresent()) {
			return true;
		}
		return false;
	}

}

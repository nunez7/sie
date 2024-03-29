package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Cliente;
import edu.mx.utdelacosta.repository.ClienteRepository;
import edu.mx.utdelacosta.service.IClienteService;

@Service
public class ClienteServiceJpa implements IClienteService{

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> buscarTodos() {
		// TODO Auto-generated method stub
		return clienteRepository.findAllByOrderByIdDesc();
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente buscarPorId(Integer id) {
		Optional<Cliente> optional = clienteRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public void guardar(Cliente cliente) {
		clienteRepository.save(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public String ultimaClave() {
		// TODO Auto-generated method stub
		return clienteRepository.findLastClave();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> buscarPorNombreORfc(String like) {
		// TODO Auto-generated method stub
		return clienteRepository.findByNombreOrRfc(like);
	}

}

package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Usuario;
import edu.mx.utdelacosta.repository.UsuariosRepository;
import edu.mx.utdelacosta.service.IUsuariosService;

@Service
public class UsuariosServiceJpa implements IUsuariosService{
	
	@Autowired
	private UsuariosRepository usuariosRepo;

	@Override
	public void guardar(Usuario usuario) {
		// TODO Auto-generated method stub
		usuariosRepo.save(usuario);
	}

	@Transactional
	@Override
	public void eliminar(Integer idUsuario) {
		// TODO Auto-generated method stub
		usuariosRepo.deleteById(idUsuario);
	}

	@Override
	public List<Usuario> buscarTodos() {
		// TODO Auto-generated method stub
		return usuariosRepo.findAll(Sort.by(Order.desc("id")));
	}

	@Override
	public Usuario buscarPorId(Integer idUsuario) {
		// TODO Auto-generated method stub
		Optional<Usuario> optional = usuariosRepo.findById(idUsuario);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public Usuario buscarPorUsuario(String username) {
		// TODO Auto-generated method stub
		return usuariosRepo.findByUsuario(username);
	}

	@Override
	@Transactional
	public int bloquear(int idUsuario) {
		// TODO Auto-generated method stub
		int rows = usuariosRepo.lock(idUsuario);
		return rows;
	}

	@Transactional
	@Override
	public int activar(int idUsuario) {
		// TODO Auto-generated method stub
		int rows = usuariosRepo.unlock(idUsuario);
		return rows;
	}

	@Override
	@Transactional(readOnly = false)
	public void actualizarContrasenia(Integer id, String contrasenia) {
		// TODO Auto-generated method stub
		usuariosRepo.updatePassword(id, contrasenia);
	}

	@Override
	public List<Usuario> buscarPorPersonaOUsuario(String nombre) {
		// TODO Auto-generated method stub
		return usuariosRepo.findByPersonOrUsername(nombre);
	}

	@Override
	public List<Usuario> buscarByExample(Example<Usuario> example) {
		// TODO Auto-generated method stub
		return usuariosRepo.findAll(example);
	}

	@Override
	public Usuario buscarPorPersona(Persona persona) {
		// TODO Auto-generated method stub
		return usuariosRepo.findByPersona(persona);
	}

	@Override
	public Page<Usuario> buscarTodos(Pageable page) {
		// TODO Auto-generated method stub
		return usuariosRepo.findAll(page);
	}

	@Override
	public List<Usuario> buscarUltimos10() {
		// TODO Auto-generated method stub
		return usuariosRepo.findTop10ByOrderByIdDesc();
	}

}

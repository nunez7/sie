package edu.mx.utdelacosta.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.PasswordToken;
import edu.mx.utdelacosta.repository.PasswordTokenRepository;
import edu.mx.utdelacosta.service.IPasswordTokenService;

@Service
public class PasswordTokenServiceJpa implements IPasswordTokenService{
	
	@Autowired
	PasswordTokenRepository tokenRepository;

	@Override
	@Transactional(readOnly = true)
	public PasswordToken buscarPorToken(String token) {
		return tokenRepository.findByToken(token);
	}

	@Override
	@Transactional
	public void guardar(PasswordToken token) {
		// TODO Auto-generated method stub
		tokenRepository.save(token);
	}

	@Override
	@Transactional
	public void eliminar(PasswordToken token) {
		// TODO Auto-generated method stub
		tokenRepository.delete(token);
	}

}

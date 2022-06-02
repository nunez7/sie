package edu.mx.utdelacosta.service;

import edu.mx.utdelacosta.model.PasswordToken;

public interface IPasswordTokenService {
	
	PasswordToken buscarPorToken(String token);
	
	void guardar(PasswordToken token);
	
	void eliminar(PasswordToken token);
}

package edu.mx.utdelacosta.repository;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.PasswordToken;

public interface PasswordTokenRepository extends CrudRepository<PasswordToken, Integer>{
	
	PasswordToken findByToken(String token);

}

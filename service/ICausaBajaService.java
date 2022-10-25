package edu.mx.utdelacosta.service;

import java.util.List;

import edu.mx.utdelacosta.model.CausaBaja;

public interface ICausaBajaService {
	
	//busca todas las cuasas de baja 
	List<CausaBaja> buscarActivas();

}

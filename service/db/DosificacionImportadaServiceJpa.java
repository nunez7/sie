package edu.mx.utdelacosta.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.DosificacionImportada;
import edu.mx.utdelacosta.model.dto.DosificacionImportarDTO;
import edu.mx.utdelacosta.repository.DosificacionImportadaRepository;
import edu.mx.utdelacosta.service.IDosificacionImportadaService;

@Service
public class DosificacionImportadaServiceJpa implements IDosificacionImportadaService{

	@Autowired
	private DosificacionImportadaRepository dosiImpRepository;
	
	@Override
	public void guardar(DosificacionImportada dosiImportada) {
		dosiImpRepository.save(dosiImportada);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DosificacionImportarDTO> buscarImportarPorMateriaYPeriodo(Integer idMateria, Integer idCarga,
			Integer idPeriodo) {
		return dosiImpRepository.findImportByMateriaAndPeriodo(idMateria, idCarga, idPeriodo);
	}

}

package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.utdelacosta.model.AreaConocimiento;
import edu.mx.utdelacosta.repository.AreaConocimientoRepository;
import edu.mx.utdelacosta.service.IAreaConocimientoService;

@Service
public class AreaConocimientoServiceJpa implements IAreaConocimientoService{

	@Autowired
	private AreaConocimientoRepository areaConocimientoRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<AreaConocimiento> buscarAreasActivas() {
		// TODO Auto-generated method stub
		return areaConocimientoRepository.findByActivo(true);
	}

	@Override
	@Transactional(readOnly = true)
	public AreaConocimiento buscarPorId(Integer id) {
		Optional<AreaConocimiento> area = areaConocimientoRepository.findById(id);
		if(area.isPresent()) {
			return area.get();
		}
		return null;
	}

}

package edu.mx.utdelacosta.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Area;
import edu.mx.utdelacosta.repository.AreaRepository;
import edu.mx.utdelacosta.service.IAreaService;

@Service
public class AreaServiceJpa implements IAreaService{
	
	@Autowired
	private AreaRepository areaRepository;

	@Override
	public void guardar(Area area) {
		// TODO Auto-generated method stub
		areaRepository.save(area);
	}

	@Override
	public List<Area> buscarTodas() {
		// TODO Auto-generated method stub
		return areaRepository.findAll(Sort.by(Order.asc("area")));
	}

	@Override
	public Area buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		Optional<Area> optional = areaRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

}

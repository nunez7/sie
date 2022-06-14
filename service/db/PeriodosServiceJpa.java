package edu.mx.utdelacosta.service.db;

import java.util.Date;
import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import edu.mx.utdelacosta.model.Periodo;
import edu.mx.utdelacosta.repository.PeriodosRepository;
import edu.mx.utdelacosta.service.IPeriodosService;

@Service
public class PeriodosServiceJpa implements IPeriodosService{
	
	@Autowired
	private PeriodosRepository periodosRepository;

	@Override
	public List<Periodo> buscarTodos() {
		// TODO Auto-generated method stub
		return (List<Periodo>) periodosRepository.findAll(Sort.by(Order.desc("id")));
	}

	@Override
	public Periodo buscarPorId(Integer idPeriodo) {
		// TODO Auto-generated method stub
		Optional<Periodo> optional = periodosRepository.findById(idPeriodo);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public void guardar(Periodo periodo) {
		// TODO Auto-generated method stub
		periodosRepository.save(periodo);
	}

	@Override
	public List<Date> buscarDiasPorFechaInicioYFechafin(String fechaInicio, String fechaFin) {
		return periodosRepository.findDiasByFechaInicioAndFechafin(fechaInicio, fechaFin);
	}
	

}

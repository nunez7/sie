package edu.mx.utdelacosta.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Periodo;

public interface PeriodosRepository extends JpaRepository<Periodo, Integer>{
	
	// Lista de dias
	@Query(value = "SELECT CAST(dd AS date) AS fecha "
			+ "FROM generate_series(CAST(:fechaInicio AS date),CAST(:fechaFin AS date),CAST('1 day' AS interval))AS dia(dd) "
			+ "WHERE CAST(TO_CHAR(dia.dd, 'd') AS INT) NOT IN (7, 1) LIMIT 200 ", nativeQuery = true)
	List<Date> findDiasByFechaInicioAndFechafin(@Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin);
	
	Periodo findTopByOrderByIdDesc();
	
	Periodo findAllByInicioLessThanEqualAndFinGreaterThanEqual(Date inicio, Date fin);
}

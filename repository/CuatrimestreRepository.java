package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Cuatrimestre;
import edu.mx.utdelacosta.model.dto.PagoCuatrimestreDTO;

public interface CuatrimestreRepository extends JpaRepository<Cuatrimestre, Integer>{
	
	@Query(value = "SELECT DISTINCT(c.consecutivo) as cuatrimestre,"
			+ "	MAX(pg.fecha_limite) as fechaLimite "
			+ "	FROM alumnos a "
			+ "	INNER JOIN alumnos_grupos algr ON algr.id_alumno = a.id "
			+ "	INNER JOIN grupos gr ON gr.id = algr.id_grupo "
			+ "	INNER JOIN cuatrimestres c ON gr.id_cuatrimestre = c.id "
			+ "	INNER JOIN pago_cuatrimestre pc ON pc.id_alumno_grupo=algr.id "
			+ " INNER JOIN pagos_generales pg ON pc.id_pago = pg.id "
			+ "	WHERE gr.id_periodo=:periodo "
			+ "	group by c.consecutivo, algr.id_grupo ",nativeQuery = true)
	List<PagoCuatrimestreDTO> findPagosGenerados(@Param("periodo")Integer idPeriodo);

}

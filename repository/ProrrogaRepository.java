package edu.mx.utdelacosta.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.CorteEvaluativo;
import edu.mx.utdelacosta.model.Prorroga;
import edu.mx.utdelacosta.model.TipoProrroga;

public interface ProrrogaRepository extends CrudRepository<Prorroga, Integer> {

	@Query(value = " SELECT p.* " + "FROM prorroga p " + "INNER JOIN cargas_horarias ch on ch.id = p.id_carga_horaria "
			+ "WHERE ch.id_periodo=:idPeriodo AND ch.id_profesor= :idPersona AND p.activo=True ", nativeQuery = true)
	List<Prorroga> findByProfesorAndPeriodoAndActivo(@Param("idPersona") Integer IdProfesor,
			@Param("idPeriodo") Integer idPeriodo);

	@Query(value = " SELECT p.* " + "FROM prorroga p " + "INNER JOIN cargas_horarias ch on ch.id = p.id_carga_horaria "
			+ "WHERE ch.id_periodo=:idPeriodo AND ch.id_profesor=:idPersona ", nativeQuery = true)
	List<Prorroga> findByProfesorAndPeriodo(@Param("idPersona") Integer IdProfesor,
			@Param("idPeriodo") Integer idPeriodo);

	@Query(value = "SELECT pr.* FROM prorroga pr " + "INNER JOIN cargas_horarias ch ON pr.id_carga_horaria = ch.id "
			+ "INNER JOIN grupos g ON g.id = ch.id_grupo " + "INNER JOIN carreras c ON c.id = g.id_carrera "
			+ "WHERE pr.activo = 'True' AND aceptada = 'False' "
			+ "AND c.id IN (SELECT id_carrera FROM persona_carrera WHERE id_persona = :idPersona)", nativeQuery = true)
	List<Prorroga> findByCarreraAndRequested(@Param("idPersona") Integer idPersona);

	@Query(value = "SELECT COUNT(p.*) as cantidad FROM prorroga p "
			+ "INNER JOIN cargas_horarias ch ON p.id_carga_horaria = ch.id "
			+ "WHERE ch.id_profesor = :profesor AND ch.id_periodo = :periodo "
			+ "AND p.aceptada = 'True'", nativeQuery = true)
	Integer countProrrogasByIdProfesorAndPeriodo(@Param("profesor") Integer profesor,
			@Param("periodo") Integer periodo);

	List<Prorroga> findByCargaHoraria(CargaHoraria cargaHoraria);

	Optional<Prorroga> findById(Integer id);

	Prorroga findByCargaHorariaAndTipoProrrogaAndActivoAndAceptada(CargaHoraria cargaHoraria, TipoProrroga tipoProrroga,
			boolean activo, boolean aceptada);
	
	Prorroga findByCargaHorariaAndTipoProrrogaAndCorteEvaluativoAndActivoAndAceptada(CargaHoraria cargaHoraria, TipoProrroga tipoProrroga,
			CorteEvaluativo corteEvaluativo, boolean activo, boolean aceptada);

	Prorroga findByCargaHorariaAndTipoProrrogaAndFechaLimiteGreaterThanEqualAndActivoAndAceptada(
			CargaHoraria cargaHoraria, TipoProrroga tipoProrroga, Date fecha, Boolean activo, Boolean aceptada);

	Prorroga findByCargaHorariaAndCorteEvaluativoAndTipoProrrogaAndActivo(CargaHoraria cargaHoraria,
			CorteEvaluativo corteEvaluativo, TipoProrroga tipoProrroga, boolean activo);

	// busca las prorrogas autorizadas por personaCarrera
	@Query(value = "SELECT pr.* FROM prorroga pr "
			+ "INNER JOIN cargas_horarias ch ON pr.id_carga_horaria = ch.id "
			+ "INNER JOIN grupos g ON g.id = ch.id_grupo "
			+ "INNER JOIN carreras c ON c.id = g.id_carrera "
			+ "WHERE pr.activo = 'True' AND aceptada = 'True' "
			+ "AND c.id IN (SELECT id_carrera FROM persona_carrera WHERE id_persona = :idPersona)", nativeQuery = true)
	List<Prorroga> findByPersonaCarreraAndAccept(@Param("idPersona") Integer idPersona);

	
}

package edu.mx.utdelacosta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.CargaHoraria;
import edu.mx.utdelacosta.model.Horario;
import edu.mx.utdelacosta.model.dto.HorarioDTO;

public interface HorarioRepository extends CrudRepository<Horario, Integer>{
	
	//Lista de todas las horas de clases 	
	@Query(value = "SELECT hr.* FROM horarios hr "
			+ "INNER JOIN cargas_horarias cr ON cr.id=hr.id_carga_horaria "
			+ "WHERE cr.id_grupo=:idGrupo AND hr.activo=true "
			+ "ORDER BY hr.hora_inicio DESC ", nativeQuery = true)
	List<Horario> findAllByGrupoActivoTrueOrderByHoraInicioDesc(@Param("idGrupo") Integer idGrupo);
	
	//horas unicas de clases 	
	@Query(value = "SELECT DISTINCT ON (hr.hora_inicio) hr.id, hr.id_dia, hr.id_carga_horaria, hr.id_actividad, hr.hora_inicio,hr.hora_fin, hr.activo FROM horarios hr "
			+ "INNER JOIN cargas_horarias cr ON cr.id=hr.id_carga_horaria "
			+ "WHERE cr.id_grupo=:idGrupo AND hr.activo=true "
			+ "ORDER BY hr.hora_inicio ASC ", nativeQuery = true)
	List<Horario> findDistinctHoraInicioByGrupoActivoTrueOrderByHoraInicioAsc(@Param("idGrupo") Integer idGrupo);
	
	
	@Query(value = "SELECT h.id, h.hora_inicio AS horaInicio, h.hora_fin AS horaFin, m.nombre AS materia, "
			+ "	m.abreviatura AS abreviaturaMateria, g.nombre AS grupo, ch.id AS cargaHoraria, h.id_actividad AS actividad, h.id_dia AS dia, "
			+ "	CONCAT(pr.nombre,' ',pr.primer_apellido,' ',pr.segundo_apellido) as profesor "
			+ "	FROM horarios h "
			+ "	INNER JOIN cargas_horarias ch ON h.id_carga_horaria=ch.id "
			+ "	INNER JOIN grupos g ON ch.id_grupo = g.id "
			+ "	INNER JOIN materias m ON ch.id_materia = m.id "
			+ "	INNER JOIN personas pr ON ch.id_profesor = pr.id  "
			+ " WHERE CAST(h.hora_inicio AS varchar) = :horaInicio "
			+ " AND h.id_dia = :dia AND ch.id_grupo = :grupo "
			+ " AND h.activo = 'True'", nativeQuery = true)
	HorarioDTO findAllByHoraInicioAndDiaAndGrupoActivoTrue(@Param("horaInicio") String horaInicio, @Param("dia") Integer dia, @Param("grupo") Integer grupo);
	
	@Query(value="SELECT DISTINCT ON (hr.hora_inicio) hr.id, hr.id_dia, hr.id_carga_horaria, hr.id_actividad, hr.hora_inicio,hr.hora_fin, hr.activo "
			+ "FROM horarios hr "
			+ "INNER JOIN cargas_horarias cr ON cr.id = hr.id_carga_horaria "
			+ "WHERE cr.id_profesor = :profesor AND hr.activo= 'True' AND cr.activo = 'True' "
			+ "AND cr.id_periodo = :periodo ORDER BY hr.hora_inicio ASC", nativeQuery = true )
	List<Horario> buscarPorProfesorDistinctPorHoraInicio(@Param("profesor") Integer profesor, @Param("periodo") Integer periodo);
	
	//busca los horarios por cargHoraria
	List<Horario> findByCargaHorariaAndActivoTrue(CargaHoraria cargaHoraria);
	
	//busca el horario por horaInicio, horafin, cargaHoraria, periodo, profesor y activo
	@Query(value = "SELECT h.* FROM horarios h "
			+ "INNER JOIN cargas_horarias ch ON h.id_carga_horaria = ch.id "
			+ "WHERE CAST(h.hora_inicio AS VARCHAR) = :horaInicio "
			+ "AND CAST(h.hora_fin AS VARCHAR) = :horaFin "
			+ "AND ch.id_profesor = :profesor AND ch.id_periodo = :periodo AND h.id_dia = :dia "
			+ "AND h.activo = 'True' " , nativeQuery = true)
	Horario findByHoraInicioAndHoraFinAndPeriodoAndProfesorAndDiaAndActivo(
			@Param("horaInicio") String horaInicio, @Param("horaFin") String horaFin, @Param("periodo") Integer periodo, 
			@Param("profesor") Integer profesor, @Param("dia") Integer dia);
	
	//busca el horario por hiraInicio, horFin, cargaHoraria, grupo y activo
	@Query(value = "SELECT h.* FROM horarios h "
			+ "INNER JOIN cargas_horarias ch ON h.id_carga_horaria = ch.id "
			+ "WHERE CAST(h.hora_inicio AS VARCHAR) = :horaInicio "
			+ "AND CAST(h.hora_fin AS VARCHAR) = :horaFin "
			+ "AND ch.id_grupo = :grupo  AND h.id_dia = :dia "
			+ "AND h.activo = 'True' ", nativeQuery = true)
	Horario findByHoraInicioandHoraFinAndGrupoAndDiaAndActivo(
			@Param("horaInicio") String horaInicio, @Param("horaFin") String horaFin,
			@Param("grupo") Integer grupo, @Param("dia") Integer dia);
	
	//busca los horarios del profesor 
	@Query(value = "SELECT h.id, h.hora_inicio AS horaInicio, h.hora_fin AS horaFin, m.nombre AS materia,"
			+ "	m.abreviatura AS abreviaturaMateria, g.nombre AS grupo, ch.id AS cargaHoraria, h.id_actividad AS actividad, h.id_dia AS dia, "
			+ " CONCAT(pr.nombre,' ',pr.primer_apellido,' ',pr.segundo_apellido) as profesor"
			+ "	FROM horarios h "
			+ "	INNER JOIN cargas_horarias ch ON h.id_carga_horaria=ch.id "
			+ "	INNER JOIN grupos g ON ch.id_grupo = g.id "
			+ "	INNER JOIN materias m ON ch.id_materia = m.id "
			+ " INNER JOIN personas pr ON ch.id_profesor = pr.id "
			+ "	WHERE CAST(h.hora_inicio AS varchar) = :horaInicio "
			+ "	AND h.id_dia = :dia AND ch.id_profesor = :profesor AND ch.id_periodo = :periodo "
			+ "	AND ch.activo ='True' AND h.activo = 'True'", nativeQuery = true)
	HorarioDTO findAllByHoraInicioAndDiaAndProfesorAndPeriodo(@Param("horaInicio") String horaInicio, @Param("dia") Integer dia,
			@Param("profesor") Integer idProfesor, @Param("periodo") Integer idPeriodo);
	
	@Query(value = "SELECT h.* " + "FROM horarios h " + "INNER JOIN cargas_horarias cg on h.id_carga_horaria=cg.id "
			+ "INNER JOIN dias d on h.id_dia=d.id WHERE cg.id=:idCargaHoraria and d.id=:idDia AND h.activo='True' "
			+ "ORDER BY hora_inicio ASC", nativeQuery = true)
	List<Horario> findByCargaHorariaAndDia(@Param("idCargaHoraria") Integer idCargaHoraria,
			@Param("idDia") Integer idDia);

	@Query(value = "SELECT DISTINCT(hora_inicio, hora_fin) " 
	+ "FROM horarios h "
	+ "INNER JOIN cargas_horarias ch on h.id_carga_horaria=ch.id "
	+ "WHERE ch.id_profesor=:idProfesor ", nativeQuery = true)
	List<HorarioDTO> findByIdProfesor(@Param("idProfesor") Integer idProfesor);

	Optional<Horario> findById(Integer idHorario);
		
}

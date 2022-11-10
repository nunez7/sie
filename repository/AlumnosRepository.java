package edu.mx.utdelacosta.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.dto.AlumnoInfoDTO;
import edu.mx.utdelacosta.model.dto.ProspectoDTO;
import edu.mx.utdelacosta.model.dto.RemedialAlumnoDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoAdeudoDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoMatriculaInicialDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoNoReinscritoDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoPromedioEscolaresDTO;
import edu.mx.utdelacosta.model.dtoreport.AlumnoRegularDTO;
import edu.mx.utdelacosta.model.dtoreport.ProspectoEscolaresDTO;

public interface AlumnosRepository extends CrudRepository<Alumno, Integer>{
	Alumno findByPersona(Persona persona);
	Alumno findByMatricula(String matricula);
	
	Optional<Alumno> findById(Integer id);
	
	@Query(value = "SELECT COUNT(*) as inscritos "
			+ "FROM alumnos_grupos ag "
			+ "INNER JOIN alumnos a on a.id=ag.id_alumno "
			+ "WHERE ag.id_grupo=:idGrupo AND ag.activo='True' AND a.estatus=1 AND ag.fecha_inscripcion IS NOT NULL ", nativeQuery = true)
	Integer countAlumnosInscritosByGrupoAndActivo(@Param("idGrupo") Integer idGrupo);
	
	@Query(value = "SELECT COUNT(ag.id) as inscritos "
			+ "FROM alumnos_grupos ag "
			+ "INNER JOIN alumnos a on a.id=ag.id_alumno "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "WHERE g.id_carrera=:idCarrera AND g.id_periodo=:idPeriodo AND ag.activo='True' AND a.estatus=1 AND ag.created IS NOT NULL ", nativeQuery = true)
	Integer countInscritosByCarreraAndPeriodo(@Param("idCarrera") Integer idCarrera, @Param("idPeriodo") Integer idPeriodo);
		
	@Query(value = "SELECT COUNT(*) as inscritos "
			+ "FROM alumnos_grupos ag "
			+ "INNER JOIN alumnos a on a.id=ag.id_alumno "
			+ "INNER JOIN personas p on a.id_persona = p.id "
			+ "INNER JOIN usuarios u ON u.id_persona = p.id "
			+ "WHERE ag.id_grupo=:idGrupo AND u.activo = 'False' AND a.estatus=0 ", nativeQuery = true)
	Integer countAlumnosBajaByGrupoAndActivo(@Param("idGrupo") Integer idGrupo);
	
	@Query(value = "SELECT COUNT(ag.id) as inscritos "
			+ "FROM alumnos_grupos ag "
			+ "INNER JOIN alumnos a on a.id=ag.id_alumno "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "WHERE g.id_carrera=:idCarrera AND g.id_periodo=:idPeriodo AND ag.activo=false AND a.estatus=0  ", nativeQuery = true)
	Integer countBajaByCarreraAndPeriodo(@Param("idCarrera") Integer idCarrera, @Param("idPeriodo") Integer idPeriodo);
	
	@Query(value = "SELECT a.* "
			+ "FROM alumnos_grupos ag "
			+ "INNER JOIN alumnos a ON a.id=ag.id_alumno "
			+ "INNER JOIN personas p ON p.id=a.id_persona "
			+ "WHERE ag.id_grupo= :idGrupo AND ag.activo = 'True' "
			+ "ORDER BY TRANSLATE (p.primer_apellido,'ÁÉÍÓÚÜÑ ','AEIOUUN') ASC, TRANSLATE (p.segundo_apellido,'ÁÉÍÓÚÜÑ ','AEIOUUN') ASC, "
			+ "TRANSLATE (p.nombre,'ÁÉÍÓÚÜÑ ','AEIOUUN') ASC ", nativeQuery = true)
	List<Alumno> findAllAlumnosByGrupoOrderByNombreAsc(@Param("idGrupo") Integer idGrupo);
	
	@Query(value = "SELECT COUNT(a.id)AS cantidad "
			+ "FROM alumnos_grupos ag "
			+ "INNER JOIN alumnos a ON a.id=ag.id_alumno "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "WHERE g.id_carrera=:idCarrera AND g.id_periodo=:idPeriodo", nativeQuery = true)
	Integer countAlumnosByCarrera(@Param("idCarrera") Integer idCarrera, @Param("idPeriodo") Integer idPeriodo);
	
	@Query(value = "SELECT a.* FROM alumnos a "
			+ "INNER JOIN personas p ON a.id_persona = p.id "
			+ "WHERE id_carrera = :carrera AND estatus = 1 "
			+ "ORDER BY p.nombre, p.primer_apellido, p.segundo_apellido", nativeQuery = true)
	List<Alumno> findAllAlumnosByCarreraAndActivo(@Param("carrera") Integer carrera);
	
	@Query(value = "SELECT a.id as idAlumno, CONCAT(p.primer_apellido,' ',p.segundo_apellido, ' ', p.nombre) as alumno, a.matricula, pg.concepto, pg.monto as cantidad "
			+ "FROM pagos_generales pg "
			+ "INNER JOIN pago_alumno pa on pg.id=pa.id_pago "
			+ "INNER JOIN alumnos a on pa.id_alumno=a.id "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno = a.id "
			+ "INNER JOIN grupos g ON g.id = ag.id_grupo "
			+ "INNER JOIN personas p ON p.id = a.id_persona "
			+ "WHERE a.id_carrera IN (SELECT id_carrera FROM persona_carrera WHERE id_persona = :persona) "
			+ "AND pg.status=0 AND g.id_periodo = :periodo "
			+ "ORDER BY p.primer_apellido, p.segundo_apellido,p.nombre ", nativeQuery = true) 
	List<AlumnoAdeudoDTO> getAllAlumnoAdeudoByPersonaCarreraAndPeriodo(@Param("persona") Integer idPersona, @Param("periodo") Integer idPeriodo);
	
	//para traer los adeudos de alumnos por carrera y periodo
	 @Query(value = "SELECT a.id as idAlumno, CONCAT(p.primer_apellido,' ',p.segundo_apellido, ' ',p.nombre) as alumno, a.matricula, c.concepto, pg.monto as cantidad, pg.concepto as descripcion "
	   + "FROM pagos_generales pg "
	   + "INNER JOIN pago_alumno pa on pg.id=pa.id_pago "
	   + "INNER JOIN alumnos a on pa.id_alumno=a.id "
	   + "INNER JOIN alumnos_grupos ag ON ag.id_alumno = a.id "
	   + "INNER JOIN grupos g ON g.id = ag.id_grupo "
	   + "INNER JOIN personas p ON p.id = a.id_persona "
	   + "INNER JOIN conceptos c ON c.id = pg.id_concepto "
	   + "WHERE a.id_carrera=:carrera "
	   + "AND pg.status=0 AND g.id_periodo = :periodo "
	   + "ORDER BY TRANSLATE (p.primer_apellido,'ÁÉÍÓÚÜÑ ','AEIOUUN') ASC, TRANSLATE (p.segundo_apellido,'ÁÉÍÓÚÜÑ ','AEIOUUN') ASC, "
	   + "TRANSLATE (p.nombre,'ÁÉÍÓÚÜÑ ','AEIOUUN') ASC ", nativeQuery = true) 
	 List<AlumnoAdeudoDTO> getAllAlumnoAdeudoByCarreraAndPeriodo(@Param("carrera") Integer idCarrera, @Param("periodo") Integer idPeriodo);
	
	@Query(value = "SELECT a.id AS idAlumno, a.matricula, p.primer_apellido AS primerApellido, p.segundo_apellido AS segundoApellido,  "
			+ "p.nombre,  a.id_persona AS idPersona, g.nombre AS grupoAnterior, "
			+ "(SELECT COALESCE(gg.nombre, '') "
			+ "FROM alumnos_grupos agg "
			+ "INNER JOIN grupos gg ON gg.id=agg.id_grupo "
			+ "WHERE gg.id_periodo=:periodo+1 AND gg.id_carrera=:carrera AND agg.id_alumno=a.id)AS grupoActual "
			+ "FROM alumnos a "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno=a.id "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "INNER JOIN cuatrimestres cu ON cu.id=g.id_cuatrimestre "
			+ "INNER JOIN carreras c ON c.id=g.id_carrera "
			+ "INNER JOIN personas p ON p.id=a.id_persona "
			+ "WHERE g.id_carrera=:carrera AND g.id_periodo=:periodo AND cu.consecutivo=:cuatrimestre "
		//	+ "AND a.estatus = 1 AND a.documentos_ingresos = 1 "
		//	+ "AND ag.pagado = 'True' AND ag.fecha_inscripcion IS NOT NULL "
		//	+ " AND a.id NOT IN ("
		//	+ " SELECT id_alumno FROM pago_alumno pa "
		//	+ " INNER JOIN pagos_generales pg  ON pg.id=pa.id_pago "
		//	+ " WHERE pa.id_alumno=a.id AND pg.status=0) "
			+ "ORDER BY c.nombre, p.primer_apellido, p.segundo_apellido, p.nombre", nativeQuery = true)
	List<AlumnoRegularDTO> findAllRegular(@Param("carrera") Integer carrera, @Param("periodo") Integer periodo,  @Param("cuatrimestre") Integer cuatrimestre);
	
	@Query(value = "SELECT a.id AS idAlumno, a.matricula, p.primer_apellido AS primerApellido, p.segundo_apellido AS segundoApellido,  "
			+ "p.nombre,  a.id_persona AS idPersona, g.nombre AS grupoAnterior, "
			+ "(SELECT COALESCE(gg.nombre, '') "
			+ "FROM alumnos_grupos agg "
			+ "INNER JOIN grupos gg ON gg.id=agg.id_grupo "
			+ "WHERE gg.id_periodo=:periodo+1 AND gg.id_carrera=:carreraActual AND agg.id_alumno=a.id)AS grupoActual "
			+ "FROM alumnos a "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno=a.id "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "INNER JOIN cuatrimestres cu ON cu.id=g.id_cuatrimestre "
			+ "INNER JOIN carreras c ON c.id=g.id_carrera "
			+ "INNER JOIN personas p ON p.id=a.id_persona "
			+ "WHERE g.id_carrera=:carrera AND g.id_periodo=:periodo-1 AND cu.consecutivo=:cuatrimestre-1 "
		//	+ "AND a.estatus = 1 AND a.documentos_ingresos = 1 "
		//	+ "AND ag.pagado = 'True' AND ag.fecha_inscripcion IS NOT NULL "
		//	+ " AND a.id NOT IN ("
		//	+ " SELECT id_alumno FROM pago_alumno pa "
		//	+ " INNER JOIN pagos_generales pg  ON pg.id=pa.id_pago "
		//	+ " WHERE pa.id_alumno=a.id AND pg.status=0) "
			+ "ORDER BY c.nombre, p.primer_apellido, p.segundo_apellido, p.nombre", nativeQuery = true)
	List<AlumnoRegularDTO> findAllRegularReinscribir(@Param("carrera") Integer carrera, @Param("periodo") Integer periodo,  @Param("cuatrimestre") Integer cuatrimestre, @Param("carreraActual") Integer idCarreraActuar);
	
	@Query(value = "SELECT a.id AS idAlumno, a.matricula, p.primer_apellido AS primerApellido, p.segundo_apellido AS segundoApellido,  "
			+ "p.nombre,  a.id_persona AS idPersona, g.nombre AS grupoAnterior, "
			+ "(SELECT COALESCE(gg.nombre, '') "
			+ "FROM alumnos_grupos agg "
			+ "INNER JOIN grupos gg ON gg.id=agg.id_grupo "
			+ "WHERE gg.id_periodo=:periodo+1 AND gg.id_carrera=:carrera AND agg.id_alumno=a.id)AS grupoActual "
			+ "FROM alumnos a "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno=a.id "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "INNER JOIN cuatrimestres cu ON cu.id=g.id_cuatrimestre "
			+ "INNER JOIN carreras c ON c.id=g.id_carrera "
			+ "INNER JOIN personas p ON p.id=a.id_persona "
			+ "WHERE a.estatus = 1 AND g.id_carrera=:carrera AND g.id_periodo=:periodo AND a.documentos_ingresos = 1 "
			+ "AND ag.pagado = 'True' AND ag.fecha_inscripcion IS NOT NULL "
			+ " AND a.id NOT IN ("
			+ " SELECT id_alumno FROM pago_alumno pa "
			+ " INNER JOIN pagos_generales pg  ON pg.id=pa.id_pago "
			+ " WHERE pa.id_alumno=a.id AND pg.status=0) "
			+ "ORDER BY c.nombre, g.nombre DESC, p.primer_apellido, p.segundo_apellido, p.nombre", nativeQuery = true)
	List<AlumnoRegularDTO> obtenerRegularesByCarreraPeriodo(@Param("carrera") Integer carrera, @Param("periodo") Integer periodo);
	
	
	@Query(value = "SELECT a.id AS idAlumno, a.matricula, CONCAT(p.primer_apellido, ' ',p.segundo_apellido, ' ',p.nombre)AS nombreCompleto,  c.nombre AS carrera, "
			+ "a.documentos_ingresos AS entregoDocumentos, a.ceneval, "
			+ "COALESCE(( "
			+ "SELECT MAX(status) FROM pagos_generales pg "
			+ "INNER JOIN pago_alumno pa ON pa.id_pago =pg.id "
			+ "WHERE pa.id_alumno=a.id AND pg.id_concepto=12 "
			+ "), 0)AS pago, p.email, dp.celular, p.fecha_alta AS fechaRegistro, dp.curp, ep.promedio, "
			+ "e.nombre AS estadoNacimiento, esc.nombre AS nombreBachillerato, eb.nombre AS estadoBachillerato, "
			+ "esc.municipio AS municipioBachillerato, esc.localidad AS localidadBachillerato, da.hijos, "
			+ "da.discapacitado, da.tipo_discapacidad as tipoDiscapacidad, CAST(da.indigena AS INT),"
			+ "CAST(da.dialecto AS INT) , da.promocion , da.tipo_beca as tipoBeca "
			+ "FROM alumnos a "
			+ "INNER JOIN datos_alumno da ON da.id_alumno=a.id "
			+ "INNER JOIN personas p ON p.id=a.id_persona "
			+ "INNER JOIN carreras c ON c.id=a.id_carrera "
			+ "LEFT JOIN datos_personales dp ON dp.id_persona=p.id "
			+ "LEFT JOIN alumnos_grupos ag ON ag.id_alumno=a.id "
			+ "LEFT JOIN escuela_procedencia ep ON ep.id_alumno=a.id "
			+ "LEFT JOIN escuelas esc ON esc.id=ep.id_escuela_procedencia "
			+ "LEFT JOIN estados eb ON eb.id=esc.id_estado "
			+ "LEFT JOIN estados e ON e.id=dp.edo_nacimiento "
			+ "WHERE ag.id_alumno IS NULL AND matricula ILIKE %:generacion% "
			+ "ORDER BY c.nombre, p.primer_apellido, p.segundo_apellido, p.nombre ", nativeQuery = true)
	List<ProspectoEscolaresDTO> findAllByGeneracion(@Param("generacion") String generacion);
	
	@Query(value = "SELECT a.id AS idAlumno,  CONCAT(p.primer_apellido, ' ',p.segundo_apellido, ' ',p.nombre)AS nombreCompleto, a.matricula, "
			+ "g.nombre AS grupo, COALESCE(ROUND(AVG(calificacion),1),0)AS calificacion, c.descripcion AS cuatrimestre, ca.nombre AS carrera, "
			+ "COALESCE(( "
			+ "	SELECT cmm.estatus FROM calificacion_materia  cmm "
			+ "	INNER JOIN cargas_horarias chh ON chh.id=cmm.id_carga_horaria "
			+ "	WHERE cmm.id_alumno=a.id AND chh.id_grupo=g.id AND estatus IN ('E', 'R') "
			+ "	ORDER BY cmm.estatus ASC "
			+ "	LIMIT 1 "
			+ "), 'O')AS estatus "
			+ "FROM alumnos a "
			+ "INNER JOIN personas p ON p.id=a.id_persona "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno=a.id "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "INNER JOIN cuatrimestres c ON c.id=g.id_cuatrimestre "
			+ "INNER JOIN carreras ca ON ca.id=g.id_carrera "
			+ "LEFT JOIN cargas_horarias ch ON ch.id=g.id "
			+ "LEFT JOIN calificacion_materia cm ON cm.id_carga_horaria=ch.id "
			+ "WHERE ch.activo='True' AND g.id_periodo=:periodo "
			+ "GROUP BY g.id, ca.nombre, c.descripcion, g.nombre, p.primer_apellido, p.segundo_apellido, p.nombre, a.id "
			+ "ORDER BY g.nombre, p.primer_apellido, p.segundo_apellido, p.nombre", nativeQuery = true)
	List<AlumnoPromedioEscolaresDTO> findAllPromedioEscolaresByPeriodo(@Param("periodo") Integer periodo);
	
	@Query(value = "SELECT a.id AS idAlumno, CONCAT(p.primer_apellido, ' ',p.segundo_apellido, ' ',p.nombre)AS nombreCompleto, a.matricula, "
			+ "g.nombre AS grupo, COALESCE(ROUND(AVG(calificacion),1),0)AS calificacion, c.descripcion AS cuatrimestre, ca.nombre AS carrera, "
			+ "COALESCE(( "
			+ "	SELECT cmm.estatus FROM calificacion_materia  cmm "
			+ "	INNER JOIN cargas_horarias chh ON chh.id=cmm.id_carga_horaria "
			+ "	WHERE cmm.id_alumno=a.id AND chh.id_grupo=g.id AND estatus IN ('E', 'R') "
			+ "	ORDER BY cmm.estatus ASC "
			+ "	LIMIT 1 "
			+ "), 'O')AS estatus "
			+ "FROM alumnos a "
			+ "INNER JOIN personas p ON p.id=a.id_persona "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno=a.id "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "INNER JOIN cuatrimestres c ON c.id=g.id_cuatrimestre "
			+ "INNER JOIN carreras ca ON ca.id=g.id_carrera "
			+ "LEFT JOIN cargas_horarias ch ON ch.id=g.id "
			+ "LEFT JOIN calificacion_materia cm ON cm.id_carga_horaria=ch.id "
			+ "WHERE ch.activo='True' AND g.id_periodo=:periodo AND ca.id=:carrera "
			+ "GROUP BY g.id, ca.nombre, c.descripcion, g.nombre, p.primer_apellido, p.segundo_apellido, p.nombre, a.id "
			+ "ORDER BY g.nombre, p.primer_apellido, p.segundo_apellido, p.nombre", nativeQuery = true)
	List<AlumnoPromedioEscolaresDTO> findAllPromedioEscolaresByPeriodoAndCarrera(@Param("periodo") Integer periodo, @Param("carrera") Integer carrera);
	
	@Query(value = "SELECT a.id AS idAlumno, a.matricula, CONCAT(p.primer_apellido, ' ',p.segundo_apellido, ' ',p.nombre)AS nombreCompleto, "
			+ "ca.nombre AS carrera, g.nombre AS grupo, c.consecutivo AS cuatrimestre, "
			+ "(CASE p.sexo WHEN 'H' THEN 'Hombre' WHEN 'M' THEN 'Mujer' ELSE 'N' END)AS sexo, "
			+ "da.indigena, da.discapacitado, dp.edad, dp.curp, p.email, e.nombre AS estadoNacimiento, esc.nombre AS nombreBachillerato, "
			+ "eb.nombre AS estadoBachillerato, esc.municipio AS municipioBachillerato, esc.localidad AS localidadBachillerato, da.hijos,"
			+ "CAST(da.dialecto AS INT) , da.promocion , da.tipo_beca as tipoBeca, COALESCE(da.tipo_discapacidad, 'No tiene') as tipoDiscapacidad "
			+ "FROM alumnos a "
			+ "INNER JOIN personas p ON p.id=a.id_persona "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno=a.id "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "INNER JOIN cuatrimestres c ON c.id=g.id_cuatrimestre "
			+ "INNER JOIN carreras ca ON ca.id=g.id_carrera "
			+ "LEFT JOIN datos_alumno da ON da.id_alumno=a.id "
			+ "LEFT JOIN datos_personales dp ON dp.id_persona=p.id "
			+ "LEFT JOIN estados e ON e.id=dp.edo_nacimiento "
			+ "LEFT JOIN escuela_procedencia ep ON ep.id_alumno=a.id "
			+ "LEFT JOIN escuelas esc ON esc.id=ep.id_escuela_procedencia "
			+ "LEFT JOIN estados eb ON eb.id=esc.id_estado "
			+ "WHERE g.id_periodo=:periodo AND ca.id=:carrera "
			+ "ORDER BY ca.nombre, ca.id_nivel_estudio, g.nombre, p.primer_apellido, p.segundo_apellido, p.nombre", nativeQuery = true)
	List<AlumnoMatriculaInicialDTO> findAllMatriculaInicial(@Param("periodo") Integer periodo, @Param("carrera") Integer carrera);
	
	//GADIEL
	@Query(value="SELECT LPAD(CAST(CAST(MAX(total) AS INTEGER)+1 AS VARCHAR),4,'0') "
			 + " FROM (SELECT RIGHT((a.matricula),4) AS total FROM  alumnos a "
			 + " WHERE a.matricula ILIKE %:matri% "
			 + " UNION ALL "
			 + " SELECT RIGHT((rc.clave),4) AS total FROM reserva_claves rc"
			 + " WHERE rc.clave ILIKE %:matri% "
			 + " )AS total", nativeQuery = true)
	String findNextMatricula(@Param("matri") String matri);
	
	@Query(value="SELECT CAST(COUNT(a.curp) AS VARCHAR) from datos_personales a"
			+ " WHERE a.curp ILIKE %:cur%", nativeQuery = true)
	Integer findExitenciaCurp(@Param("cur") String cur);
	
	@Query(value="SELECT (a.*) FROM alumnos a"
			+ " LEFT JOIN alumnos_grupos ag ON ag.id_alumno = a.id"
			+ " LEFT JOIN pago_alumno pa ON pa.id_alumno = a.id"
			+ " LEFT JOIN pagos_generales pg ON pg.id = pa.id_pago"
			+ " LEFT JOIN persona_documento pd ON pd.id_persona = a.id_persona"
			+ " WHERE ag.id_alumno IS NULL AND pa.id_pago = pg.id AND pg.status = 1"
			+ " GROUP BY a.id"
			+ " HAVING COUNT(pd.id_persona)>=3", nativeQuery = true)
	List<Alumno> findAllByStatusPagoGeneralWithoutGrupoAndWithDocumentos();
	
	@Query(value="SELECT a.* FROM alumnos a"
			+ " LEFT JOIN alumnos_grupos algr on algr.id_alumno = a.id"
			+ " LEFT JOIN grupos gr on gr.id = algr.id_grupo"
			+ " WHERE gr.id_periodo=:periodo and a.id_carrera =:carrera"
			+ " ORDER BY gr.id ASC", nativeQuery = true)
	List<Alumno> findAllByCarreraAndPeriodo(@Param("carrera")Integer idCarrea, @Param("periodo")Integer idPeriodo);
	
	@Query(value="SELECT a.* FROM alumnos a"
			+ " LEFT JOIN alumnos_grupos algr on algr.id_alumno = a.id"
			+ " LEFT JOIN grupos gr on gr.id = algr.id_grupo"
			+ " WHERE gr.id_periodo=:periodo AND a.id_carrera =:carrera AND gr.id_cuatrimestre IN (1,7) "
			+ " ORDER BY gr.id ASC", nativeQuery = true)
	List<Alumno> findAllAceptarByCarreraAndPeriodo(@Param("carrera")Integer idCarrea, @Param("periodo")Integer idPeriodo);
	
	@Modifying(clearAutomatically = true)
    @Transactional
	@Query(value="INSERT INTO reserva_claves (clave)"
			+ " VALUES"
			+ " (:matri)", nativeQuery = true)
	void insertMatriculaFromAlumnoInReservaClave(@Param("matri") String matri);
	
	@Query(value = "select a.* "
			+ "FROM alumnos a "
			+ "INNER JOIN personas per ON per.id=a.id_persona "
			+ "WHERE CONCAT(per.nombre,' ',per.primer_apellido) iLIKE %:nombre% "
			+ "OR CONCAT(per.primer_apellido,' ',per.segundo_apellido) iLIKE %:nombre% "
			+ "OR CONCAT(per.segundo_apellido, ' ',per.nombre) iLIKE %:nombre% "
			+ "OR a.matricula iLIKE %:nombre% "
			+ "ORDER BY per.primer_apellido, per.segundo_apellido, per.nombre ", nativeQuery = true)
	List<Alumno> findByNombreOrMatricula(@Param("nombre") String nombre);
	
	@Query(value = "select a.*,CONCAT(per.primer_apellido,' ',per.segundo_apellido,' ', per.nombre) AS nombreCompleto "
			+ "FROM alumnos a "
			+ "INNER JOIN alumnos_grupos ag ON a.id=ag.id_alumno "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "INNER JOIN personas per ON per.id=a.id_persona "
			+ "WHERE g.id_profesor=:idProfesor AND g.id_periodo=:idPeriodo AND ag.activo='True' AND (CONCAT(per.nombre,' ',per.primer_apellido) iLIKE %:nombre% "
			+ "OR CONCAT(per.primer_apellido,' ',per.segundo_apellido) iLIKE %:nombre% "
			+ "OR CONCAT(per.segundo_apellido, ' ',per.nombre) iLIKE %:nombre% "
			+ "OR a.matricula iLIKE %:nombre%)  "
			+ "ORDER BY per.primer_apellido, per.segundo_apellido, per.nombre", nativeQuery = true)
	List<AlumnoInfoDTO> findByProfesorAndPeriodoAndNombreOrMatricula(@Param("idProfesor") Integer idProfesor, @Param("idPeriodo") Integer idPeriodo,@Param("nombre") String nombre);
	
	// trae los alumnos de las carreras de la persona (reporte de datos personales)
	@Query(value = "SELECT a.* FROM alumnos a " + "INNER JOIN personas p ON a.id_persona = p.id "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno = a.id " + "INNER JOIN grupos g ON g.id = ag.id_grupo "
			+ "WHERE g.id_carrera IN (SELECT id_carrera FROM persona_carrera WHERE id_persona = :idPersona) AND estatus = 1 "
			+ "AND g.id_periodo = :idPeriodo " + "GROUP BY a.id, p.primer_apellido, p.segundo_apellido, p.nombre "
			+ "ORDER BY TRANSLATE (p.primer_apellido,'ÁÉÍÓÚÜÑ ','AEIOUUN') ASC, TRANSLATE (p.segundo_apellido,'ÁÉÍÓÚÜÑ ','AEIOUUN') ASC, "
			+ "TRANSLATE (p.nombre,'ÁÉÍÓÚÜÑ ','AEIOUUN') ASC ", nativeQuery = true)
	List<Alumno> findAllAlumnosByPersonaCarreraAndActivoAndPeriodo(@Param("idPersona") Integer idPersona, @Param("idPeriodo") Integer idPeriodo);
	
	@Query(value = "select a.*,CONCAT(per.nombre,' ',per.primer_apellido,' ',per.segundo_apellido) AS nombreCompleto "
			+ "FROM alumnos a "
			+ "INNER JOIN alumnos_grupos ag ON a.id=ag.id_alumno "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "INNER JOIN personas per ON per.id=a.id_persona "
			+ "WHERE AND g.id_profesor=:idProfesor AND g.id_periodo=:idPeriodo "
			+ "ORDER BY per.primer_apellido, per.segundo_apellido, per.nombre", nativeQuery = true)
	List<AlumnoInfoDTO> findByProfesorAndPeriodo(@Param("idProfesor") Integer idProfesor, @Param("idPeriodo") Integer idPeriodo);
	
	@Query(value = "SELECT a.* FROM alumnos_grupos ag "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "INNER JOIN alumnos a ON a.id=ag.id_alumno "
			+ "INNER JOIN personas p ON p.id=a.id_persona "
			+ "WHERE ag.id_grupo=:idGrupo AND g.id_periodo=:idPeriodo "
			+ "ORDER BY TRANSLATE (p.primer_apellido,'ÁÉÍÓÚÜÑ ','AEIOUUN') ASC, TRANSLATE (p.segundo_apellido,'ÁÉÍÓÚÜÑ ','AEIOUUN') ASC, "
			+ "TRANSLATE (p.nombre,'ÁÉÍÓÚÜÑ ','AEIOUUN') ASC", nativeQuery = true)
	List<Alumno> findByGrupoAndPeriodo(@Param("idGrupo") Integer idGrupo, @Param("idPeriodo") Integer idPeriodo);
  
	// para traer los adeudos de alumnos por carrera y periodo
	@Query(value = "SELECT a.id as idAlumno, CONCAT(p.nombre,' ',p.primer_apellido,' ',p.segundo_apellido) as alumno, a.matricula, c.concepto, pg.monto as cantidad, pg.concepto as descripcion "
			+ "FROM pagos_generales pg " + "INNER JOIN pago_alumno pa on pg.id=pa.id_pago "
			+ "INNER JOIN alumnos a on pa.id_alumno=a.id " + "INNER JOIN alumnos_grupos ag ON ag.id_alumno = a.id "
			+ "INNER JOIN grupos g ON g.id = ag.id_grupo " + "INNER JOIN personas p ON p.id = a.id_persona "
			+ "INNER JOIN conceptos c ON c.id = pg.id_concepto " + "WHERE pg.status = 0 AND g.id_periodo = :periodo "
			+ "ORDER BY p.nombre, p.primer_apellido, p.segundo_apellido", nativeQuery = true)
	List<AlumnoAdeudoDTO> getAllAlumnoAdeudoByAllCarreraAndPeriodo(@Param("periodo") Integer idPeriodo);
	
	@Query(value = "SELECT a.* FROM alumnos a "
			+ "INNER JOIN personas p ON a.id_persona = p.id "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno = a.id "
			+ "INNER JOIN grupos g ON g.id = ag.id_grupo "
			+ "WHERE g.id_carrera = :idCarrera AND estatus = 1 "
			+ "AND g.id_periodo = :idPeriodo "
			+ "GROUP BY a.id, p.primer_apellido, p.segundo_apellido, p.nombre "
			+ "ORDER BY TRANSLATE (p.primer_apellido,'ÁÉÍÓÚÜÑ ','AEIOUUN') ASC, TRANSLATE (p.segundo_apellido,'ÁÉÍÓÚÜÑ ','AEIOUUN') ASC,"
			+ "TRANSLATE (p.nombre,'ÁÉÍÓÚÜÑ ','AEIOUUN') ASC ", nativeQuery = true)
	List<Alumno> findAllAlumnosByCarreraAndActivoAndPeriodo(@Param("idCarrera") Integer idCarrera, @Param("idPeriodo") Integer idPeriodo);
	
	@Query(value = "SELECT count(a.id) "
			+ "FROM alumnos a "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno=a.id "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "INNER JOIN cuatrimestres cu ON cu.id=g.id_cuatrimestre "
			+ "INNER JOIN carreras c ON c.id=g.id_carrera "
			+ "INNER JOIN personas p ON p.id=a.id_persona "
			+ "WHERE a.estatus = 1 AND g.id=:grupo AND a.documentos_ingresos = 1 "
			+ "AND ag.pagado = 'True' AND ag.fecha_inscripcion IS NOT NULL "
			+ "AND a.id NOT IN ( "
			+ "SELECT id_alumno FROM pago_alumno pa "
			+ "INNER JOIN pagos_generales pg  ON pg.id=pa.id_pago "
			+ "WHERE pa.id_alumno=a.id AND pg.status=0)", nativeQuery = true)
	Integer countAlumnosRegularesByGrupo(@Param("grupo") Integer idGrupo);
	
	@Query(value = "SELECT a.id AS idAlumno, a.matricula, CONCAT(p.primer_apellido, ' ',p.segundo_apellido, ' ',p.nombre)AS nombreCompleto,  c.nombre AS carrera, c.id as idCarrera, "
			+ "a.documentos_ingresos AS entregoDocumentos, CAST( COALESCE(( "
			+ "SELECT MAX(status) FROM pagos_generales pg "
			+ "INNER JOIN pago_alumno pa ON pa.id_pago =pg.id "
			+ "WHERE pa.id_alumno=a.id AND pg.id_concepto=12 "
			+ "), 0) AS INTEGER ) AS pago, p.id as idPersona "
			+ "FROM alumnos a "
			+ "INNER JOIN personas p ON p.id=a.id_persona "
			+ "INNER JOIN carreras c ON c.id=a.id_carrera "
			+ "LEFT JOIN datos_personales dp ON dp.id_persona=p.id "
			+ "LEFT JOIN alumnos_grupos ag ON ag.id_alumno=a.id "
			+ "WHERE ag.id_alumno IS NULL AND a.estatus = 1 "
			+ "ORDER BY p.primer_apellido, p.segundo_apellido, p.nombre, c.nombre  ", nativeQuery = true)
	List<ProspectoDTO> findAllActiveProspectos();
	
	@Query(value = "SELECT a.* "
			+ "FROM alumnos a "
			+ "INNER JOIN personas p ON p.id=a.id_persona "
			+ "INNER JOIN carreras c ON c.id=a.id_carrera "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno=a.id "
			+ "INNER JOIN grupos g on ag.id_grupo=g.id "
			+ "WHERE a.estatus = 1 AND a.documentos_ingresos=1 AND a.id_carrera =:idCarrera AND ag.pagado = 'True' AND g.id_periodo = :idPeriodo "
			+ "ORDER BY a.id DESC ", nativeQuery = true)
	List<Alumno> findAllAceptedProspectos(@Param("idCarrera") Integer idCarrera, @Param("idPeriodo") Integer idPeriodo);
	
	@Query(value = "SELECT DISTINCT(a.id) as idalumno, a.matricula, CONCAT(p.primer_apellido,' ',p.segundo_apellido,' ', p.nombre) as nombre, "
			   + "g.nombre as grupo, c.nombre as carrera, ag.pagado as pago, a.documentos_ingresos as documentos "
			   + "FROM alumnos_grupos ag "
			   + "INNER JOIN alumnos a on a.id = ag.id_alumno "
			   + "INNER JOIN grupos g ON g.id = ag.id_grupo "
			   + "INNER JOIN carreras c ON c.id = g.id_carrera "
			   + "INNER JOIN personas p ON p.id = a.id_persona "
			   + "WHERE fecha_inscripcion IS NULL AND g.id_periodo = :idPeriodo "
			   + "AND c.id IN (SELECT id_carrera FROM persona_carrera WHERE id_persona = :idPersona) "
			   + "AND ag.activo = 'True' ORDER BY nombre", nativeQuery = true)
	List<AlumnoNoReinscritoDTO> findNoReinscritosByPersonaCarreraAndPeriodo(@Param("idPersona") Integer idPersona, @Param("idPeriodo") Integer idPeriodo);
	
	@Query(value = "SELECT DISTINCT(a.id) as idalumno, CONCAT(p.nombre,' ',p.primer_apellido,' ',p.segundo_apellido) as nombre, "
			   + "g.nombre as grupo, c.nombre as carrera, ag.pagado as pago, a.documentos_ingresos as documentos "
			   + "FROM alumnos_grupos ag "
			   + "INNER JOIN alumnos a on a.id = ag.id_alumno "
			   + "INNER JOIN grupos g ON g.id = ag.id_grupo "
			   + "INNER JOIN carreras c ON c.id = g.id_carrera "
			   + "INNER JOIN personas p ON p.id = a.id_persona "
			   + "WHERE fecha_inscripcion IS NULL AND g.id_periodo = :idPeriodo "
			   + "AND ag.activo = 'True' ORDER BY nombre", nativeQuery = true)
	List<AlumnoNoReinscritoDTO> findNoReinscritosByPeriodo(@Param("idPeriodo") Integer idPeriodo);
	
	@Query(value = "SELECT COUNT(DISTINCT(a.*)) FROM alumnos a" + " INNER JOIN personas p on a.id_persona = p.id "
			+ " INNER JOIN alumnos_grupos ag on ag.id_alumno = a.id "
			+ " WHERE p.sexo = :sexo and ag.id_grupo = :grupo ", nativeQuery = true)
	Integer countAlumnosBySexoAndGrupo(@Param("sexo") String sexo, @Param("grupo") Integer idGrupo);
	
	@Query(value = "SELECT COUNT(DISTINCT(a.*)) FROM alumnos a INNER JOIN personas p on a.id_persona = p.id "
			+ "INNER JOIN alumnos_grupos ag on ag.id_alumno = a.id "
			+ "INNER JOIN grupos g ON g.id = ag.id_grupo "
			+ "INNER JOIN carreras c ON c.id = g.id_carrera "
			+ "WHERE p.sexo = :sexo AND c.id IN (SELECT id_carrera FROM persona_carrera WHERE id_persona = :idPersona) " 
			+ "AND g.id_periodo = :idPeriodo", nativeQuery = true)
	Integer countAlumnosBySexoAndPersonaCarrera(@Param("sexo") String sexo, @Param("idPersona") Integer idPersona, @Param("idPeriodo") Integer idPeriodo);
	
	@Query(value = "SELECT COUNT(a.id)AS cantidad "
			+ "FROM alumnos_grupos ag "
			+ "INNER JOIN alumnos a ON a.id=ag.id_alumno "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "WHERE g.id_carrera=:idCarrera AND g.id_periodo=:idPeriodo "
			+ "AND g.id_turno = :idTurno ", nativeQuery = true)
	Integer countAlumnosByCarreraAndCarrera(@Param("idCarrera") Integer idCarrera, @Param("idPeriodo") Integer idPeriodo, @Param("idTurno") Integer idTurno);
	
	@Query(value = "SELECT COUNT(ag.id) as inscritos "
			+ "FROM alumnos_grupos ag "
			+ "INNER JOIN alumnos a on a.id=ag.id_alumno "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "WHERE g.id_carrera=:idCarrera AND g.id_periodo=:idPeriodo AND ag.activo='True' AND a.estatus=1 "
			+ "AND g.id_turno = :idTurno AND ag.created IS NOT NULL ", nativeQuery = true)
	Integer countInscritosByCarreraAndPeriodoAndTurno(@Param("idCarrera") Integer idCarrera, @Param("idPeriodo") Integer idPeriodo, @Param("idTurno") Integer idTurno);
	
	@Query(value = "SELECT COUNT(ag.id) as inscritos "
			+ "FROM alumnos_grupos ag "
			+ "INNER JOIN alumnos a on a.id=ag.id_alumno "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "WHERE g.id_carrera=:idCarrera AND g.id_periodo=:idPeriodo "
			+ "AND g.id_turno = :idTurno AND ag.activo=false AND a.estatus=0  ", nativeQuery = true)
	Integer countBajasByCarreraAndPeriodoAndTurno(@Param("idCarrera") Integer idCarrera, @Param("idPeriodo") Integer idPeriodo, @Param("idTurno") Integer idTurno);
	
	@Query(value = "SELECT a.id AS idAlumno, a.matricula, p.primer_apellido AS primerApellido, p.segundo_apellido AS segundoApellido,  "
			+ "p.nombre,  a.id_persona AS idPersona, g.nombre AS grupoAnterior, "
			+ "(SELECT COALESCE(gg.nombre, '') "
			+ "FROM alumnos_grupos agg "
			+ "INNER JOIN grupos gg ON gg.id=agg.id_grupo "
			+ "WHERE gg.id_periodo=:periodo+1 AND gg.id_carrera=:carrera AND agg.id_alumno=a.id)AS grupoActual "
			+ "FROM alumnos a "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno=a.id "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "INNER JOIN cuatrimestres cu ON cu.id=g.id_cuatrimestre "
			+ "INNER JOIN carreras c ON c.id=g.id_carrera "
			+ "INNER JOIN personas p ON p.id=a.id_persona "
			+ "WHERE a.estatus = 1 AND g.id_carrera=:carrera AND g.id_periodo=:periodo AND a.documentos_ingresos = 1 "
			+ "AND ag.pagado = 'True' AND g.id_turno = :idTurno AND ag.fecha_inscripcion IS NOT NULL "
			+ " AND a.id NOT IN ("
			+ " SELECT id_alumno FROM pago_alumno pa "
			+ " INNER JOIN pagos_generales pg  ON pg.id=pa.id_pago "
			+ " WHERE pa.id_alumno=a.id AND pg.status=0) "
			+ "ORDER BY c.nombre, g.nombre DESC, p.primer_apellido, p.segundo_apellido, p.nombre", nativeQuery = true)
	List<AlumnoRegularDTO> obtenerRegularesByCarreraAndPeriodoAndTurno(@Param("carrera") Integer carrera, @Param("periodo") Integer periodo, @Param("idTurno") Integer idTurno);
	
	@Query(value = "SELECT a.*,CONCAT(per.nombre,' ',per.primer_apellido,' ',per.segundo_apellido) AS nombreCompleto "
			+ "FROM alumnos a "
			+ "INNER JOIN personas per ON per.id=a.id_persona "
			+ "INNER JOIN alumnos_grupos ag ON a.id=ag.id_alumno "
			+ "INNER JOIN grupos g ON g.id = ag.id_grupo AND g.id_periodo=:idPeriodo "
			+ "INNER JOIN carreras c ON c.id = g.id_carrera "
			+ "AND c.id IN (SELECT id_carrera FROM persona_carrera WHERE id_persona = :idPersona) "
			+ "WHERE CONCAT(per.nombre,' ',per.primer_apellido) iLIKE %:nombre% "
			+ "OR CONCAT(per.segundo_apellido, ' ',per.nombre) iLIKE %:nombre% "
			+ "OR CONCAT(per.primer_apellido,' ',per.segundo_apellido) iLIKE %:nombre% "
			+ "OR a.matricula iLIKE %:nombre% "
			+ "ORDER BY per.primer_apellido, per.segundo_apellido, per.nombre", nativeQuery = true)
	List<AlumnoInfoDTO> findByPersonaCarreraAndNombreAndPeriodo(@Param("idPersona") Integer idPersona, @Param("idPeriodo") Integer idPeriodo, @Param("nombre") String nombre);
	
	@Query(value = "SELECT a.*,CONCAT(per.nombre,' ',per.primer_apellido,' ',per.segundo_apellido) AS nombreCompleto "
			+ "FROM alumnos a "
			+ "INNER JOIN personas per ON per.id=a.id_persona "
			+ "INNER JOIN alumnos_grupos ag ON a.id=ag.id_alumno "
			+ "INNER JOIN grupos g ON g.id = ag.id_grupo "
			+ "INNER JOIN carreras c ON c.id = g.id_carrera "
			+ "WHERE c.id IN (SELECT id_carrera FROM persona_carrera WHERE id_persona = :idPersona) "
			+ "AND g.id_periodo = :idPeriodo AND a.estatus = 1", nativeQuery = true)
	List<AlumnoInfoDTO> findByPersonaCarreraAndPeriodo(@Param("idPersona") Integer idPersona, @Param("idPeriodo") Integer idPeriodo);
	
	@Query(value = "SELECT a.*,CONCAT(per.nombre,' ',per.primer_apellido,' ',per.segundo_apellido) AS nombreCompleto "
			+ "FROM alumnos a "
			+ "INNER JOIN personas per ON per.id=a.id_persona "
			+ "INNER JOIN alumnos_grupos ag ON a.id=ag.id_alumno "
			+ "INNER JOIN grupos g ON g.id = ag.id_grupo AND g.id_periodo = :idPeriodo "
			+ "WHERE CONCAT(per.nombre,' ',per.primer_apellido) iLIKE %:nombre% "
			+ "OR CONCAT(per.segundo_apellido, ' ',per.nombre) iLIKE %:nombre% "
			+ "OR CONCAT(per.primer_apellido,' ',per.segundo_apellido) iLIKE %:nombre% "
			+ "OR a.matricula iLIKE %:nombre% "
			+ "ORDER BY per.primer_apellido, per.segundo_apellido, per.nombre", nativeQuery = true)
	List<AlumnoInfoDTO> findAllByNombreOrMatriculaAndPeriodo(@Param("nombre") String nombre, @Param("idPeriodo") Integer idPeriodo);
	
	@Query(value = "SELECT a.*,CONCAT(per.nombre,' ',per.primer_apellido,' ',per.segundo_apellido) AS nombreCompleto "
			+ "FROM alumnos a "
			+ "INNER JOIN personas per ON per.id=a.id_persona "
			+ "INNER JOIN alumnos_grupos ag ON a.id=ag.id_alumno "
			+ "INNER JOIN grupos g ON g.id = ag.id_grupo "
			+ "WHERE g.id_periodo= :idPeriodo "
			+ "ORDER BY per.primer_apellido, per.segundo_apellido, per.nombre", nativeQuery = true)
	List<AlumnoInfoDTO> findAllByPeriodo(@Param("idPeriodo") Integer idPeriodo);
			
	@Query(value = "SELECT a.id AS idAlumno, a.matricula, p.primer_apellido AS primerApellido, p.segundo_apellido AS segundoApellido, "
			+ "	p.nombre,  a.id_persona AS idPersona, "
			+ "	(SELECT COALESCE(gg.nombre, '') "
			+ "	FROM alumnos_grupos agg "
			+ "	INNER JOIN grupos gg ON gg.id=agg.id_grupo "
			+ "	WHERE gg.id_periodo=:periodo AND gg.id_carrera=:carrera AND agg.id_alumno=a.id "
			+ " ORDER BY ag.id DESC LIMIT 1)AS grupoActual "
			+ "	FROM alumnos a "
			+ "	INNER JOIN carreras c ON a.id_carrera = c.id "
			+ "	INNER JOIN personas p ON p.id=a.id_persona "
			+ "	LEFT JOIN alumnos_grupos ag ON ag.id_alumno = a.id "
			+ "	LEFT JOIN grupos g ON ag.id_grupo = g.id "
			+ "	WHERE a.id_carrera=:carrera AND a.matricula ILIKE '%-3%' "
			+ "	ORDER BY c.nombre, p.primer_apellido, p.segundo_apellido, p.nombre", nativeQuery = true)
	List<AlumnoRegularDTO> findAllRegularProspecto(@Param("carrera") Integer carrera, @Param("periodo") Integer periodo);
	
	@Query(value = "SELECT id_alumno AS alumno, id_carga_horaria AS carga, "
			+ "id_corte_evaluativo AS corte, m.nombre AS materia "
			+ "FROM calificacion_corte cc "
			+ "INNER JOIN cargas_horarias ch ON ch.id = cc.id_carga_horaria "
			+ "INNER JOIN materias m ON ch.id_materia = m.id "
			+ "WHERE valor < 7.5 AND ch.id_periodo = 11 AND ch.activo = true ", nativeQuery = true)
	List<RemedialAlumnoDTO> findAllRemedial();
	
	@Query(value = "SELECT c.clave AS siglascarrera, c.nombre AS carrera, a.matricula ,"
			+ "p.nombre AS nombre, p.primer_apellido as primerapellido, p.segundo_apellido as segundoapellido, p.email AS correo, p.sexo ,"
			+ "da.discapacitado, da.indigena, dp.curp, g.nombre as grupoactual, c2.consecutivo as cuatrimestre "
			+ "FROM alumnos a "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno = a.id "
			+ "INNER JOIN grupos g ON ag.id_grupo = g.id "
			+ "INNER JOIN cuatrimestres c2 ON g.id_cuatrimestre = c2.id "
			+ "INNER JOIN personas p ON p.id = a.id_persona "
			+ "INNER JOIN carreras c ON c.id = a.id_carrera "
			+ "INNER JOIN datos_alumno da ON da.id_alumno = a.id "
			+ "INNER JOIN datos_personales dp ON dp.id_persona = p.id "
			+ "WHERE g.id_cuatrimestre = :idCuatrimestre AND g.id_carrera = :idCarrera  "
			+ "AND g.id_periodo = :idPeriodo "
			+ "ORDER BY g.nombre, TRANSLATE (p.primer_apellido,'ÁÉÍÓÚÜÑ ','AEIOUUN') ASC, TRANSLATE (p.segundo_apellido,'ÁÉÍÓÚÜÑ ','AEIOUUN') ASC, "
			+ "TRANSLATE (p.nombre,'ÁÉÍÓÚÜÑ ','AEIOUUN') ASC", nativeQuery = true)
	List<AlumnoRegularDTO> findAllByCarreraAndCuatrimestreAndPeriodo(@Param("idCarrera") Integer idCarrera, @Param("idCuatrimestre") Integer idCuatrimestre,
			@Param("idPeriodo") Integer idPeriodo);
	
}

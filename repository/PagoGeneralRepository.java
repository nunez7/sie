package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.model.dto.FolioDTO;

public interface PagoGeneralRepository extends CrudRepository<PagoGeneral, Integer>{
	
	@Query(value = "SELECT pg.* FROM pagos_generales pg "
			+ "INNER JOIN pago_alumno pa ON pa.id_pago=pg.id "
			+ "WHERE pa.id_alumno =:idAlumno AND pg.status =:status AND pg.activo=true "
			+ "ORDER BY created DESC", nativeQuery = true)
	List<PagoGeneral> findByAlumnoAndStatusOrderByCreatedDesc(@Param("idAlumno") Integer idAlumno, @Param("status") Integer status);
	
	@Query(value = "SELECT pg.* FROM pagos_generales pg "
			+ "INNER JOIN pago_alumno pa ON pg.id=pa.id_pago "
			+ "WHERE pa.id_alumno =:idAlumno AND pg.folio =:folio AND pg.status =:status AND pg.activo=true "
			+ "ORDER BY created DESC", nativeQuery = true)
	List<PagoGeneral> findByAlumnoAndStatusAndFolioOrderByCreatedDesc(@Param("idAlumno") Integer idAlumno, @Param("folio") String folio, @Param("status") Integer status);
	
	@Query(value="SELECT LPAD(CAST(COUNT(a.referencia)+1 AS VARCHAR),3,'0') from pagos_generales a" 
			   + " WHERE a.referencia ILIKE '%:referencia%'", nativeQuery = true) 
	String findUltimaReferenciaSEP(@Param("referencia") String referencia);
	
	@Query(value="SELECT CAST(COUNT(a.referencia_fondos)+100 AS VARCHAR)from pagos_generales a" 
			   + " WHERE a.referencia_fondos ILIKE %:referencia%", nativeQuery = true) 
	String findUltimaReferencia(@Param("referencia") String referencia);
	

	@Query(value = "SELECT pg.* FROM pagos_generales pg "
			+ "INNER JOIN pago_alumno pa on pg.id=pa.id_pago " 
			 + "WHERE pg.id_concepto= :idConcepto and pa.id_alumno= :idAlumno and pg.activo=true", nativeQuery = true)
	PagoGeneral findByAlumnoAndConceptosAndActivo(@Param("idAlumno") Alumno alumno,@Param("idConcepto") Integer concepto);
	
	@Query(value = "SELECT pg.* FROM pagos_generales pg " 
				 + "ORDER BY pg.id DESC LIMIT 1 ", nativeQuery = true)
	PagoGeneral findLastPagoGeneral();
	
	/*
	@Query(value = "SELECT pg.* "
			+ "FROM pagos_generales pg "
			+ "INNER JOIN pago_asignatura pa on pg.id=pa.id_pago "
			+ "INNER JOIN pago_alumno pal on pg.id=pal.id_pago "
			+ "WHERE pal.id_alumno= :idAlumno and pg.id_concepto= :idConcepto AND pa.id_asignatura= :idAsignatura AND pg.activo=true", nativeQuery = true)
	PagoGeneral findByAlumnoAndConceptoAndAsignatura(@Param("idAlumno") Integer idAlumno, @Param("idConcepto") Integer idConcepto, @Param("idAsignatura") Integer idAsignatura);
	
	@Query(value = "SELECT pg.* "
			+ "FROM pagos_generales pg "
			+ "INNER JOIN pago_asignatura pa on pg.id=pa.id_pago "
			+ "INNER JOIN pago_alumno pal on pg.id=pal.id_pago "
			+ "WHERE pal.id_alumno= :idAlumno and pg.id_concepto= :idConcepto AND pa.id_asignatura= :idAsignatura AND pa.id_corte_evaluativo=:idCorteEvaluativo AND pg.activo=true", nativeQuery = true)
	PagoGeneral findByAlumnoAndConceptoAndAsignaturaAndCorteEvaluativo(@Param("idAlumno") Integer idAlumno, @Param("idConcepto") Integer idConcepto, @Param("idAsignatura") Integer idAsignatura, @Param("idCorteEvaluativo") Integer idCorteEvaluativo);
	 */
	
	// metodo modificado
		@Query(value = "SELECT pg.* "
				+ "FROM pagos_generales pg "
				+ "INNER JOIN pago_asignatura pa on pg.id=pa.id_pago "
				+ "INNER JOIN pago_alumno pal on pg.id=pal.id_pago "
				+ "WHERE pal.id_alumno= :idAlumno and pg.id_concepto= :idConcepto AND pa.id_carga_horaria= :idCargaHoraria AND pg.activo=true", nativeQuery = true)
		PagoGeneral findByAlumnoAndConceptoAndCargaHoraria(@Param("idAlumno") Integer idAlumno, @Param("idConcepto") Integer idConcepto, @Param("idCargaHoraria") Integer idCargaHoraria);
		
		@Query(value = "SELECT pg.* "
				+ "FROM pagos_generales pg "
				+ "INNER JOIN pago_asignatura pa on pg.id=pa.id_pago "
				+ "INNER JOIN pago_alumno pal on pg.id=pal.id_pago "
				+ "WHERE pal.id_alumno= :idAlumno and pg.id_concepto= :idConcepto AND pa.id_carga_horaria=:idCargaHoraria AND pa.id_corte_evaluativo=:idCorteEvaluativo AND pg.activo=true", nativeQuery = true)
		PagoGeneral findByAlumnoAndConceptoAndCargaHorariaAndCorteEvaluativo(@Param("idAlumno") Integer idAlumno, @Param("idConcepto") Integer idConcepto, @Param("idCargaHoraria") Integer idCargaHoraria, @Param("idCorteEvaluativo") Integer idCorteEvaluativo);
		
		@Query(value = "SELECT count(pg.*) FROM pagos_generales pg " 
				 + "INNER JOIN pago_alumno pa ON pa.id_pago=pg.id "
				 + "WHERE pa.id_alumno=:idAlumno AND pg.status=:status AND pg.activo=true", nativeQuery = true)
		Integer countByAlumnoAndStatus(@Param("idAlumno") Integer idAlumno, @Param("status") Integer status);
		
		@Query(value = "SELECT pg.folio AS Folio, MAX(COALESCE(a.id, c.id)) as idCliente, MAX(CONCAT(COALESCE(c.nombre_cliente,' '), "
				+ "COALESCE(p.primer_apellido,''), ' ' , COALESCE(p.segundo_apellido, '') , ' ' , COALESCE(p.nombre,''))) AS nombre, "
				+ "SUM((pg.cantidad  * pg.monto_unitario) - ((COALESCE(pg.descuento,0) * (pg.cantidad * pg.monto_unitario))/100) ) AS Monto, "
				+ "MAX(CAST(pg.status AS INT)) AS Activo, MAX(pg.created) AS Fecha, MAX(pg.tipo)AS TipoPago "
				+ "FROM pagos_generales pg "
				+ "LEFT JOIN pago_cliente pc ON pc.id_pago = pg.id "
				+ "LEFT JOIN clientes c  ON pc.id_cliente = c.id "
				+ "LEFT JOIN pago_alumno pa ON pa.id_pago = pg.id "
				+ "LEFT JOIN alumnos a ON pa.id_alumno = a.id "
				+ "LEFT JOIN personas p  ON a.id_persona = p.id "
				+ "WHERE (pg.folio iLIKE %:like% "
				+ "OR CONCAT(p.nombre,' ',p.primer_apellido) iLIKE %:like% "
				+ "OR CONCAT(p.primer_apellido,' ',p.segundo_apellido) iLIKE %:like% "
				+ "OR CONCAT(p.segundo_apellido, ' ',p.nombre) iLIKE %:like% "
				+ "OR c.nombre_cliente iLIKE %:like% ) AND pg.folio NOT LIKE '' "
				+ "GROUP BY pg.folio ORDER BY pg.folio DESC ", nativeQuery = true)
		List<FolioDTO> FindByFolioOrNombreOrCliente(@Param("like") String like);
		
		@Query(value = "SELECT pg.folio AS Folio, MAX(CONCAT(COALESCE(c.nombre_cliente,' '), "
				+ "COALESCE(p.primer_apellido,''), ' ' , COALESCE(p.segundo_apellido, '') , ' ' , COALESCE(p.nombre,''))) AS nombre, "
				+ "SUM((pg.cantidad  * pg.monto_unitario) - ((COALESCE(pg.descuento,0) * (pg.cantidad * pg.monto_unitario))/100) ) AS Monto, "
				+ "MAX(CAST(pg.activo AS INT)) AS Activo, MAX(pg.created) AS Fecha, MAX(pg.tipo)AS TipoPago "
				+ "FROM pagos_generales pg "
				+ "LEFT JOIN pago_cliente pc ON pc.id_pago = pg.id "
				+ "LEFT JOIN clientes c  ON pc.id_cliente = c.id "
				+ "LEFT JOIN pago_alumno pa ON pa.id_pago = pg.id "
				+ "LEFT JOIN alumnos a ON pa.id_alumno = a.id "
				+ "LEFT JOIN personas p  ON a.id_persona = p.id "
				+ "WHERE pg.folio = :folio "
				+ "GROUP BY pg.folio ORDER BY pg.folio DESC ", nativeQuery = true)
		FolioDTO findFolio (@Param("folio") String folio);
		
		List<PagoGeneral> findByFolio(String folio);
		
		@Query(value = "SELECT * FROM pagos_generales pg "
				+ "	WHERE pg.folio= :folio "
				+ "	ORDER BY id  DESC LIMIT 1" ,nativeQuery = true)
		PagoGeneral findLastByFolio(String folio);
		
		@Query(value = "SELECT pg.folio AS Folio, MAX((COALESCE(c.nombre_cliente,p.nombre))) AS nombre, MAX((COALESCE(p.primer_apellido ,''))) AS primerApellido, "
				+ "MAX((COALESCE(p.segundo_apellido ,''))) AS segundoApellido, "
				+ "MAX(COALESCE(c.rfc  ,a.matricula)) as matricula, MAX(COALESCE(c.clave, ca.nombre)) as carrera, "
				+ "SUM(DISTINCT(pg.cantidad  * pg.monto_unitario) - ((COALESCE(pg.descuento,0) * (pg.cantidad * pg.monto_unitario))/100) ) AS Monto, "
				+ "MAX(CAST(pg.activo AS INT)) AS Activo, MAX(pg.created) AS Fecha, MAX(pg.tipo)AS TipoPago, "
				+ "MAX(COALESCE(c.sector, g.nombre)) as grupo, MAX(COALESCE(c.tipo, concat(cc.fecha_inicio,' - ',cc.fecha_fin))) as ciclos, MAX(COALESCE(c.tamano, CAST(cu.consecutivo AS VARCHAR))) as cuatrimestre,"
				+ "MAX(COALESCE(pr.fecha_cobro, null)) as fechaPago, MAX(COALESCE(CONCAT(p2.primer_apellido, ' ', p2.segundo_apellido,' ',p2.nombre), '')) as pagoRecibe, MAX(COALESCE(a.id, 0)) as idAlumno "
				+ "FROM pagos_generales pg "
				+ "LEFT JOIN pago_recibe pr ON pr.id_pago = pg.id "
				+ "LEFT JOIN personas p2 ON pr.id_cajero  = p2.id "
				+ "LEFT JOIN pago_cliente pc ON pc.id_pago = pg.id "
				+ "LEFT JOIN clientes c  ON pc.id_cliente = c.id "
				+ "LEFT JOIN pago_alumno pa ON pa.id_pago = pg.id "
				+ "LEFT JOIN alumnos a ON pa.id_alumno = a.id "
				+ "LEFT JOIN alumnos_grupos ag ON ag.id_alumno  = a.id "
				+ "LEFT JOIN grupos g ON ag.id_grupo  = g.id "
				+ "LEFT JOIN cuatrimestres cu  ON g.id_cuatrimestre = cu.id "
				+ "LEFT JOIN periodos pe ON g.id_periodo  = pe.id "
				+ "LEFT JOIN ciclos cc  ON pe.id_ciclo  = cc.id "
				+ "LEFT JOIN carreras ca  ON g.id_carrera  = ca.id "
				+ "LEFT JOIN personas p  ON a.id_persona = p.id "
				+ "WHERE pg.folio = :folio "
				+ "GROUP BY pg.folio ORDER BY pg.folio DESC", nativeQuery = true)
		FolioDTO findFolioRecibo(@Param("folio") String folio);
	
	
}

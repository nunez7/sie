package edu.mx.utdelacosta.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.model.dto.FolioDTO;
import edu.mx.utdelacosta.model.dtoreport.PagosGeneralesDTO;

public interface PagoGeneralRepository extends CrudRepository<PagoGeneral, Integer>{
	
	@Query(value = "SELECT pg.* FROM pagos_generales pg "
			+ "INNER JOIN pago_alumno pa ON pa.id_pago=pg.id "
			+ "WHERE pa.id_alumno =:idAlumno AND pg.status =:status AND pg.activo=true "
			+ "ORDER BY folio DESC", nativeQuery = true)
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
		
	// prueba para reporte de caja detallado
	@Query(value="SELECT * FROM pagos_generales WHERE status = 1 ORDER BY id DESC LIMIT 50", nativeQuery = true)
	List<PagoGeneral> findByLast100();	
	
	// busca los pagos para reporte detallado con fechas de inicio, fin y un cajero
	@Query(value = "SELECT COALESCE(pg.folio, 'S/F') AS folio, "
			+ "COALESCE(a.matricula, c.clave, per.no_empleado, 'N/A') as matricula, " + "CASE "
			+ "	WHEN(ap.nombre IS NOT NULL AND ap.primer_apellido IS NOT NULL AND ap.segundo_apellido IS NOT NULL) "
			+ "	THEN CONCAT(ap.nombre,' ', ap.primer_apellido,' ', ap.segundo_apellido) "
			+ "	WHEN(c.nombre_cliente IS NOT NULL) " + "	THEN c.nombre_cliente "
			+ "	WHEN(ppp.nombre IS NOT NULL AND ppp.primer_apellido IS NOT NULL AND ppp.segundo_apellido IS NOT NULL) "
			+ "	THEN CONCAT(ppp.nombre,' ', ppp.primer_apellido,' ', ppp.segundo_apellido) "
			+ "END as nombre, pg.concepto as concepto, pg.status as estatus, CONCAT(p.nombre,' ',p.primer_apellido,' ',p.segundo_apellido) as cajero, "
			+ "pg.monto as monto, pr.fecha_cobro as fecha, pg.factura as factura " + "FROM pagos_generales pg "
			+ "INNER JOIN pago_recibe pr ON pg.id = pr.id_pago " + "INNER JOIN personas p ON p.id = pr.id_cajero "
			+ "LEFT JOIN pago_alumno pa ON pa.id_pago = pg.id " + "LEFT JOIN alumnos a ON a.id = pa.id_alumno "
			+ "LEFT JOIN personas ap ON ap.id = a.id_persona " + "LEFT JOIN pago_cliente pc ON pc.id_pago = pg.id "
			+ "LEFT JOIN clientes c ON c.id = pc.id_cliente " + "LEFT JOIN pago_persona pp ON pp.id_pago = pg.id "
			+ "LEFT JOIN personas ppp ON ppp.id = pp.id_persona " + "LEFT JOIN personal per ON per.id_persona = ppp.id "
			+ "WHERE pg.status = 1 AND p.id = :cajero " + "AND pr.fecha_cobro BETWEEN :fechaInicio AND :fechaFin "
			+ "ORDER BY pg.id", nativeQuery = true)
	List<PagosGeneralesDTO> findByFechaInicioAndFechaFinAndCajero(@Param("fechaInicio") Date fechaInicio,
			@Param("fechaFin") Date fechaFin, @Param("cajero") Integer idCajero);

	// busca los pagos para reporte detallado con fechas de inicio, fin y todos los cajeros
	@Query(value = "SELECT COALESCE(pg.folio, 'S/F') AS folio, "
			+ "COALESCE(a.matricula, c.clave, per.no_empleado, 'N/A') as matricula, " + "CASE "
			+ "	WHEN(ap.nombre IS NOT NULL AND ap.primer_apellido IS NOT NULL AND ap.segundo_apellido IS NOT NULL) "
			+ "	THEN CONCAT(ap.nombre,' ', ap.primer_apellido,' ', ap.segundo_apellido) "
			+ "	WHEN(c.nombre_cliente IS NOT NULL) " + "	THEN c.nombre_cliente "
			+ "	WHEN(ppp.nombre IS NOT NULL AND ppp.primer_apellido IS NOT NULL AND ppp.segundo_apellido IS NOT NULL) "
			+ "	THEN CONCAT(ppp.nombre,' ', ppp.primer_apellido,' ', ppp.segundo_apellido) "
			+ "END as nombre, pg.concepto as concepto, pg.status as estatus, CONCAT(p.nombre,' ',p.primer_apellido,' ',p.segundo_apellido) as cajero, "
			+ "pg.monto as monto, pr.fecha_cobro as fecha, pg.factura as factura " + "FROM pagos_generales pg "
			+ "INNER JOIN pago_recibe pr ON pg.id = pr.id_pago " + "INNER JOIN personas p ON p.id = pr.id_cajero "
			+ "LEFT JOIN pago_alumno pa ON pa.id_pago = pg.id " + "LEFT JOIN alumnos a ON a.id = pa.id_alumno "
			+ "LEFT JOIN personas ap ON ap.id = a.id_persona " + "LEFT JOIN pago_cliente pc ON pc.id_pago = pg.id "
			+ "LEFT JOIN clientes c ON c.id = pc.id_cliente " + "LEFT JOIN pago_persona pp ON pp.id_pago = pg.id "
			+ "LEFT JOIN personas ppp ON ppp.id = pp.id_persona " + "LEFT JOIN personal per ON per.id_persona = ppp.id "
			+ "WHERE pg.status = 1 AND pr.fecha_cobro BETWEEN :fechaInicio AND :fechaFin "
			+ "ORDER BY pg.id ", nativeQuery = true)
	List<PagosGeneralesDTO> findByFechaInicioAndFechaFinAndAllCajeros(@Param("fechaInicio") Date fechaInicio,
			@Param("fechaFin") Date fechaFin);

	@Query(value = "SELECT MAX(pg.status) FROM pagos_generales pg " + "LEFT JOIN pago_alumno pa ON pa.id_pago=pg.id "
			+ "LEFT JOIN alumnos al on pa.id_alumno = al.id "
			+ "WHERE al.id_persona=:idPersona AND pg.id_concepto=12 AND pg.activo=true ", nativeQuery = true)
	Integer countPagoExamenAdmision(@Param("idPersona") Integer idPersona);
	
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
//			+ "OR p.nombre iLIKE %:like% "
			+ "OR CONCAT(p.nombre,' ',p.primer_apellido) iLIKE %:like% "
//			+ "OR p.primer_apellido iLIKE %:like% "
			+ "OR CONCAT(p.primer_apellido,' ',p.segundo_apellido) iLIKE %:like% "
//			+ "OR p.segundo_apellido iLIKE %:like% "
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
	
	//para generar el folio 
	@Query(value = "SELECT CAST(CAST(MAX(folio) AS INTEGER)+ 1 AS VARCHAR) as folio FROM pagos_generales ", nativeQuery = true)
	String generateFolio();
	
	//busca adeudos o pagos de personal (pagoPersona)
	@Query(value = "SELECT pg.* FROM pagos_generales pg "
			+ "INNER JOIN pago_persona pp ON pg.id = pp.id_pago "
			+ "WHERE pp.id_persona = :idPersona AND pg.status = :status AND pg.activo = 'True' "
			+ "ORDER BY pg.created DESC", nativeQuery = true)
	List<PagoGeneral> findByPersonaAndStatusOrderByCreatedDesc(@Param("idPersona") Integer idPersona, @Param("status") Integer status);
	
	//busca los pagos o adeduos de un cliente (pagoCliente)
	@Query(value = "SELECT pg.* FROM pagos_generales pg "
			+ "INNER JOIN pago_cliente pc ON pg.id = pc.id_pago "
			+ "WHERE pc.id_cliente = :cliente AND pg.status = :status "
			+ "AND pg.activo = 'True' ORDER BY pg.created DESC", nativeQuery = true)
	List<PagoGeneral> findByClienteAndStatusOrderByCreatedDesc(@Param("cliente") Integer idCliente, @Param("status") Integer status);
	
	//busca los pagos o adeduos por referencia
	@Query(value = "SELECT * FROM pagos_generales "
			+ "WHERE referencia = :referencia AND activo = 'True' "
			+ "AND status = 0", nativeQuery = true)
	List<PagoGeneral> findByReferencia(@Param("referencia") String referencia);
}

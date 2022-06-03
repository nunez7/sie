package edu.mx.utdelacosta.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Alumno;
import edu.mx.utdelacosta.model.PagoGeneral;

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
		
	// prueba para reporte de caja detallado
	@Query(value="SELECT * FROM pagos_generales WHERE status = 1 ORDER BY id DESC LIMIT 50", nativeQuery = true)
	List<PagoGeneral> findByLast100();	
	
	//busca los pagos para reporte detallado con fechas de inicio, fin y un cajero
	@Query(value="SELECT pg.* FROM pagos_generales pg "
			+ "INNER JOIN pago_recibe pr ON pg.id = pr.id_pago "
			+ "INNER JOIN personas p ON p.id = pr.id_cajero "
			+ "WHERE pg.status = 1 AND p.id = :cajero "
			+ "AND pr.fecha_cobro BETWEEN :fechaInicio AND :fechaFin", nativeQuery = true)
	List<PagoGeneral> findByFechaInicioAndFechaFinAndCajero(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin, @Param("cajero") Integer idCajero);
	
	//busca los pagos para reporte detallado con fechas de inicio, fin y todos los cajeros
	@Query(value="SELECT pg.* FROM pagos_generales pg "
			+ "INNER JOIN pago_recibe pr ON pg.id = pr.id_pago "
			+ "INNER JOIN personas p ON p.id = pr.id_cajero "
			+ "WHERE pg.status = 1 "
			+ "AND pr.fecha_cobro BETWEEN :fechaInicio AND :fechaFin", nativeQuery = true)
	List<PagoGeneral> findByFechaInicioAndFechaFinAndAllCajeros(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
	
}

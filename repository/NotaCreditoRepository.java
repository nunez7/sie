package edu.mx.utdelacosta.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.NotaCredito;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.model.dto.NotaCreditoDTO;

public interface NotaCreditoRepository extends CrudRepository<NotaCredito, Integer> {
	
	NotaCredito findByPagoGeneral(PagoGeneral pagoGeneral);
	
	@Query(value = "select (( "
			+ "	select sum(pg2.monto) "
			+ "	from pagos_generales pg2 "
			+ "	where pg2.folio = :folio"
			+ "	) - coalesce(nc.cantidad,0)) as montoTotal, (nc.comentario) , (nc.cantidad) "
			+ "	from pagos_generales pg "
			+ "	left join nota_credito nc on nc.id_pago_general = pg.id "
			+ "	where pg.folio = :folio "
			+ "	group by nc.cantidad, nc.comentario limit 1", nativeQuery = true)
	NotaCreditoDTO findByFolio(@Param("folio") String folio);
	
	@Query(value = "SELECT COALESCE(SUM(cantidad),0) "
			+ "	FROM nota_credito nc "
			+ "	WHERE DATE(nc.fecha_alta) BETWEEN :fechaInicio AND :fechaFin", nativeQuery = true)
	Double findTotalByFechaInicioAndFechaFin(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
	
	@Query(value = "SELECT COALESCE(SUM(nc.cantidad),0) "
			+ "	FROM nota_credito nc "
			+ "	INNER JOIN pagos_generales pg ON nc.id_pago_general = pg.id "
			+ "	INNER JOIN pago_recibe pr ON pr.id_pago = pg.id "
			+ "	WHERE DATE(nc.fecha_alta) BETWEEN :fechaInicio AND :fechaFin AND pr.id_cajero = :cajero ", nativeQuery = true)
	Double findTotalByFechaInicioAndFechaFinAndCajero(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin, @Param("cajero") Integer cajero);
	
	@Query(value = "SELECT COALESCE(SUM(cantidad),0) montoTotal, COALESCE(COUNT(nc.id),0) AS cantidad "
			+ "	FROM nota_credito nc "
			+ "	WHERE DATE(nc.fecha_alta) BETWEEN :fechaInicio AND :fechaFin", nativeQuery = true)
	NotaCreditoDTO findConcentradoByFechaInicioAndFechaFin(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
	
	@Query(value = "SELECT COALESCE(SUM(nc.cantidad),0) as montoTotal, COALESCE(COUNT(nc.id),0) AS cantidad  "
			+ "	FROM nota_credito nc "
			+ "	INNER JOIN pagos_generales pg ON nc.id_pago_general = pg.id "
			+ "	INNER JOIN pago_recibe pr ON pr.id_pago = pg.id "
			+ "	WHERE DATE(nc.fecha_alta) BETWEEN :fechaInicio AND :fechaFin AND pr.id_cajero = :cajero ", nativeQuery = true)
	NotaCreditoDTO findConcentradoByFechaInicioAndFechaFinAndCajero(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin, @Param("cajero") Integer cajero);

}

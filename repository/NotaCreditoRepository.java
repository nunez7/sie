package edu.mx.utdelacosta.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.NotaCredito;
import edu.mx.utdelacosta.model.PagoGeneral;
import edu.mx.utdelacosta.model.dto.NotaCreditoDTO;

public interface NotaCreditoRepository extends CrudRepository<NotaCredito, Integer> {
	
	NotaCredito findByPagoGeneral(PagoGeneral pagoGeneral);
	
	/*
	@Query(value = "SELECT ( "
			+ "	SELECT SUM(pg2.monto) "
			+ "	FROM pagos_generales pg2 "
			+ "	WHERE pg2.folio = :folio "
			+ "	) - nc.cantidad as montoTotal, nc.comentario , nc.cantidad "
			+ "	FROM nota_credito nc "
			+ "	INNER JOIN pagos_generales pg on nc.id_pago_general = pg.id "
			+ "	WHERE pg.folio = :folio "
			+ "	GROUP BY nc.cantidad, nc.comentario ", nativeQuery = true)
	NotaCreditoDTO findByFolio(@Param("folio") String folio);
	*/
	
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

}

package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Modulo;

public interface ModulosRepository extends CrudRepository<Modulo, Integer> {
	// select * from Moduo where activo = ?
	List<Modulo> findByActivoOrderByNombreAsc(boolean estatus);

	@Query(value = "SELECT m.cve_modulo, m.nombre, m.activo FROM modulos m "
			+ "INNER JOIN modulo_rol mr ON m.cve_modulo = mr.id_modulo "
			+ "INNER JOIN roles r ON mr.id_rol = r.id WHERE m.activo=true AND r.id = :rol ", nativeQuery = true)
	List<Modulo> buscarModulosPorRol(@Param("rol") Integer idRol);
	
	@Query(value = "SELECT m.cve_modulo, m.nombre, m.activo "
			+ "FROM modulo_rol mr "
			+ "INNER JOIN modulos m ON m.cve_modulo=mr.id_modulo "
			+ "INNER JOIN roles r ON r.id = mr.id_rol "
			+ "INNER JOIN usuario_rol ur ON ur.id_rol = r.id "
			+ "WHERE ur.activo=true AND ur.id_usuario = :usuario AND mr.id_modulo = :modulo ", nativeQuery = true)
	Modulo hasAccess (@Param("usuario") Integer idUsuario, @Param("modulo") Integer idModulo);
	
	@Query(value = "SELECT * FROM modulos m "
			+ "WHERE activo='True' AND cve_modulo IN ( "
			+ "SELECT id_modulo FROM preguntas_frecuentes pf "
			+ "WHERE pf.id_modulo=m.cve_modulo) "
			+ "ORDER BY cve_modulo", nativeQuery = true)
	List<Modulo> buscarModulosConPreguntasFrecuentes();
}
package edu.mx.utdelacosta.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Usuario;


public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {
	// Buscar usuario por username
	Usuario findByUsuario(String username);
	
	@Query(value = "SELECT u.id, u.id_persona, u.usuario, u.contrasenia, u.activo, u.fecha_alta "
			+ "FROM usuarios u "
			+ "INNER JOIN personas per ON per.id=u.id_persona "
			+ "WHERE CONCAT(per.nombre,' ',per.primer_apellido) iLIKE %:nombre% "
			+ "OR CONCAT(per.primer_apellido,' ',per.segundo_apellido) iLIKE %:nombre% "
			+ "OR CONCAT(per.segundo_apellido, ' ',per.nombre) iLIKE %:nombre% "
			+ "OR u.usuario iLIKE %:nombre% "
			+ "ORDER BY per.primer_apellido, per.segundo_apellido, per.nombre", nativeQuery = true)
	ArrayList<Usuario> findByPersonOrUsername (@Param("nombre") String nombre);

	@Modifying
	@Query("UPDATE Usuario u SET u.activo='False' WHERE u.id = :paramIdUsuario")
	int lock(@Param("paramIdUsuario") int idUsuario);

	@Modifying
	@Query("UPDATE Usuario u SET u.activo='True' WHERE u.id = :paramIdUsuario")
	int unlock(@Param("paramIdUsuario") int idUsuario);
	
	@Modifying
	@Query("UPDATE Usuario u SET u.contrasenia = :paramContrasenia WHERE u.id = :paramIdUsuario")
	void updatePassword(@Param("paramIdUsuario") int idUsuario, @Param("paramContrasenia") String contrasenia);
	
	Usuario findByPersona(Persona p);
	
	List <Usuario> findTop10ByOrderByIdDesc();
}

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
	
	//alumnos
	@Query(value = "SELECT u.* FROM usuarios u "
			+ "INNER JOIN personas p ON p.id=u.id_persona "
			+ "INNER JOIN alumnos a ON a.id_persona = u.id_persona "
			+ "INNER JOIN alumnos_grupos ag ON ag.id_alumno = a.id AND ag.id_grupo=(SELECT g.id FROM alumnos_grupos ag "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo WHERE ag.id_alumno=a.id ORDER BY id_periodo DESC LIMIT 1) "
			+ "INNER JOIN grupos g ON g.id=ag.id_grupo "
			+ "WHERE a.id_carrera=:idCarrera and g.id_periodo=:idPeriodo ORDER BY a.id_carrera, g.id, p.primer_apellido, p.segundo_apellido, p.nombre, u.usuario", nativeQuery = true)
	List<Usuario> findAllbyPeriodoAndCarreraOrdeByCarreraAndPrimerApellidoAndSegundoApellidoAndNombreAndUsuario(@Param("idCarrera") Integer idCarrera,@Param("idPeriodo") Integer idPeriodo);
	
	//Personal
	@Query(value = "SELECT u.* FROM usuarios u "
			+ "INNER JOIN personas p ON p.id=u.id_persona "
			+ "INNER JOIN usuario_rol ur ON ur.id_usuario = u.id "
			+ "INNER JOIN roles r ON r.id=ur.id_rol "
			+ "INNER JOIN personal pe ON pe.id_persona = u.id_persona "
			+ "ORDER BY r.id, p.primer_apellido, p.segundo_apellido, p.nombre, u.usuario", nativeQuery = true)
	List<Usuario> findAllOrdeByRolAndPrimerApellidoAndSegundoApellidoAndNombreAndUsuario();
	
	//Profesores
	@Query(value = "SELECT u.* FROM usuarios u "
			+ "INNER JOIN personas p ON p.id=u.id_persona "
			+ "INNER JOIN usuario_rol ur ON ur.id_usuario = u.id "
			+ "INNER JOIN roles r ON r.id=ur.id_rol "
			+ "INNER JOIN personal pe ON pe.id_persona = u.id_persona "
			+ "WHERE r.id IN (:idRol) AND u.activo='True' "
			+ "ORDER BY r.id, p.primer_apellido, p.segundo_apellido, p.nombre, u.usuario", nativeQuery = true)
	List<Usuario> buscarUsuariosPorRol(@Param("idRol") Integer idRol);
}

package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.Personal;

public interface PersonalRepository extends JpaRepository<Personal, Integer>{
	Personal findByPersona(Persona persona);
	
	@Query(value = "SELECT per.* FROM personal per "
			+ "INNER JOIN personas p ON p.id=per.id_persona "
			+ "ORDER BY per.activo, id_puesto, p.primer_apellido, p.segundo_apellido, p.nombre", nativeQuery = true)
	List<Personal> findAllByPersonaOrderByPuesto();
	
	@Query(value = "SELECT * FROM personal "
			+ "WHERE jefe='True'", nativeQuery = true)
	List<Personal> findAllBoss();
	
	//para traerse la lista de grupos
		@Query(value = "SELECT * FROM personal p "
				+ "INNER JOIN puestos pu ON p.id_puesto = pu.id "
				+ "INNER JOIN personas per on p.id_persona=per.id "
				+ "WHERE pu.id IN(6, 8) ", nativeQuery = true)
		List<Personal> listaProfesores();
}

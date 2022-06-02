package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.mx.utdelacosta.model.DosificacionComentario;

public interface DosificacionesComentariosRepository extends CrudRepository<DosificacionComentario, Integer> {
	List<DosificacionComentario> findByIdPersona(Integer idPersona);
	List<DosificacionComentario> findByIdCargaHoraria(Integer idCargaHoraria);
}

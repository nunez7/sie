package edu.mx.utdelacosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mx.utdelacosta.model.Documento;
import edu.mx.utdelacosta.model.Persona;
import edu.mx.utdelacosta.model.PersonaDocumento;
import edu.mx.utdelacosta.model.dto.DocumentoDTO;

public interface PersonaDocumentoRepository extends CrudRepository<PersonaDocumento, Integer>{
	PersonaDocumento findByPersonaAndDocumento(Persona persona, Documento documento);
	List<PersonaDocumento> findByPersona(Persona persona);
	//extrae los 3 docuemtos basicos asociados al alumno 1=acta, 2=CURP, 3=Certificado de bachillerato	
	@Query(value = "Select pd.* from persona_documento pd "
			+ "INNER JOIN documentos d ON d.id=pd.id_documento "			
			+ "where pd.id_persona =:idPersona and d.id IN(1,2,3) ORDER BY d.id", nativeQuery = true)
	List<PersonaDocumento> findActaCurpCerbachiByPersona(@Param("idPersona") Integer idPersona);
	
	@Query(value = "SELECT d.id, d.nombre, "
			+ "COALESCE((SELECT entregado FROM persona_documento pd  "			
			+ "WHERE d.id = pd.id_documento AND pd.id_persona=:idPersona ), 'No entregado') "
			+ "AS entregado, "
			+ "COALESCE(( SELECT validado FROM persona_documento pd "
			+ "WHERE d.id = pd.id_documento AND pd.id_persona=:idPersona ), false) "
			+ "AS validado  "
			+ "FROM documentos d "
			+ "WHERE d.id IN(1,2,3)", nativeQuery = true)
	List<DocumentoDTO> findActaCurpCerbachiByPersonaDto(@Param("idPersona") Integer idPersona);
	
	@Query(value="SELECT pd.* FROM persona_documento pd WHERE pd.id_persona =:persona AND pd.id_documento =:idDoc", nativeQuery =true)
	PersonaDocumento findPersonaDocByPersonaAndIdDoc(@Param("persona")Integer idPersona, @Param("idDoc")int idDocumento);
	
	@Query(value="SELECT CAST(COUNT(*)AS INT) FROM persona_documento pd"
			+ " WHERE pd.id_persona =:persona and id_documento IN(1,2,3) and validado =true", nativeQuery=true)
	Integer findPersonaDocValidadoByPersona(@Param("persona")Integer idPersona);
	
	@Query(value="SELECT d.id, d.nombre, pd.entregado, pd.validado FROM persona_documento pd "
			+ "INNER JOIN personas p ON pd.id_persona=p.id "
			+ "INNER JOIN documentos d ON pd.id_documento=d.id "
			+ "WHERE pd.prestado = 'True' AND id_persona = :persona", nativeQuery = true)
	List<DocumentoDTO> findDocumentosPrestadoByPersona(@Param("persona") Integer idPersona);
}

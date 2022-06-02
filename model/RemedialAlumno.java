package edu.mx.utdelacosta.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "remedial_alumno")
public class RemedialAlumno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "id_alumno")
	private Alumno alumno;
	
	@ManyToOne
	@JoinColumn(name = "id_carga_horaria", referencedColumnName = "id")
	private CargaHoraria cargaHoraria;
	
	@OneToOne
	@JoinColumn(name = "tipo_remedial")
	private Remedial remedial;
	
	@OneToOne
	@JoinColumn(name = "tipo_testimonio", referencedColumnName = "id")
	private Testimonio testimonio;
	
	@ManyToOne
	@JoinColumn(name = "id_corte", referencedColumnName = "id")
	private CorteEvaluativo corteEvaluativo;
	
	private boolean pagado; 
	
	public RemedialAlumno() {
	}
	
	public RemedialAlumno(int id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public CargaHoraria getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(CargaHoraria cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public Remedial getRemedial() {
		return remedial;
	}

	public void setRemedial(Remedial remedial) {
		this.remedial = remedial;
	}

	public Testimonio getTestimonio() {
		return testimonio;
	}

	public void setTestimonio(Testimonio testimonio) {
		this.testimonio = testimonio;
	}

	public CorteEvaluativo getCorteEvaluativo() {
		return corteEvaluativo;
	}

	public void setCorteEvaluativo(CorteEvaluativo corteEvaluativo) {
		this.corteEvaluativo = corteEvaluativo;
	}

	public boolean isPagado() {
		return pagado;
	}

	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}
	
	
}

package portaleventos;
import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Evento")
public class Evento {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name = "evento_id")
	private long id;
	
	
	@Column(name = "Data_inicio" , nullable = false)
	private LocalDate dataInicio;
	
	@Column(name = "Data_final" , nullable = false)
	private LocalDate dataFinal;
	
	public Evento() {}
	
	public Evento(LocalDate dataInicio, LocalDate dataFinal) {
		this.dataInicio = dataInicio;
		this.dataFinal = dataFinal;
	}
	
	public long getId() {
		return id;
	}
	
	public LocalDate getDataInicio() {
		return dataInicio;
	}
	
	public LocalDate getDataFinal() {
		return dataFinal;
	}
	
	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	@Override
    public String toString() {
        return "Evento{id=" + id + ", inicio=" + dataInicio + ", fim=" + dataFinal + '}';
    }
	
}

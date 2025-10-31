package portaleventos;

//Esta classe descreve uma inscricao a um evento, depois de aceite o pedido


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity

//@Table(uniqueConstraints = @UniqueConstraint define uma restricao unica na base de dados

//Diz que nao pode existir mais de um registro com a mesma combinacao de participante_id" e "evento_id" ou seja
//um mesmo participante nao pode inscrever-se duas vezes no mesmo evento.
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"participante_id", "evento_id"}))

public class Inscrever 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private LocalDateTime dataRegistro = LocalDateTime.now();
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private EstadoInscricao estado = EstadoInscricao.ATIVA;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "participante_id")
	private Participante participante;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "evento_id")
	private Evento evento;
	
    public Inscrever()
    {
    	
    }

	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}

	public LocalDateTime getDataRegistro() 
	{
		return dataRegistro;
	}

	public void setDataRegistro(LocalDateTime dataRegistro) 
	{
		this.dataRegistro = dataRegistro;
	}

	public EstadoInscricao getEstado() 
	{
		return estado;
	}

	public void setEstado(EstadoInscricao estado) 
	{
		this.estado = estado;
	}

	public Participante getParticipante() 
	{
		return participante;
	}

	public void setParticipante(Participante participante) 
	{
		this.participante = participante;
	}

	public Evento getEvento() 
	{
		return evento;
	}

	public void setEvento(Evento evento) 
	{
		this.evento = evento;
	}

}

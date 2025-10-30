package portaleventos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.*;
import portaleventos.Enum.Departamento;
import portaleventos.Enum.TipoEvento;

/**
 * Esta classe representa um evento e é a superclasse da hierarquia
 */

@Entity
@Table(name = "eventos") // a single table for all the hierarchy
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn (name = "tipo_evento", discriminatorType =
DiscriminatorType.STRING, length = 20)

public class Evento {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="evento_id")
	private long id;
	
	private String tituloEvento;
	private String descricao;
	
	private LocalDate data;
	private LocalTime dataHorarioInicio;
	private LocalTime dataHorarioFim;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoEvento TipoEvento;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Departamento Departamento;

	private float Orcamento;
	
	private String local;
	private int capacidade;
	private float precoEntrada;
	
	private String nomePromotores;
	
	// Dados do Organizador
	private String nomeCompletoOrganizador;
	private String emailOrganizador;
	private int contactoOrganizador;
	
	public Evento() {}
	
	@ManyToOne
	@JoinColumn(name = "participante_id")
	private Participante participante;
	
	@ManyToOne
	@JoinColumn(name = "promotor_id")
	private Promotor promotor;

	public Evento(String tituloEvento, String descricao, LocalDate data, LocalTime horaInicio,
			LocalTime horaFim, portaleventos.Enum.TipoEvento tipoEvento,
			portaleventos.Enum.Departamento departamento, float orcamento, String local, int capacidade,
			float precoEntrada, String nomePromotores, String nomeCompletoOrganizador, String emailOrganizador,
			int contactoOrganizador) {
		this.tituloEvento = tituloEvento;
		this.descricao = descricao;
		this.data = data;
		dataHorarioInicio = horaInicio;
		dataHorarioFim = horaFim;
		TipoEvento = tipoEvento;
		Departamento = departamento;
		Orcamento = orcamento;
		this.local = local;
		this.capacidade = capacidade;
		this.precoEntrada = precoEntrada;
		this.nomePromotores = nomePromotores;
		this.nomeCompletoOrganizador = nomeCompletoOrganizador;
		this.emailOrganizador = emailOrganizador;
		this.contactoOrganizador = contactoOrganizador;
	}



	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}



	public String getTituloEvento() {
		return tituloEvento;
	}

	public void setTituloEvento(String tituloEvento) {
		this.tituloEvento = tituloEvento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalTime getDataHorarioInicio() {
		return dataHorarioInicio;
	}

	public void setDataHorarioInicio(LocalTime dataHorarioInicio) {
		this.dataHorarioInicio = dataHorarioInicio;
	}

	public LocalTime getDataHorarioFim() {
		return dataHorarioFim;
	}

	public void setDataHorarioFim(LocalTime dataHorarioFim) {
		this.dataHorarioFim = dataHorarioFim;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public int getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}

	public float getPrecoEntrada() {
		return precoEntrada;
	}

	public void setPrecoEntrada(float precoEntrada) {
		this.precoEntrada = precoEntrada;
	}

	public String getNomeCompletoOrganizador() {
		return nomeCompletoOrganizador;
	}

	public void setNomeCompletoOrganizador(String nomeCompletoOrganizador) {
		nomeCompletoOrganizador = nomeCompletoOrganizador;
	}

	public String getEmailOrganizador() {
		return emailOrganizador;
	}

	public void setEmailOrganizador(String emailOrganizador) {
		emailOrganizador = emailOrganizador;
	}

	public int getContactoOrganizador() {
		return contactoOrganizador;
	}

	public void setContactoTelefonicoOrganizador(int contactoOrganizador) {
		contactoOrganizador = contactoOrganizador;
	}



	public void setPromotor(Promotor promotor2) {
	}
	
	
	
	
	
}

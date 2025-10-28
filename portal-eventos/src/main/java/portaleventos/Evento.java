package portaleventos;

import java.time.LocalDateTime;

import jakarta.persistence.*;

/**
 * Esta classe representa um evento e é a superclasse da hierarquia
 */

@Entity
@Table(name = "eventos") // a single table for all the hierarchy
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn (name = "tipo_evento", discriminatorType =
DiscriminatorType.STRING, length = 20)

public class Evento {

	private String tituloEvento;
	private String descricao;
	
	private LocalDateTime dataHorarioInicio;
	private LocalDateTime dataHorarioFim;
	
	private String local;
	private int capacidade;
	private float precoEntrada;
	
	// Dados do Organizador
	private String nomeCompletoOrganizador;
	private String emailOrganizador;
	private int contactoOrganizador;
	
	public Evento() {}
	
	

	public Evento(String tituloEvento, String descricao, LocalDateTime dataHorarioInicio, LocalDateTime dataHorarioFim,
			String local, int capacidade, float precoEntrada, String nomeCompletoOrganizador, String emailOrganizador,
			int contactoOrganizador) {
		this.tituloEvento = tituloEvento;
		this.descricao = descricao;
		this.dataHorarioInicio = dataHorarioInicio;
		this.dataHorarioFim = dataHorarioFim;
		this.local = local;
		this.capacidade = capacidade;
		this.precoEntrada = precoEntrada;
		this.nomeCompletoOrganizador = nomeCompletoOrganizador;
		this.emailOrganizador = emailOrganizador;
		this.contactoOrganizador = contactoOrganizador;
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

	public LocalDateTime getDataHorarioInicio() {
		return dataHorarioInicio;
	}

	public void setDataHorarioInicio(LocalDateTime dataHorarioInicio) {
		this.dataHorarioInicio = dataHorarioInicio;
	}

	public LocalDateTime getDataHorarioFim() {
		return dataHorarioFim;
	}

	public void setDataHorarioFim(LocalDateTime dataHorarioFim) {
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
	
	
	
	
	
}

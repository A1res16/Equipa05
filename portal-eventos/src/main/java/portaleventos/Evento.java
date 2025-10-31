package portaleventos;


//Esta classe descreve um evento, depois de aceite o pedido

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

@Entity

public class Evento 
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   
   @Column(nullable = false, length = 120)
   private String titulo;
   
   @Column(nullable = false, length = 1000)
   private String descricao;
   
   @Column(nullable = false)
   private LocalDateTime dataHoraInicio;
   
   @Column(nullable = false)
   private LocalDateTime dataHoraFim;
   
   @Column(nullable = false)
   private Integer capacidade;
   
   @Column(nullable=false) 
   private BigDecimal precoEntrada;
   
   @Enumerated(EnumType.STRING)
   @Column(nullable = false, length = 15)
   private EventoEstado estado = EventoEstado.CONFIRMADO;
   
   @Column(nullable = false)
   private boolean necessitaInscricao = true;
   
   //Quem criou/publicou
   @ManyToOne(optional = false)
   @JoinColumn(name = "promotor_id")
   private Promotor promotor;
   
   //Tipo de evento
   @ManyToOne(optional = false)
   @JoinColumn(name = "tipo_id")
   private TipoEvento tipo;
   
   //Lista de inscricoes dos (participantes)
   @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Inscrever> inscricoes = new ArrayList<>();

// Opcionalmente, repetir coleções por tipo (se precisares já nesta fase):
  @ManyToMany @JoinTable(name="evento_palestra_orador",
     joinColumns=@JoinColumn(name="evento_id"),
     inverseJoinColumns=@JoinColumn(name="orador_id"))
  private List<Orador> oradores = new ArrayList<>();

  @ManyToMany @JoinTable(name="evento_festa_patrocinador",
     joinColumns=@JoinColumn(name="evento_id"),
     inverseJoinColumns=@JoinColumn(name="patrocinador_id"))
  private List<Patrocinador> patrocinadores = new ArrayList<>();

  @ManyToMany @JoinTable(name="evento_workshop_instrutor",
     joinColumns=@JoinColumn(name="evento_id"),
     inverseJoinColumns=@JoinColumn(name="orador_id"))
  private List<Orador> instrutores = new ArrayList<>();

  @ElementCollection
  @CollectionTable(name="evento_exposicao_artistas", joinColumns=@JoinColumn(name="evento_id"))
  @Column(name="artista", length=120)
  private List<String> artistas = new ArrayList<>();

  @ElementCollection
  @CollectionTable(name="evento_exposicao_obras", joinColumns=@JoinColumn(name="evento_id"))
  @Column(name="obra", length=200)
  private List<String> obras = new ArrayList<>();

   public Evento() 
   {
	   
   }
   
   //Adicionar participantes, este metodo serve apenas para atualizar as duas listas(evento e participante)
   //de forma sincronizada, quando quisermos inscrever alguém diretamente aqui, é opcional, só nos ajuda a manter o relacionamento bidirecional
   public void addParticipantes(Participante p)
   {
	   Inscrever i = new Inscrever();
	   i.setParticipante(p);
	   i.setEvento(this);
	   inscricoes.add(i);
	   
	   //para sincronizar as listas
	   p.getInscricoes().add(i);
   }

   public Long getId() 
   {
	   return id;
   }

   public void setId(Long id) 
   {
	   this.id = id;
   }

   public String getTitulo() 
   {
	   return titulo;
   }

   public void setTitulo(String titulo) 
   {
	   this.titulo = titulo;
   }

   public String getDescricao() 
   {
	   return descricao;
   }

   public void setDescricao(String descricao) 
   {
	   this.descricao = descricao;
   }

   public LocalDateTime getDataHoraInicio() 
   {
	   return dataHoraInicio;
   }

   public void setDataHoraInicio(LocalDateTime dataHoraInicio) 
   {
	   this.dataHoraInicio = dataHoraInicio;
   }

   public LocalDateTime getDataHoraFim() 
   {
	   return dataHoraFim;
   }

   public void setDataHoraFim(LocalDateTime dataHoraFim) 
   {
	   this.dataHoraFim = dataHoraFim;
   }

   public Integer getCapacidade() 
   {
	   return capacidade;
   }

   public void setCapacidade(Integer capacidade) 
   {
	   this.capacidade = capacidade;
   }

   public EventoEstado getEstado() 
   {
	   return estado;
   }

   public void setEstado(EventoEstado estado) 
   {
	   this.estado = estado;
   }

   public boolean isNecessitaInscricao() 
   {
	   return necessitaInscricao;
   }

   public void setNecessitaInscricao(boolean necessitaInscricao) 
   {
	   this.necessitaInscricao = necessitaInscricao;
   }

   public Promotor getPromotor() 
   {
	   return promotor;
   }

   public void setPromotor(Promotor promotor) 
   {
	   this.promotor = promotor;
   }

   public TipoEvento getTipo() 
   {
	   return tipo;
   }

   public void setTipo(TipoEvento tipo) 
   {
	   this.tipo = tipo;
   }

   public List<Inscrever> getInscricoes() 
   {
	   return inscricoes;
   }

   public void setInscricoes(List<Inscrever> inscricoes) 
   {
	   this.inscricoes = inscricoes;
   }

   public BigDecimal getPrecoEntrada() 
   {
	   return precoEntrada;
   }

   public void setPrecoEntrada(BigDecimal precoEntrada) 
   {
	   this.precoEntrada = precoEntrada;
   }

   public List<Orador> getOradores() 
   {
	   return oradores;
   }

   public void setOradores(List<Orador> oradores) 
   {
	   this.oradores = oradores;
   }

   public List<Patrocinador> getPatrocinadores() 
   {
	   return patrocinadores;
   }

   public void setPatrocinadores(List<Patrocinador> patrocinadores) 
   {
	   this.patrocinadores = patrocinadores;
   }

   public List<Orador> getInstrutores() 
   {
	   return instrutores;
   }

   public void setInstrutores(List<Orador> instrutores) 
   {
	   this.instrutores = instrutores;
   }

   public List<String> getArtistas() 
   {
	   return artistas;
   }

   public void setArtistas(List<String> artistas) 
   {
	   this.artistas = artistas;
   }

   public List<String> getObras() 
   {
	   return obras;
   }

   public void setObras(List<String> obras) 
   {
	   this.obras = obras;
   }
      
}

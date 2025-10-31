package portaleventos;



//Esta classe descreve um formulario 
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FormularioEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Dados finais do evento
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

    @Column(nullable = false)
    private BigDecimal precoEntrada;

    // Contato do organizador
    @Column(nullable = false, length = 120)
    private String nomeOrganizador;

    @Column(nullable = false, length = 120)
    private String emailOrganizador;

    @Column(length = 30)
    private String telefoneOrganizador;

    // Ligações
    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_id")
    private TipoEvento tipo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "promotor_id")
    private Promotor promotor;

    @OneToOne(optional = false)
    @JoinColumn(name = "pedido_id", unique = true)
    private Pedido pedido;

    // Campos opcionais
    @Column(length = 30)
    private String idioma;

    @Column(length = 255)
    private String linkInscricao;

    // Palestra
    // ligacao varios oradores estao ligados a varias palestras
    @ManyToMany
    @JoinTable(name = "form_palestra_orador", // terceira coluna que junta orador com palestra com form_id e orador_id
            joinColumns = @JoinColumn(name = "form_id"),
            inverseJoinColumns = @JoinColumn(name = "orador_id"))
    private List<Orador> oradores = new ArrayList<>();

    // Festa
    @ManyToMany
    @JoinTable(name = "form_festa_patrocinador",
            joinColumns = @JoinColumn(name = "form_id"),
            inverseJoinColumns = @JoinColumn(name = "patrocinador_id"))
    private List<Patrocinador> patrocinadores = new ArrayList<>();

    // Workshop
    @ManyToMany
    @JoinTable(name = "form_workshop_instrutor",
            joinColumns = @JoinColumn(name = "form_id"),
            inverseJoinColumns = @JoinColumn(name = "orador_id"))
    private List<Orador> instrutores = new ArrayList<>();

    @Column(length = 400)
    private String requisitosMateriais;

    @Column(length = 400)
    private String materiaisFornecidos;

    private Integer vagasPraticas;
    private Integer cargaHorariaHoras;
    private Boolean inscricaoPrevia;

    // Exposição
    //@ElementCollection diz ao hibernate que a lista de artista é uma lista simples, nao é uma relacao com outras entidades mas é uma lista de valores simples
    //o hibernate cria uma tabela separada para armazenar so esses valores
    @ElementCollection // cada formulario pode ter varios artistas, mas queremos guardar essa lista de nomes no BD sem criar uma classe artista
    @CollectionTable(name = "form_exposicao_artistas", joinColumns = @JoinColumn(name = "form_id"))//@JoinColumn diz o nome da tabela auxiliar onde a lista será guardada
    @Column(name = "artista", length = 120)
    private List<String> artistas = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "form_exposicao_obras", joinColumns = @JoinColumn(name = "form_id")) // define a chave estrangeira que liga a tabela separada com o formulario principal
    @Column(name = "obra", length = 200)
    private List<String> obras = new ArrayList<>();

    public FormularioEvento() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDateTime getDataHoraInicio() { return dataHoraInicio; }
    public void setDataHoraInicio(LocalDateTime dataHoraInicio) { this.dataHoraInicio = dataHoraInicio; }

    public LocalDateTime getDataHoraFim() { return dataHoraFim; }
    public void setDataHoraFim(LocalDateTime dataHoraFim) { this.dataHoraFim = dataHoraFim; }

    public Integer getCapacidade() { return capacidade; }
    public void setCapacidade(Integer capacidade) { this.capacidade = capacidade; }

    public BigDecimal getPrecoEntrada() { return precoEntrada; }
    public void setPrecoEntrada(BigDecimal precoEntrada) { this.precoEntrada = precoEntrada; }

    public String getNomeOrganizador() { return nomeOrganizador; }
    public void setNomeOrganizador(String nomeOrganizador) { this.nomeOrganizador = nomeOrganizador; }

    public String getEmailOrganizador() { return emailOrganizador; }
    public void setEmailOrganizador(String emailOrganizador) { this.emailOrganizador = emailOrganizador; }

    public String getTelefoneOrganizador() { return telefoneOrganizador; }
    public void setTelefoneOrganizador(String telefoneOrganizador) { this.telefoneOrganizador = telefoneOrganizador; }

    public TipoEvento getTipo() { return tipo; }
    public void setTipo(TipoEvento tipo) { this.tipo = tipo; }

    public Promotor getPromotor() { return promotor; }
    public void setPromotor(Promotor promotor) { this.promotor = promotor; }

    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    public String getLinkInscricao() { return linkInscricao; }
    public void setLinkInscricao(String linkInscricao) { this.linkInscricao = linkInscricao; }

    public List<Orador> getOradores() { return oradores; }
    public void setOradores(List<Orador> oradores) { this.oradores = oradores; }

    public List<Patrocinador> getPatrocinadores() { return patrocinadores; }
    public void setPatrocinadores(List<Patrocinador> patrocinadores) { this.patrocinadores = patrocinadores; }

    public List<Orador> getInstrutores() { return instrutores; }
    public void setInstrutores(List<Orador> instrutores) { this.instrutores = instrutores; }

    public String getRequisitosMateriais() { return requisitosMateriais; }
    public void setRequisitosMateriais(String requisitosMateriais) { this.requisitosMateriais = requisitosMateriais; }

    public String getMateriaisFornecidos() { return materiaisFornecidos; }
    public void setMateriaisFornecidos(String materiaisFornecidos) { this.materiaisFornecidos = materiaisFornecidos; }

    public Integer getVagasPraticas() { return vagasPraticas; }
    public void setVagasPraticas(Integer vagasPraticas) { this.vagasPraticas = vagasPraticas; }

    public Integer getCargaHorariaHoras() { return cargaHorariaHoras; }
    public void setCargaHorariaHoras(Integer cargaHorariaHoras) { this.cargaHorariaHoras = cargaHorariaHoras; }

    public Boolean getInscricaoPrevia() { return inscricaoPrevia; }
    public void setInscricaoPrevia(Boolean inscricaoPrevia) { this.inscricaoPrevia = inscricaoPrevia; }

    public List<String> getArtistas() { return artistas; }
    public void setArtistas(List<String> artistas) { this.artistas = artistas; }

    public List<String> getObras() { return obras; }
    public void setObras(List<String> obras) { this.obras = obras; }

    
}

package portaleventos;

//Esta classe descreve um pedido de evento, para ser avaliado

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================
    // Atributos principais
    // ==========================
    @Column(nullable = false, length = 120)
    private String titulo;

    @Column(nullable = false, length = 400)
    private String descricaoBreve;

    @Column(nullable = false)
    private LocalDate dataDia;  // Data pretendida para o evento

    @Column(nullable = false, length = 100)
    private String localProposto;

    @Column(nullable = true)
    private Integer capacidadeSolicitada;

    @Column(nullable = true, precision = 10, scale = 2)
    private BigDecimal orcamento;

    //@Enumerated(EnumType.STRING) diz como o tipo enum deve ser persistido na BD
    //Quando usamos um enum numa classe entidade, podemos escolher se um enum pode ser armazenado como uma string ou valor ordinario
    //Sera armazenado como nome de uma constante enum (EnumType.STRING)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PedidoEstado estado = PedidoEstado.PENDENTE;

    @Column(nullable = false)
    private LocalDateTime dataSubmissao = LocalDateTime.now();

    @Column(length = 400)
    private String justificativa; // Motivo de rejeição ou devolução

    // ==========================
    // Relações com outras classes
    // ==========================

    // Promotor que criou o pedido
    @ManyToOne(optional = false) // um promotor pode ter varios pedidos. cada pedido esta ligado a um só promotor 
    @JoinColumn(name = "promotor_id") //@JoinColumn diz que a chave estrangeira p_id na coluna pedido liga o pedido ao promotor correspondente
    private Promotor promotor;

    // Departamento responsável
    @ManyToOne(optional = false)
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    // Área dentro do departamento
    @ManyToOne(optional = false)
    @JoinColumn(name = "area_id")
    private Area area;

    // Tipo do evento (Festa, Palestra, Workshop, Exposição)
    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_id")
    private TipoEvento tipo;

    // Formulário de evento (gerado após aprovação)
    //um formulario esta ligado a um pedido
    
    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private FormularioEvento formulario;

    // ==========================
    // Construtores
    // ==========================
    public Pedido() {}

    public Pedido(String titulo, String descricaoBreve, LocalDate dataDia, String localProposto,
                  Integer capacidadeSolicitada, BigDecimal orcamento, Promotor promotor,
                  Departamento departamento, Area area, TipoEvento tipo) 
    {
        this.titulo = titulo;
        this.descricaoBreve = descricaoBreve;
        this.dataDia = dataDia;
        this.localProposto = localProposto;
        this.capacidadeSolicitada = capacidadeSolicitada;
        this.orcamento = orcamento;
        this.promotor = promotor;
        this.departamento = departamento;
        this.area = area;
        this.tipo = tipo;
    }

    // ==========================
    // Getters e Setters
    // ==========================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricaoBreve() { return descricaoBreve; }
    public void setDescricaoBreve(String descricaoBreve) { this.descricaoBreve = descricaoBreve; }

    public LocalDate getDataDia() { return dataDia; }
    public void setDataDia(LocalDate dataDia) { this.dataDia = dataDia; }

    public String getLocalProposto() { return localProposto; }
    public void setLocalProposto(String localProposto) { this.localProposto = localProposto; }

    public Integer getCapacidadeSolicitada() { return capacidadeSolicitada; }
    public void setCapacidadeSolicitada(Integer capacidadeSolicitada) { this.capacidadeSolicitada = capacidadeSolicitada; }

    public BigDecimal getOrcamento() { return orcamento; }
    public void setOrcamento(BigDecimal orcamento) { this.orcamento = orcamento; }

    public PedidoEstado getEstado() { return estado; }
    public void setEstado(PedidoEstado estado) { this.estado = estado; }

    public LocalDateTime getDataSubmissao() { return dataSubmissao; }
    public void setDataSubmissao(LocalDateTime dataSubmissao) { this.dataSubmissao = dataSubmissao; }

    public String getJustificativa() { return justificativa; }
    public void setJustificativa(String justificativa) { this.justificativa = justificativa; }

    public Promotor getPromotor() { return promotor; }
    public void setPromotor(Promotor promotor) { this.promotor = promotor; }

    public Departamento getDepartamento() { return departamento; }
    public void setDepartamento(Departamento departamento) { this.departamento = departamento; }

    public Area getArea() { return area; }
    public void setArea(Area area) { this.area = area; }

    public TipoEvento getTipo() { return tipo; }
    public void setTipo(TipoEvento tipo) { this.tipo = tipo; }

    public FormularioEvento getFormulario() { return formulario; }
    public void setFormulario(FormularioEvento formulario) { this.formulario = formulario; }

    
}

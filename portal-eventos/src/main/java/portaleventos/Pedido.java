package portaleventos;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pedidos")
public class Pedido
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tituloEvento;

    @Column(nullable = false)
    private String nomePromotores;

    @Column(nullable = false)
    private LocalDate data;

    //Duração em MINUTOS  (IMPLEMENTAR NAS USER STORIES)
    @Column(nullable = false)
    private int duracao;

    @Column(nullable = false)
    private String local;

    @Column(nullable = false)
    private String descricaoBreve;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Enum TipoEvento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Enum Departamento;

    //Orçamento em euros;
    @Column(nullable = false)
    private int orcamento;

    @Column(nullable = false)
    private int capacidade;

    @Column(nullable = false)
    private Enum Estado;

    @ManyToOne
    @JoinTable(
            name = "promotor_pedido",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "promotor_id")
    )
    private Set<Promotor> promotores = new HashSet<>();

    public Pedido() {
    }

    public Pedido(String tituloEvento, String nomePromotores, LocalDate data, int duracao, String local, String descricaoBreve, Enum TipoEvento, Enum Departamento, int orcamento, int capacidade, Enum Estado)
    {
        this.tituloEvento = tituloEvento;
        this.nomePromotores = nomePromotores;
        this.data = data;
        this.duracao = duracao;
        this.local = local;
        this.descricaoBreve = descricaoBreve;
        this.TipoEvento = TipoEvento;
        this.Departamento = Departamento;
        this.orcamento = orcamento;
        this.capacidade = capacidade;
        this.Estado = Estado;
    }


    public Enum getEstado()
    {
        return Estado;
    }

    public void setEstado(Enum estado)
    {
        this.Estado = estado;
    }
}

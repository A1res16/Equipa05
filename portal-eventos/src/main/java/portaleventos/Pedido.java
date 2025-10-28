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
    private String tituloEvento;
    private String nomePromotores;
    private LocalDate data;
    //Duração em MINUTOS  (IMPLEMENTAR NAS USER STORIES)
    private int duracao;
    private String local;
    private String descricaoBreve;
    private Enum TipoEvento;
    private Enum Departamento;
    //Orçamento em euros;
    private int orcamento;
    private int capacidade;
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
}

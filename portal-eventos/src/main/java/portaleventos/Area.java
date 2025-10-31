package portaleventos;


//Esta classe descreve uma area
import jakarta.persistence.*;

@Entity
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @ManyToOne(optional = false)
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    public Area() {}

    public Area(String nome, Departamento departamento) {
        this.nome = nome;
        this.departamento = departamento;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Departamento getDepartamento() { return departamento; }
    public void setDepartamento(Departamento departamento) { this.departamento = departamento; }

   
}

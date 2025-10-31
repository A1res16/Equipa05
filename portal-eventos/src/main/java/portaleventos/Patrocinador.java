package portaleventos;


//Esta classe descreve um patrocinador 
import jakarta.persistence.*;


@Entity
public class Patrocinador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(length = 120)
    private String contacto;

    public Patrocinador() {}

    public Patrocinador(String nome, String contacto) {
        this.nome = nome;
        this.contacto = contacto;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }

    
}

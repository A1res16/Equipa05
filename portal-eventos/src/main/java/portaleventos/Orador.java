package portaleventos;


//Esta classe descreve um orador 
import jakarta.persistence.*;


@Entity
public class Orador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(length = 1000)
    private String bio;

    @Column(length = 120)
    private String contacto;

    public Orador() {}

    public Orador(String nome, String bio, String contacto) {
        this.nome = nome;
        this.bio = bio;
        this.contacto = contacto;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }

   
}

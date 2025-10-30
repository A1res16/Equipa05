package portaleventos;

import Enum.Tipo;
import jakarta.persistence.*;
import Enum.Perfil;


@Entity
@Table (name ="person")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "person_type",
discriminatorType = DiscriminatorType.STRING, length = 20)
public class Person {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="person_id")
	private long id;
	
	@Column(nullable=false,unique=true,length=50)
	private String username;
	
	@Column(nullable=false,length=25)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private Perfil perfil;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private Tipo tipo;

	/**
	 * 
	 */
	public Person() {
	}

	/**
	 * @param username
	 * @param password
	 * @param tipo
	 */
	public Person(String username, String password,Tipo tipo,Perfil perfil) {
		this.username = username;
		this.password = password;
		this.tipo=tipo;
		this.perfil=perfil;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the tipo
	 */
	public Tipo getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the perfil
	 */
	public Perfil getPerfil() {
		return perfil;
	}

	/**
	 * @param perfil the perfil to set
	 */
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
	
}

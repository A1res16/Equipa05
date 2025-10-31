package portaleventos;

//Esta classe será a classe base para os perfis, contém heranca(single_table)

import jakarta.persistence.*;


@Entity //usamos entity ou @table antes da classe para mapear ela para uma tabela
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //para todas as entidades na hierarquia serão guardadas numa única tabela
@DiscriminatorColumn (name = "perfil", length = 20) //usado para distinguir entre diferentes tipos de entidades na única tabela com herança

public class Pessoa 
{
	//usamos as anotacoes para mapear a classe em tabela na Base de dados
	//as anotacoes @id e @generate... devem aparecer acima dos atributos ou metodos getts que definem a entidade da chave primaria 
   @Id // coluna id na tabela na BD
   @GeneratedValue(strategy = GenerationType.IDENTITY) //para dizer a estrategia para a geracao da coluna ID, ela é auto-incrementada
   private Long id;
   
   @Column(nullable = false, length = 120) //usado o nullabe column porque quando usamos uma single table, a tabela tem de ter 
   //varias colunas nulas para acomodar campos de diferentes subclasses
   private String nome;
   
   @Column(nullable = false, unique = true, length = 40)
   private String username;
   
   @Column(nullable = false, length = 255)
   private String passwordHash;
   
   @Column(length = 120)
   private String email;
   
   @Column(nullable = false)
   private boolean ativo = true;
   
   
   // Construtor vazio da classe pessoa
   public Pessoa()
   {
	   
	   
   }

   public Long getId() 
   {
	   return id;
   }

   public void setId(Long id) 
   {
	   this.id = id;
   }

   public String getNome() 
   {
	   return nome;
   }

   public void setNome(String nome) 
   {
	   this.nome = nome;
   }


   public String getUsername() 
   {
	   return username;
   }

   public void setUsername(String username) 
   {
	   this.username = username;
   }

   public String getPasswordHash() 
   {
	   return passwordHash;
   }

   public void setPasswordHash(String passwordHash) 
   {
	   this.passwordHash = passwordHash;
   }

   public String getEmail() 
   {
	   return email;
   }

   public void setEmail(String email) 
   {
	   this.email = email;
   }

   public boolean isAtivo() 
   {
	   return ativo;
   }

   public void setAtivo(boolean ativo) 
   {
	   this.ativo = ativo;
   }
   
}

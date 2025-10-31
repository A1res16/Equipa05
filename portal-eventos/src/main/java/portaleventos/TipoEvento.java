package portaleventos;

//Esta classe descreve um tipo de evento, da lista de eventos

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "TipoEvento")
@Table(name = "tipo_evento") // forçar o nome da tabela

public class TipoEvento 
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   
   @Column(nullable = false, unique = true, length = 80)
   private String descricao; //ex: Festa, Palestra, Workshop, Exposicao
   
   @OneToMany(mappedBy = "tipo")
   private List<Evento> eventos = new ArrayList<>();

   public TipoEvento() 
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

   public String getDescricao() 
   {
	   return descricao;
   }

   public void setDescricao(String descricao) 
   {
	   this.descricao = descricao;
   }

   public List<Evento> getEventos() 
   {
	   return eventos;
   }

   public void setEventos(List<Evento> eventos) 
   {
	   this.eventos = eventos;
   }
   
}

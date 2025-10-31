package portaleventos;

//Esta classe descreve um promotor, extende a classe pessoa, herdando atributos e metodos

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@DiscriminatorValue("PROMOTOR")

public class Promotor extends Pessoa
{
   @OneToMany(mappedBy = "promotor")
   private List<Pedido> pedidos = new ArrayList<>();
   
   @OneToMany(mappedBy = "promotor")
   private List<Evento> eventosCriados = new ArrayList<>();
   
   public Promotor()
   {
	   
   }

   public List<Pedido> getPedidos() 
   {
	   return pedidos;
   }

   public List<Evento> getEventosCriados() 
   {
	   return eventosCriados;
   }
}

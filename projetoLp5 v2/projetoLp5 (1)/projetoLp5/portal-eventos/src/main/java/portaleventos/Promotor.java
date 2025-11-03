package portaleventos;

/**@author Equipa5
 * @version 02 + comentarios
 */
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;


@Entity
@DiscriminatorValue ("Promotor")
public class Promotor extends Person{
	
	@OneToMany(mappedBy="promotor", cascade = CascadeType.ALL,orphanRemoval=true)
	private List<Pedido>pedidos=new ArrayList<Pedido>();
	
	@OneToMany(mappedBy="promotor", cascade = CascadeType.ALL,orphanRemoval=true)
	private List<Evento>eventos=new ArrayList<Evento>();

	
	 public void verPedidos() {
	        if (pedidos == null || pedidos.isEmpty()) {
	            System.out.println("Este promotor não tem pedidos registados.");
	        } else {
	            System.out.println("Pedidos do promotor:");
	            for (Pedido p : pedidos) {
	                System.out.println(p);
	            }
	        }
	    }
	 
	 public List<Pedido> getPedidos() {
		    return pedidos;
		}
	 
}

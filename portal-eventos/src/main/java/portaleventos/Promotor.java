package portaleventos;

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
	
}

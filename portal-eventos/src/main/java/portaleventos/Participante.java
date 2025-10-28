package portaleventos;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;


@Entity
@DiscriminatorValue ("Participante")
public class Participante extends Person{
	
	@OneToMany(mappedBy="participante", cascade = CascadeType.ALL,orphanRemoval=true)
	private List<Evento>eventos=new ArrayList<Evento>();
	
}

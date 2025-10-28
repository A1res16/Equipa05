package portaleventos;
import jakarta.persistence.*;


@Entity
@Table(name = "Festa")
public class Festa  extends Evento{
		
	
	@Column(name = "patrocinadores" , nullable = false, length =100)
		private String patrocinadores;
	
	@Column(name = "link" ,  length = 255)
	private String link;
	
	

public Festa(String patrocinadores, String link) {
	this.patrocinadores = patrocinadores;
	this.link = link;
}


public String getPatrocinadores() {
	return patrocinadores;
}

public String getLink() {
	return link;
}

public void setPatrocinadores(String patrocinadores) {
	this.patrocinadores = patrocinadores;
}

public void setLink(String link) {
	this.link = link;
}

@Override
public String toString() {
    return "Festa{" +
           "id=" + getId() +
           ", dataInicio=" + getDataInicio() +
           ", dataFinal=" + getDataFinal() +
           ", patrocinadores='" + patrocinadores + '\'' +
           ", link='" + link + '\'' +
           '}';
}

}
	


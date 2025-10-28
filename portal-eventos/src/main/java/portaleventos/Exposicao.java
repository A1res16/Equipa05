package portaleventos;
import java.time.LocalDate;

import jakarta.persistence.*;


@Entity
@Table(name = "Exposicao")
public class Exposicao extends Evento {
	
	@Column(name = "patrocinadores" ,  nullable = false , length = 200)
	private String patrocinadores;
	
	
	@Column(name = "link" ,  nullable = false , length = 200)
	private String link;
	
	public Exposicao(LocalDate dataInicio , LocalDate dataFinal, String patrocinadores, String link) {
		super(dataInicio, dataFinal);
		this.patrocinadores = patrocinadores;
		this.link = link;
		
	}
	
	
	public String getPatrocinadores() {
        return patrocinadores;
    }
	

    public void setPatrocinadores(String patrocinadores) {
        this.patrocinadores = patrocinadores;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Exposicao{" +
               "id=" + getId() +
               ", dataInicio=" + getDataInicio() +
               ", dataFinal=" + getDataFinal() +
               ", patrocinadores='" + patrocinadores + '\'' +
               ", link='" + link + '\'' +
               '}';
    }
}

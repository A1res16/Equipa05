package portaleventos;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "Palestra")
public class Palestra extends Evento {
	
	@Column(name = "Oradores" , nullable = false, length = 200)
	private String oradores;
	
	@Column(name = "Patrocinadores" , nullable = false, length = 200)
	private String patrocinadores;
	
	@Column(name = "idioma" , nullable = false, length = 200)
	private String idioma;
	
	public Palestra (LocalDate dataInicio, LocalDate dataFinal, String oradores, String patrocinadores, String idioma) {
		super(dataInicio, dataFinal);
		this.oradores = oradores;
		this.patrocinadores = patrocinadores;
		this.idioma = idioma;
	}

	public String getOradores() {
        return oradores;
    }
	

    public void setOradores(String oradores) {
        this.oradores = oradores;
    }

    public String getPatrocinadores() {
        return patrocinadores;
    }

    public void setPatrocinadores(String patrocinadores) {
        this.patrocinadores = patrocinadores;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    @Override
    public String toString() {
        return "Palestra{" +
               "id=" + getId() +
               ", dataInicio=" + getDataInicio() +
               ", dataFinal=" + getDataFinal() +
               ", oradores='" + oradores + '\'' +
               ", patrocinadores='" + patrocinadores + '\'' +
               ", idioma='" + idioma + '\'' +
               '}';
    }
	
	
}

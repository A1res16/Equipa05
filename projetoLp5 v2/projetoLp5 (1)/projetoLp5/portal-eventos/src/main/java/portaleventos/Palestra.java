package portaleventos;

/**@author Equipa5
 * @version 02 + comentarios
 */
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "Palestra")
@DiscriminatorValue ("Palestra")
public class Palestra extends Evento {

    @Column(name = "Oradores" , nullable = false, length = 200)
    private String oradores;

    @Column(name = "Patrocinadores" , nullable = false, length = 200)
    private String patrocinadores;

    @Column(name = "idioma" , nullable = false, length = 200)
    private String idioma;
    
    @Column(name="link",nullable = false, length = 200)
    private String link;

    public Palestra(String tituloEvento, String descricao, LocalDateTime dataHoraInicio,
            LocalDateTime dataHoraFim, String local, int capacidade,
            float precoEntrada, String nomeCompletoOrganizador,
            String emailOrganizador, int contatoOrganizador,
            String patrocinadores, String link, String oradores, String idioma) {

    	/*super(tituloEvento, descricao, dataHoraInicio, dataHoraFim, local,
    		      capacidade, precoEntrada, nomeCompletoOrganizador,
    		      emailOrganizador, contatoOrganizador);*/


    	this.oradores = oradores;
    	this.patrocinadores = patrocinadores;
    	this.link = link;
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

}

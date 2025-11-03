package portaleventos;
/**@author Equipa5
 * @version 02 + comentarios
 */
import java.time.LocalDateTime;

import jakarta.persistence.*;


@Entity
@Table(name = "Festa")
@DiscriminatorValue ("Festa")
public class Festa  extends Evento{


    @Column(name = "patrocinadores" , nullable = false, length =100)
    private String patrocinadores;

    @Column(name = "link" ,  length = 255)
    private String link;

    public Festa(String tituloEvento, String descricao, LocalDateTime dataHoraInicio,
            LocalDateTime dataHoraFim, String local, int capacidade,
            float precoEntrada, String nomeCompletoOrganizador,
            String emailOrganizador, int contatoOrganizador,
            String patrocinadores, String link) {

    /*super(tituloEvento, descricao, dataHoraInicio, dataHoraFim, local,
    capacidade, precoEntrada, nomeCompletoOrganizador,
    emailOrganizador, contatoOrganizador);*/

	this.patrocinadores = patrocinadores;
	this.link = link;
}

    

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

}
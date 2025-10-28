public class CancelarInscricao {
    private Long id;
    private Inscricao inscricao;
    private String motivo;

    public CancelarInscricao(Long id, Inscricao inscricao, String motivo) {
        this.id = id;
        this.inscricao = inscricao;
        this.motivo = motivo;
    }
    //gets e metodos da class Evento
    //remove o participante da lista de participantes do evento
	inscricao.getEvento().removerParticipante(inscricao.getParticipante());
    System.out.println("Inscrição cancelada: " + inscricao.getParticipante().getNome() +
            " no evento " + inscricao.getEvento().getTitulo() +
            ". Motivo: " + motivo);
}

public Long getId() {
    return id;
}
public String getMotivo() {
    return motivo;
}
public Inscricao getInscricao() {
    return inscricao;
}

    }


            }

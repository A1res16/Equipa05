public class PedidoEvento {
    private Long id;
    private String descricao;
    private String data;
    private String status;
    private UTL solicitante;

    public PedidoEvento( Long id, String descricao, String data, UTL solicitante) {
        this.id = id;
        this.descricao = descricao;
        this.data = data;
        this.status = "PENDENTE";
        this.solicitante = solicitante;
    }

    public String getDescricao() {
        return descricao;
    }
    public Long getId() {
        return id;
    }

    public void aprovar() {
        status = "APROVADO";
    }
    public void rejeitar() {
        status = "REJEITADO";
    }
}

}

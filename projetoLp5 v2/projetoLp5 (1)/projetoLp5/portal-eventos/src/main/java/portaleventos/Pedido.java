package portaleventos;

/**@author Equipa5
 * @version 02 + comentarios
 */
import jakarta.persistence.*;

import portaleventos.Enum.TipoEvento;
import portaleventos.Enum.Departamento;
import portaleventos.Enum.Estado;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pedidos")
public class Pedido
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tituloEvento;

    @Column(nullable = false)
    private String nomePromotores;

    @Column(nullable = false)
    private LocalDate data;

    //Duração em MINUTOS  (IMPLEMENTAR NAS USER STORIES)
    @Column(nullable = false)
    private float duracao;

    @Column(nullable = false)
    private String local;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoEvento TipoEvento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Departamento Departamento;

    //Orçamento em euros;
    @Column(nullable = false)
    private int orcamento;

    @Column(nullable = false)
    private int capacidade;

    @Column(nullable = false)
    private Estado Estado;

    @ManyToOne
    @JoinColumn(name = "promotor_id")
    private Promotor promotor;

    public Pedido() {
    }

    public Pedido(String tituloEvento, String nomePromotores, LocalDate data, float duracao, String local, String descricao, TipoEvento TipoEvento, Departamento Departamento, int orcamento, int capacidade, Estado Estado)
    {
        this.tituloEvento = tituloEvento;
        this.nomePromotores = nomePromotores;
        this.data = data;
        this.duracao = duracao;
        this.local = local;
        this.descricao = descricao;
        this.TipoEvento = TipoEvento;
        this.Departamento = Departamento;
        this.orcamento = orcamento;
        this.capacidade = capacidade;
        this.Estado = Estado;
    }


    public Estado getEstado()
    {
        return Estado;
    }

    public void setEstado(Estado estado)
    {
        this.Estado = estado;
    }

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the tituloEvento
	 */
	public String getTituloEvento() {
		return tituloEvento;
	}

	/**
	 * @param tituloEvento the tituloEvento to set
	 */
	public void setTituloEvento(String tituloEvento) {
		this.tituloEvento = tituloEvento;
	}

	/**
	 * @return the nomePromotores
	 */
	public String getNomePromotores() {
		return nomePromotores;
	}

	/**
	 * @param nomePromotores the nomePromotores to set
	 */
	public void setNomePromotores(String nomePromotores) {
		this.nomePromotores = nomePromotores;
	}

	/**
	 * @return the data
	 */
	public LocalDate getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(LocalDate data) {
		this.data = data;
	}

	/**
	 * @return the duracao
	 */
	public float getDuracao() {
		return duracao;
	}

	/**
	 * @param duracao the duracao to set
	 */
	public void setDuracao(float duracao) {
		this.duracao = duracao;
	}

	/**
	 * @return the local
	 */
	public String getLocal() {
		return local;
	}

	/**
	 * @param local the local to set
	 */
	public void setLocal(String local) {
		this.local = local;
	}

	/**
	 * @return the descricaoBreve
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricaoBreve to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the tipoEvento
	 */
	public TipoEvento getTipoEvento() {
		return TipoEvento;
	}

	/**
	 * @param tipoEvento the tipoEvento to set
	 */
	public void setTipoEvento(TipoEvento tipoEvento) {
		TipoEvento = tipoEvento;
	}

	/**
	 * @return the departamento
	 */
	public Departamento getDepartamento() {
		return Departamento;
	}

	/**
	 * @param departamento the departamento to set
	 */
	public void setDepartamento(Departamento departamento) {
		Departamento = departamento;
	}

	/**
	 * @return the orcamento
	 */
	public int getOrcamento() {
		return orcamento;
	}

	/**
	 * @param orcamento the orcamento to set
	 */
	public void setOrcamento(int orcamento) {
		this.orcamento = orcamento;
	}

	/**
	 * @return the capacidade
	 */
	public int getCapacidade() {
		return capacidade;
	}

	/**
	 * @param capacidade the capacidade to set
	 */
	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}

	/**
	 * @return the promotor
	 */
	public Promotor getPromotor() {
		return promotor;
	}

	/**
	 * @param promotor the promotor to set
	 */
	public void setPromotor(Promotor promotor) {
		this.promotor = promotor;
	}

    
}

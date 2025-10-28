package portaleventos.Enum;

public enum Departamento
{
    DMAG("Arquitetura e Multimédia"),
    DD("Direito"),
    DPE("Psicologia e Educação"),
    DGE("Economia e Gestão"),
    DCT("Ciência e Tecnologia"),
    DTPC("Turismo, Património e Cultura");


    private final String departamento;

    Departamento(String departamento) {
        this.departamento = departamento;
    }

    public String getDepartamento()
    {
        return departamento;
    }
}

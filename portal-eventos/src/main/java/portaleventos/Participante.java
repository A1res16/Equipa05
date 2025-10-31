package portaleventos;

//Esta classe descreve um participante, extende a classe pessoa, herdando atributos e metodos

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("PARTICIPANTE") //@DiscriminatorValue define qual perfil ou subclasse o registro representa 
//na coluna adicional na tabela criada pelo @DiscriminatorColumn

public class Participante extends Pessoa
{
	//a relacao one to many diz que um participante, pode ter varias inscricoes
	
	//a relacao é mapeada pelo campo participante na entidade Inscrever, teremos um participante id 
	//como chave primaria na tabela participante e um participante id como chave estrangeira na tabela inscrever
	
	//cascadeType.all faz o hibernate salvar/deletar um participante automaticamente quando salvados/deletamos uma inscricao
	
	//orphanRemoval apaga os participantes que sao removidos de uma inscricao
   @OneToMany(mappedBy = "participante", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Inscrever> inscricoes = new ArrayList<>();
   
   public Participante ()
   {
	   
   }

   public List<Inscrever> getInscricoes() 
   {
	   return inscricoes;
   }

   public void setInscricoes(List<Inscrever> inscricoes) 
   {
	   this.inscricoes = inscricoes;
   }
   
}

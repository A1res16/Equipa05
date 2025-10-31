package portaleventos;
/**@author aires
 * @version 1
 */
//Esta classe descreve uma fabrica de sessao, no hibernate para fazermos as operacoes na Base de dados, fazemos via sessao na SessioFactory


import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


// criamos a classe HibernateUtil para centralizar a criacao da sessionFactory
//criamos a sessionfactory em classe unitaria, porque é uma boa pratica criar só uma instancia da sessionfactory durante toda a aplicacao
//porque evida duplicacao e erros em outras partes do codigo
public class HibernateUtil 
{
	//static: pertence à classe e não a um objeto. Assim, qualquer classe pode chamar HibernateUtil.getSessionFactory()
	//sem precisar criar um novo HibernateUtil
	//final garante a referencia nao será substituida depois de criada
	private static final SessionFactory SESSION_FACTORY = buildSessionFactory();
	private static SessionFactory buildSessionFactory()
	{
		try 
		{
			//new Configuration() cria um objeto de configuracao do hibernate
			//.configure() carrega o ficheiro de configuracao padrao(cfg.xml) do nosso projeto resource
			Configuration cfg = new Configuration().configure("/hibernate.cfg.xml"); //para ler o hibernate.cfg.xml
			
			//Para registar as entidades por codigo, apaga erros no classpath nesta classe
			//isto serve para evitar erros de classe nao encontrada
			cfg.addAnnotatedClass(Pessoa.class)
			   .addAnnotatedClass(Participante.class)
			   .addAnnotatedClass(Promotor.class)
			   .addAnnotatedClass(TipoEvento.class)
			   .addAnnotatedClass(Departamento.class)
			   .addAnnotatedClass(Area.class)
			   .addAnnotatedClass(Orador.class)
			   .addAnnotatedClass(Patrocinador.class)
			   .addAnnotatedClass(Pedido.class)
			   .addAnnotatedClass(FormularioEvento.class)
			   .addAnnotatedClass(Evento.class)
			   .addAnnotatedClass(Inscrever.class); //diz: hibernate, esta classe é uma entidade mapeada (tem @Entity), usa-a na sessão.
			
			//buildSessionFactory() constroi um fabrica de sessao na qual é como uma pool de ligacoes
			ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
			
			return cfg.buildSessionFactory(registry);
		}
		catch (Throwable ex)
		{
			System.err.println("Falha ao inicializar SessionFactory: " + ex);
			throw new ExceptionInInitializerError(ex);
		}
		
		// uma SessionFactory é uma fablica que cria sessoes de objetos para interagir com a base de dados
		
	}
	public static SessionFactory getSessionFactory() 
	{
		return SESSION_FACTORY;
	}
	
	public static void shutdown()
	{
		getSessionFactory().close();
	}
   
}

package portaleventos;
/**@author aires
 * @version 2
 */
//Esta classe serve de gestao dos metodos do sistema

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import portaleventos.*;
import portaleventos.Enum.Departamento;
import portaleventos.Enum.Estado;
import portaleventos.Enum.TipoEvento;
//import portaleventos.HibernateUtil;
import Enum.Perfil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import Enum.Tipo;

public class Gere {

    private final SessionFactory sessionFactory;

    public Gere() {
        // Inicializa o SessionFactory usando hibernate.cfg.xml
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

	public List<Pedido> listarTodosPedidos() {
        Session session = sessionFactory.openSession();
        List<Pedido> pedidos = null;

        try {
            session.beginTransaction();

            Query<Pedido> query = session.createQuery("FROM Pedido", Pedido.class);
            pedidos = query.getResultList();

            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Erro ao listar pedidos: " + e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }

        return pedidos;
    }

    public List<Pedido> listarTodosPedidosDoPromotor(long id) {
        Session session = sessionFactory.openSession();
        List<Pedido> pedidos = null;

        List<Pedido> pedidosDoPromotor = null;
        try {
            session.beginTransaction();

            pedidosDoPromotor = null;

            Query<Pedido> query = session.createQuery("FROM Pedido", Pedido.class);
            pedidos = query.getResultList();
            for (Pedido p : pedidos) {
                if (p.getPromotor().getId() == id) {
                    pedidosDoPromotor.add(p);
                }
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Erro ao listar pedidos: " + e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }

        //RETORNA NULL SE NÃO HOUVER PEDIDOS
        if (pedidosDoPromotor == null)
        {
            return null;
        }


        return pedidosDoPromotor;
    }

    public List<Pedido> listarTodosPedidosAutorizadosDoPromotor(long id) {
        Session session = sessionFactory.openSession();
        List<Pedido> pedidos = null;

        List<Pedido> pedidosAutorizadosDoPromotor = null;
        try {
            session.beginTransaction();


            Query<Pedido> query = session.createQuery("FROM Pedido", Pedido.class);
            pedidos = query.getResultList();
            for (Pedido p : pedidos) {
                if (p.getPromotor().getId() == id && p.getEstado().equals(Estado.AUTORIZADO)) {
                    pedidosAutorizadosDoPromotor.add(p);
                }
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Erro ao listar pedidos: " + e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }

        //RETORNA NULL SE NÃO HOUVER PEDIDOS
        if (pedidosAutorizadosDoPromotor == null)
        {
            return null;
        }


        return pedidosAutorizadosDoPromotor;
    }


    
    public void criarAdmin() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // Verifica se já existe um utilizador com username "Admin"
        Query<Person> query = session.createQuery("FROM Person WHERE username = :username", Person.class);
        query.setParameter("username", "Admin");
        List<Person> resultado = query.getResultList();

        if (resultado.isEmpty()) {
            Person p1 = new Person();
            p1.setUsername("Admin");
            p1.setPassword("12345");
            p1.setTipo(Tipo.Ativo);
            p1.setPerfil(Perfil.Admin);

            session.persist(p1);
            System.out.println("Admin criado com sucesso.");
        } else {
            System.out.println("Admin já existe. Nenhuma ação necessária.");
        }

        session.getTransaction().commit();
        session.close();
    }

    
    public Person login(String username, String password) {
        Session session = sessionFactory.openSession();
        Person user = null;

        try {
            session.beginTransaction();

            Query<Person> query = session.createQuery(
                "FROM Person WHERE username = :username AND password = :password", Person.class);
            query.setParameter("username", username);
            query.setParameter("password", password);

            user = query.uniqueResult();

            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Erro ao fazer login: " + e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }

        return user;
    }
    
    //apenas para o admin
    public void criarConta(String username, String password, Tipo tipo, Perfil perfil) {
    Session session = sessionFactory.openSession();
    try {
        session.beginTransaction();

        // Verifica se já existe um usuário com este username
        Query<Person> query = session.createQuery("FROM Person WHERE username = :username", Person.class);
        query.setParameter("username", username);
        List<Person> resultado = query.getResultList();

        if (resultado.isEmpty()) {
            Person p1 = new Person();
            p1.setUsername(username);
            p1.setPassword(password);
            p1.setPerfil(perfil);
            p1.setTipo(tipo);

            session.persist(p1);
            session.getTransaction().commit();
            System.out.println("Conta criada com sucesso!");
        } else {
            System.out.println("Erro: Username já existe.");
        }
    } catch (Exception e) {
        session.getTransaction().rollback();
        System.out.println("Erro ao criar conta: " + e.getMessage());
    } finally {
        session.close();
    }
}
    
    //depois de dar login
    public void trocarPassword(Person person, String novaPassword) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
       
        // Atualiza a password
        person.setPassword(novaPassword);
        session.update(person); 

        session.getTransaction().commit();
        session.close();
    }
    
    //apenas para o admin
    public void eliminarConta(String username)
    {
    	Session session = sessionFactory.openSession();
        session.beginTransaction();

        // Pesquisa o utilizador pelo nome
        Person p = (Person) session.createQuery("FROM Person WHERE username = :username")
            .setParameter("username", username)
            .uniqueResult();
        
        if (p != null) {
            // elimina 
        	session.delete(p);
        }
            else {
            	System.out.println("O username não existe");
            }

            session.getTransaction().commit();
            session.close();
    }
    
    //apenas para o admin
    public void trocarTipo(String username,Tipo tipo)
    {
    	Session session = sessionFactory.openSession();
        session.beginTransaction();

        // Pesquisa o utilizador pelo nome
        Person p = (Person) session.createQuery("FROM Person WHERE username = :username")
            .setParameter("username", username)
            .uniqueResult();
        
        if (p != null) {
            // atualizar o tipo
        	p.setTipo(tipo);
        	session.update(p);
        }
        else {
            System.out.println("O username não existe");
        }

        session.getTransaction().commit();
        session.close();
    }

    public Pedido procurarPedidoPorId(long pedidoId)
    {
        Session session = sessionFactory.openSession();
        return session.get(Pedido.class, pedidoId);
    }
    
    public void eliminarEvento(long eventoId)
    {
        try (Session session = sessionFactory.openSession())
        {
            session.beginTransaction();

            Evento evento = session.get(Evento.class, eventoId);
            if (evento != null)
            {
                session.remove(evento);
                System.out.println("Evento eliminado com sucesso!");
            } else {
                System.out.println("Evento com ID " + eventoId + " não encontrado.");
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Erro ao eliminar evento: " + e.getMessage());
        }
    }

    public void eliminarPedido(long pedidoId)
    {
        try (Session session = sessionFactory.openSession())
        {
            session.beginTransaction();

            Pedido pedido = session.get(Pedido.class, pedidoId);
            if (pedido != null)
            {
                session.remove(pedido);
                System.out.println("Pedido eliminado com sucesso!");
            }
            else
            {
                System.out.println("Pedido com ID " + pedidoId + " não encontrado.");
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Erro ao eliminar evento: " + e.getMessage());
        }
    }
    
    public List<Evento> listarTodosEventos()
    {
        Session session = sessionFactory.openSession();
        List<Evento> eventos = null;

        try
        {
            session.beginTransaction();
            eventos = session.createQuery("FROM Evento", Evento.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Erro ao listar eventos: " + e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }

        return eventos;
    }

    public void criarEvento(Pedido pedido, LocalTime horaInicio, LocalTime horaFim, float orcamento, float precoEntrada, String nomeOrganizador, String emailOrganizador, int contactoOrganizador)
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        // --- Create Pedidos ---
        Evento evento = new Evento(
                pedido.getTituloEvento(),
                pedido.getDescricao(),
                pedido.getData(),
                horaInicio,
                horaFim,
                pedido.getTipoEvento(),
                pedido.getDepartamento(),
                orcamento,
                pedido.getLocal(),
                pedido.getCapacidade(),
                precoEntrada,
                pedido.getNomePromotores(),
                nomeOrganizador,
                emailOrganizador,
                contactoOrganizador
        );

        evento.setPromotor(pedido.getPromotor());

        session.persist(evento);

        session.getTransaction().commit();
        session.close();
        System.out.println("Pedido formalizado com sucesso. Evento criado!");
    }

    
    public void criarPedido(String tituloEvento, String nomePromotores, LocalDate data, float duracao, String local, String descricao, TipoEvento TipoEvento, Departamento Departamento, int orcamento, int capacidade, Estado Estado)
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        // --- Create Pedidos ---
        Pedido p1 = new Pedido(tituloEvento, nomePromotores, data, duracao, local, descricao, TipoEvento, Departamento, orcamento, capacidade, Estado);
        session.persist(p1);

        session.getTransaction().commit();
        session.close();
        System.out.println("Pedido criado!");
    }
    
    public void atualizarPedido(String tituloAntigo, String tituloNovo, String nomePromotores, LocalDate data, float duracao, String local, String descricaoBreve, TipoEvento tipoEvento, Departamento departamento, int orcamento, int capacidade, Estado estado) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // Procurar o pedido pelo título antigo
        Query<Pedido> query = session.createQuery("FROM Pedido WHERE tituloEvento = :titulo", Pedido.class);
        query.setParameter("titulo", tituloAntigo);
        List<Pedido> resultados = query.getResultList();

        if (resultados.isEmpty()) {
            System.out.println("Nenhum pedido encontrado com o título: " + tituloAntigo);
        } else {
            Pedido pedido = resultados.get(0); // Assume que só há um com esse título

            // Atualizar todos os campos
            pedido.setTituloEvento(tituloNovo);
            pedido.setNomePromotores(nomePromotores);
            pedido.setData(data);
            pedido.setDuracao(duracao);
            pedido.setLocal(local);
            pedido.setDescricao(descricaoBreve);
            pedido.setTipoEvento(tipoEvento);
            pedido.setDepartamento(departamento);
            pedido.setOrcamento(orcamento);
            pedido.setCapacidade(capacidade);
            pedido.setEstado(estado);

            session.merge(pedido);
            System.out.println("Pedido atualizado com sucesso.");
        }

        session.getTransaction().commit();
        session.close();
    }

    
}
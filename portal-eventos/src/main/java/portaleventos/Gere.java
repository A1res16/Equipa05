package portaleventos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;

public class Gere
{
    SessionFactory sessionFactory = new Configuration()
        .configure("hibernate.cfg.xml")
        .buildSessionFactory();
    Session session = sessionFactory.openSession();

    public void exit()
    {
        if (sessionFactory != null)
        {
            sessionFactory.close();
        }
    }

    public void create(String tituloEvento, String nomePromotores, LocalDate data, int duracao, String local, String descricaoBreve, Enum TipoEvento, Enum Departamento, int orcamento, int capacidade, Enum Estado)
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        // --- Create Pedidos ---
        Pedido p1 = new Pedido(tituloEvento, nomePromotores, data, duracao, local, descricaoBreve, TipoEvento, Departamento, orcamento, capacidade, Estado);
        session.persist(p1);

        session.getTransaction().commit();
        session.close();
        System.out.println("Pedido criado!");
    }





    public void remove(Pedido p1)
    {
        session.remove(p1);
        System.out.println("Pedido removido...");
    }


}
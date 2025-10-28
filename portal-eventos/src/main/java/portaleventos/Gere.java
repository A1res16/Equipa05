package portaleventos;
/**@author aires
 * @version 1
 */

//Esta classe serve de gestao dos metodos do sistema

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import portaleventos.*;
import portaleventos.HibernateUtil;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import Enum.Tipo;
import Enum.Perfil;

public class Gere {

    private SessionFactory sessionFactory;

    public Gere() {
        // Inicializa o SessionFactory usando hibernate.cfg.xml
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }
    
    //tem de chamar logo no menu 
    public void criarAdmin() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Person p1 = new Person();
        p1.setUsername("Admin");
        p1.setPassword("12345");
        p1.setTipo(Tipo.Ativo);
        p1.setPerfil(Perfil.Admin);

        session.persist(p1);

        session.getTransaction().commit();
        session.close();
    }
    
    //apenas para o admin
    public void criarConta(String username, String password,Tipo tipo,Perfil perfil) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Person p1 = new Person();
        p1.setUsername(username);
        p1.setPassword(password);
        p1.setPerfil(perfil);
        p1.setTipo(tipo);

        session.persist(p1);

        session.getTransaction().commit();
        session.close();
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
    
    public void eliminarEvento(SessionFactory sessionFactory, int eventoId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Evento evento = session.get(Evento.class, eventoId);
            if (evento != null) {
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
}

}


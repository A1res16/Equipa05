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
import java.time.LocalDateTime;

public class Gere {

    public void criarEvento(SessionFactory sessionFactory, String tituloEvento, String descricao, LocalDateTime dataHorarioInicio, LocalDateTime dataHorarioFim, String local, int capacidade, float precoEntrada, String nomeCompletoOrganizador, String emailOrganizador, int contactoOrganizador) {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Evento evento = new Evento(tituloEvento, descricao, dataHorarioInicio, dataHorarioFim, local, capacidade, precoEntrada, nomeCompletoOrganizador, emailOrganizador, contactoOrganizador);

            session.persist(evento);
            session.getTransaction().commit();

            System.out.println("Evento criado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao criar evento: " + e.getMessage());
        }
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


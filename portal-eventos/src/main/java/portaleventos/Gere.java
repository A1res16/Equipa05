package portaleventos;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Gere {
   
	private static SessionFactory sessionFactory;
	
	public Gere(){
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}
	
	public void criarTipoEvento( Evento evento) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.persist(evento);
		
		session.getTransaction().commit();
		session.close();
		System.out.println("Tipo de evento criado com sucesso");
	}
	
	
	
	@SuppressWarnings("deprecation")
	public void atualizarTipoEvento(Long id, String novoPatrocinador, String novoLink) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Evento evento = session.get(Evento.class, id);
		if (evento != null) {
			if(evento instanceof Festa f) {
				f.setPatrocinadores(novoPatrocinador);
				f.setLink(novoLink);
				session.update(f);
				System.out.println("Festa atualizada com sucesso");
			}
			else if (evento instanceof Workshop w) {
				w.setPatrocinadores(novoPatrocinador);
				w.setLink(novoLink);
				session.update(w);
				System.out.println("Workshop atualizada com sucesso");
			}
			else if (evento instanceof Exposicao e) {
				e.setPatrocinadores(novoPatrocinador);
                e.setLink(novoLink);
                session.update(e);
                System.out.println("Exposição atualizada com sucesso");
			}
			else if (evento instanceof Palestra p) {
				p.setPatrocinadores(novoPatrocinador);
                session.update(p);
                System.out.println("Palestra atualizada com sucesso");
			}
		} else {
			System.out.println("Evento com ID " + id + " não encontrado");
		}
		
		session.getTransaction().commit();
		session.close();
				}
	
	@SuppressWarnings("deprecation")
	public void eliminarTipoEventos(Long id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Evento evento = session.get(Evento.class, id);
		if (evento != null) {
			session.delete(evento);
			System.out.println("Tipo de evento eliminado com sucesso");
		} else {
			System.out.println("Evento com ID " + id + " não encontrado!");
		}
		
		session.getTransaction().commit();
		session.close();
	}
	
	public void listarEventos() {
		Session session = sessionFactory.openSession();
		var lista = session.createQuery("FROM Evento" ,  Evento.class).list();
		for(Evento e : lista) {
			System.out.println("Evento: " + e);
		}
		session.close();
	}
}

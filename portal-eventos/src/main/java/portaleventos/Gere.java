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
import org.hibernate.cfg.Configuration;

public class Gere {

public class Gere
{
	
    //Helpers de sessão/transação
    //programacao funcional
    private <T> void persist(T entity) 
    {
    	//abrir a session para as operacoes na BD
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) 
        {
            tx = s.beginTransaction();
            s.persist(entity);
            tx.commit();
        } 
        catch (Exception e) 
        { 
        	if (tx != null)
            {
        		tx.rollback(); 
        		throw e; 
            }
        }
    }
    
    private <T> void merge(T entity) 
    {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) 
        {
            tx = s.beginTransaction();
            s.merge(entity);
            tx.commit();
        } 
        catch (Exception e) 
        { 
        	if (tx != null) 
        	{
        		tx.rollback(); 
        		throw e; 
        	}
        }
    }
 
    private <T> void remove(Class<T> clazz, Long id) 
    {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) 
        {
            tx = s.beginTransaction();
            T obj = s.get(clazz, id);
            if (obj != null) s.remove(obj);
            tx.commit();
        } 
        catch (Exception e) 
        { 
        	if (tx != null)
        	{
        		tx.rollback(); 
        		throw e; 
            }
        }
     }

 /* ==========
    PESSOAS
    ========== */
 public void criarPessoa(Pessoa p) 
 { 
	 persist(p); 
 }
 public List<Pessoa> listarPessoas() 
 {
     try (Session s = HibernateUtil.getSessionFactory().openSession()) 
     {
         return s.createQuery("from Pessoa", Pessoa.class).list();
     }
 }
 public Pessoa obterPessoa(Long id) 
 {
     try (Session s = HibernateUtil.getSessionFactory().openSession()) 
     {
         return s.get(Pessoa.class, id);
     }
 }
 public void atualizarPessoa(Pessoa p) 
 { 
	 merge(p); 
 }
 public void apagarPessoa(Long id) 
 { 
	 remove(Pessoa.class, id); 
 }

 /* ==========
    PARTICIPANTE
    ========== */
 public void criarParticipante(Participante p) 
 { 
	 persist(p); 
 }
 public List<Participante> listarParticipantes() 
 {
     try (Session s = HibernateUtil.getSessionFactory().openSession()) 
     {
         return s.createQuery("from Participante", Participante.class).list();
     }
 }

 /* ==========
    PROMOTOR 
    ========== */
 public void criarPromotor(Promotor p) 
 { 
	 persist(p); 
 }
 public List<Promotor> listarPromotores() 
 {
     try (Session s = HibernateUtil.getSessionFactory().openSession()) 
     {
         return s.createQuery("from Promotor", Promotor.class).list();
     }
 }

 /* ==========
    DEPARTAMENTO / ÁREA
    ========== */
 public void criarDepartamento(Departamento d) 
 { 
	 persist(d); 
 }
 public List<Departamento> listarDepartamentos() 
 {
     try (Session s = HibernateUtil.getSessionFactory().openSession()) 
     {
         return s.createQuery("from Departamento", Departamento.class).list();
     }
 }
 public void criarArea(Area a) 
 { 
	 persist(a); 
 }
 public List<Area> listarAreasPorDepartamento(Long depId)
 {
     try (Session s = HibernateUtil.getSessionFactory().openSession()) 
     {
         return s.createQuery("from Area a where a.departamento.id = :dep", Area.class)
                 .setParameter("dep", depId).list();
     }
 }

 /* ==========
    TIPO EVENTO / ORADOR / PATROCINADOR
    ========== */
 public void criarTipoEvento(TipoEvento t) 
 { 
	 persist(t); 
 }
 public List<TipoEvento> listarTiposEvento() 
 {
     try (Session s = HibernateUtil.getSessionFactory().openSession()) 
     {
         return s.createQuery("from TipoEvento", TipoEvento.class).list();
     }
 }
 public void criarOrador(Orador o) 
 { 
	 persist(o); 
 }
 public List<Orador> listarOradores() 
 {
     try (Session s = HibernateUtil.getSessionFactory().openSession()) 
     {
         return s.createQuery("from Orador", Orador.class).list();
     }
 }
 public void criarPatrocinador(Patrocinador p) 
 { 
	 persist(p); 
 }
 public List<Patrocinador> listarPatrocinadores() 
 {
     try (Session s = HibernateUtil.getSessionFactory().openSession()) 
     {
         return s.createQuery("from Patrocinador", Patrocinador.class).list();
     }
 }

 /* ==========
    PEDIDO
    ========== */
 public void criarPedido(Pedido p) 
 { 
	 persist(p); 
 }
 public Pedido obterPedido(Long id) 
 {
     try (Session s = HibernateUtil.getSessionFactory().openSession()) 
     {
         return s.get(Pedido.class, id);
     }
 }
 public List<Pedido> listarPedidos() 
 {
     try (Session s = HibernateUtil.getSessionFactory().openSession()) 
     {
         return s.createQuery("from Pedido p order by p.dataSubmissao desc", Pedido.class).list();
     }
 }
 public List<Pedido> listarPedidosPorEstado(PedidoEstado estado) 
 {
     try (Session s = HibernateUtil.getSessionFactory().openSession()) 
     {
         return s.createQuery("from Pedido p where p.estado = :st order by p.dataSubmissao desc", Pedido.class)
                 .setParameter("st", estado).list();
     }
 }
 public void atualizarPedido(Pedido p) 
 { 
	 merge(p); 
 }
 public void apagarPedido(Long id) 
 { 
	 remove(Pedido.class, id); 
 }

 // Avaliação de pedido (Update simples)
 public void aprovarPedido(Long idPedido) 
 {
     Pedido p = obterPedido(idPedido);
     if (p == null) throw new IllegalArgumentException("Pedido não encontrado");
     p.setEstado(PedidoEstado.APROVADO);
     atualizarPedido(p);
 }
 public void rejeitarPedido(Long idPedido, String motivo) 
 {
     Pedido p = obterPedido(idPedido);
     if (p == null) throw new IllegalArgumentException("Pedido não encontrado");
     p.setEstado(PedidoEstado.REJEITADO);
     p.setJustificativa(motivo);
     atualizarPedido(p);
 }
 public void devolverPedido(Long idPedido, String motivo) 
 {
     Pedido p = obterPedido(idPedido);
     if (p == null) throw new IllegalArgumentException("Pedido não encontrado");
     p.setEstado(PedidoEstado.DEVOLVIDO);
     p.setJustificativa(motivo);
     atualizarPedido(p);
 }

 /* ==========
    FORMALIZAÇÃO (FormularioEvento)
    ========== */
 public void criarFormulario(FormularioEvento f) 
 {
     // validação mínima: tipo, promotor, pedido
     if (f.getTipo() == null) throw new IllegalArgumentException("Tipo é obrigatório");
     if (f.getPromotor() == null) throw new IllegalArgumentException("Promotor é obrigatório");
     if (f.getPedido() == null) throw new IllegalArgumentException("Pedido é obrigatório");
     if (f.getEmailOrganizador() == null || f.getEmailOrganizador().isBlank())
         throw new IllegalArgumentException("Email do organizador é obrigatório");
     persist(f);
 }

 public FormularioEvento obterFormularioPorPedido(Long pedidoId) 
 {
     try (Session s = HibernateUtil.getSessionFactory().openSession()) 
     {
         return s.createQuery("from FormularioEvento f where f.pedido.id = :pid", FormularioEvento.class)
                 .setParameter("pid", pedidoId).uniqueResult();
     }
 }

 /* ==========
    EVENTO
    ========== */
 public void criarEvento(Evento e) 
 { 
	 persist(e); 
 }
 public Evento obterEvento(Long id) 
 {
     try (Session s = HibernateUtil.getSessionFactory().openSession()) {
         return s.get(Evento.class, id);
     }
 }
 public List<Evento> listarEventos() 
 {
     try (Session s = HibernateUtil.getSessionFactory().openSession()) 
     {
         return s.createQuery("from Evento e order by e.dataHoraInicio", Evento.class).list();
     }
 }
 public void atualizarEvento(Evento e) 
 { 
	 merge(e); 
 }
 public void apagarEvento(Long id) 
 { 
	 remove(Evento.class, id); 
 }

 // Publicar Evento a partir do Formulário
 public Evento publicarEventoAPartirDoFormulario(Long pedidoId) 
 {
     FormularioEvento f = obterFormularioPorPedido(pedidoId);
     if (f == null) throw new IllegalArgumentException("Formulário não encontrado para o pedido " + pedidoId);

     // copia campos base
     Evento e = new Evento();
     e.setTitulo(f.getTitulo());
     e.setDescricao(f.getDescricao());
     e.setDataHoraInicio(f.getDataHoraInicio());
     e.setDataHoraFim(f.getDataHoraFim());
     e.setCapacidade(f.getCapacidade());
     e.setPrecoEntrada(f.getPrecoEntrada() != null ? f.getPrecoEntrada() : BigDecimal.ZERO);
     e.setPromotor(f.getPromotor());
     e.setTipo(f.getTipo());
     e.setPedidoOrigem(f.getPedido());
     e.setNecessitaInscricao(true);

     // validações por tipo (simples)
     String tipo = f.getTipo().getDescricao();
     if ("Festa".equalsIgnoreCase(tipo)) 
     {
         if (f.getPatrocinadores().isEmpty())
             throw new IllegalArgumentException("Festa requer pelo menos 1 patrocinador.");
         e.getPatrocinadores().addAll(f.getPatrocinadores());
     } 
     else if ("Palestra".equalsIgnoreCase(tipo)) 
     {
         if (f.getOradores().isEmpty())
             throw new IllegalArgumentException("Palestra requer pelo menos 1 orador.");
         e.getOradores().addAll(f.getOradores());
     } 
     else if ("Workshop".equalsIgnoreCase(tipo)) 
     {
         if (Boolean.TRUE.equals(f.getInscricaoPrevia()) && (f.getVagasPraticas() == null || f.getVagasPraticas() <= 0))
             throw new IllegalArgumentException("Workshop com inscrição prévia requer vagasPraticas > 0.");
         e.getInstrutores().addAll(f.getInstrutores());
     } 
     else if ("Exposição".equalsIgnoreCase(tipo)) 
     {
         e.getArtistas().addAll(f.getArtistas());
         e.getObras().addAll(f.getObras());
     }

     criarEvento(e);
     // marca pedido como APROVADO se ainda não estiver
     Pedido p = f.getPedido();
     if (p.getEstado() != PedidoEstado.APROVADO) 
     {
         p.setEstado(PedidoEstado.APROVADO);
         atualizarPedido(p);
     }
     return e;
 }

 /* ==========
    INSCRIÇÕES
    ========== */
 public void inscreverParticipanteNoEvento(Participante part, Evento evento) 
 {
     // evita duplicado
     try (Session s = HibernateUtil.getSessionFactory().openSession()) 
     {
         Long count = s.createQuery(
                 "select count(i.id) from Inscrever i where i.participante.id = :pid and i.evento.id = :eid", Long.class)
                 .setParameter("pid", part.getId())
                 .setParameter("eid", evento.getId())
                 .uniqueResult();
         if (count != null && count > 0) throw new IllegalStateException("Participante já inscrito.");
     }
     Inscrever i = new Inscrever();
     i.setParticipante(part);
     i.setEvento(evento);
     persist(i);
 }

 public void cancelarInscricao(Long inscricaoId) 
 {
     try (Session s = HibernateUtil.getSessionFactory().openSession()) 
     {
         Transaction tx = s.beginTransaction();
         Inscrever i = s.get(Inscrever.class, inscricaoId);
         if (i != null) 
         {
             i.setEstado(EstadoInscricao.CANCELADA);
             s.merge(i);
         }
         tx.commit();
     } 
     catch (Exception e) 
     { 
    	 throw e; 
     }
 }

 public List<Inscrever> listarInscricoesDoEvento(Long eventoId) 
 {
     try (Session s = HibernateUtil.getSessionFactory().openSession()) 
     {
         return s.createQuery("from Inscrever i where i.evento.id = :eid", Inscrever.class)
                 .setParameter("eid", eventoId).list();
     }
 }

 /* ==========
    FILTROS (estado / data / tipo)
    ========== */
 public List<Evento> filtrarEventosPorEstado(EventoEstado estado) 
 {
     try (Session s = HibernateUtil.getSessionFactory().openSession()) 
     {
         return s.createQuery("from Evento e where e.estado = :st order by e.dataHoraInicio", Evento.class)
                 .setParameter("st", estado).list();
     }
 }

 public List<Evento> filtrarEventosPorIntervalo(LocalDateTime inicio, LocalDateTime fim) 
 {
     try (Session s = HibernateUtil.getSessionFactory().openSession()) 
     {
         return s.createQuery("from Evento e where e.dataHoraInicio >= :i and e.dataHoraFim <= :f order by e.dataHoraInicio",
                         Evento.class)
                 .setParameter("i", inicio).setParameter("f", fim).list();
     }
 }

 public List<Evento> filtrarEventosPorTipo(Long tipoId) 
 {
     try (Session s = HibernateUtil.getSessionFactory().openSession()) 
     {
         return s.createQuery("from Evento e where e.tipo.id = :tid order by e.dataHoraInicio", Evento.class)
                 .setParameter("tid", tipoId).list();
     }
 }

 public List<Pedido> filtrarPedidosPorEstado(PedidoEstado estado) 
 {
     try (Session s = HibernateUtil.getSessionFactory().openSession()) 
     {
         return s.createQuery("from Pedido p where p.estado = :st order by p.dataSubmissao desc", Pedido.class)
                 .setParameter("st", estado).list();
     }
 }

 public List<Pedido> listarPedidosPorDepArea(Long depId, Long areaId) 
 {
     try (Session s = HibernateUtil.getSessionFactory().openSession()) 
     {
         return s.createQuery("from Pedido p where p.departamento.id = :dep and p.area.id = :area order by p.dataSubmissao desc",
                         Pedido.class)
                 .setParameter("dep", depId)
                 .setParameter("area", areaId)
                 .list();
     }
 }

 /* ==========
    LOGIN (simples)
    ========== */
 public Pessoa autenticar(String username, String pwHash) 
 {
     try (Session s = HibernateUtil.getSessionFactory().openSession()) 
     {
         return s.createQuery("from Pessoa p where p.username = :u and p.passwordHash = :h and p.ativo = true", Pessoa.class)
                 .setParameter("u", username)
                 .setParameter("h", pwHash)
                 .uniqueResult();
     }
 }
}


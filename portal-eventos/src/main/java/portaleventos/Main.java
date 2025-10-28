package portaleventos;
/**@author aires
 * @version 1
 */

//Esta classe serve de menu do sistema

import portaleventos.Gere;
import portaleventos.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main 
{

    
    private static final Gere gere = new Gere();
    private static final Scanner in = new Scanner(System.in);

    // Sessão corrente (Aires)
    private static Pessoa logado;

    public static void main(String[] args) 
    {
        System.out.println("=== Portal de Gestão de Eventos UPT ===");

        seedMinimo(); // cria Tipos, Dep/Áreas, Utilizadores base (Zé e Alexandre)

        boolean sair = false;
        while (!sair) 
        {
            mostrarMenu();
            int op = lerInt("Opção: ");
            try 
            {
                switch (op) 
                {
                    case 1: fazerLogin();                         // Aires
                    case 2: criarPedido();                        // Pedro
                    case 3: formalizarPedido();                   // Pedro e Alexandre
                    case 4: avaliarPedido();                      // Aires
                    case 5: publicarEventoDePedido();             // Rodri
                    case 6: listarEventos();                      // Rodri
                    case 7: filtrarEventos();                     // Rodri (filtros)
                    case 8: gerirTiposOradoresPatrocinadores();   // Alexandre
                    case 9: inscreverParticipanteEmEvento();      // Elisio
                    case 10: cancelarInscricao();                 // Elisio
                    case 11: criarUtilizadoresRapido();           // Zé
                    case 0: sair = true;
                    default: System.out.println("Opção inválida!");
                }
            } 
            catch (Exception e) 
            {
                System.err.println("Erro: " + e.getMessage());
            }
        }
        System.out.println("Adeus!");
    }

    // ---------------------- MENU ----------------------
    private static void mostrarMenu() 
    {        
    	System.out.println(" -------- MENU DEMO ------- ");
    	System.out.println(" 1) Login ");
    	System.out.println(" 2) Criar Pedido ");
    	System.out.println(" 3) Formalizar Pedido ");
    	System.out.println(" 4) Avaliar Pedido (aprovar/rejeitar/devolver) ");
    	System.out.println(" 5) Publicar Evento a partir de Pedido/Formulário ");
    	System.out.println(" 6) Listar Eventos ");
    	System.out.println(" 7) Filtros de Eventos (estado, período, tipo) ");
    	System.out.println(" 8) Gerir Tipos / Oradores / Patrocinadores ");
    	System.out.println(" 9) Inscrever Participante em Evento ");
    	System.out.println(" 10) Cancelar Inscrição ");
    	System.out.println(" 11) Criar Utilizadores Rápido (Promotor/Participante) ");
    	System.out.println(" 0) Sair ");

    }

    // ---------------------- SEED ----------------------
    private static void seedMinimo() 
    {
        if (gere.listarTiposEvento().isEmpty()) 
        {
            String[] ts = {"Festa", "Palestra", "Workshop", "Exposição"};
            for (String t : ts) 
            { 
            	var te = new TipoEvento(); 
            	te.setDescricao(t); 
            	gere.criarTipoEvento(te); 
            }
            System.out.println("Seed: Tipos criados.");
        }
        
        // Departamento/Áreas (apoio a Pedido)
        if (gere.listarDepartamentos().isEmpty()) 
        {
            var d = new Departamento(); d.setNome("DCT"); gere.criarDepartamento(d);
            var a1 = new Area(); 
            a1.setNome("IA"); 
            a1.setDepartamento(d); 
            gere.criarArea(a1);
            
            var a2 = new Area(); 
            a2.setNome("Sistemas"); 
            a2.setDepartamento(d); 
            gere.criarArea(a2);
            System.out.println("Seed: Departamento/Áreas criados.");
        }
        
        // Utilizadores 
        if (gere.listarPromotores().isEmpty()) 
        {
            Promotor p = new Promotor();
            p.setNome("Ze Promotor");
            p.setUsername("Ze");
            p.setPasswordHash("123");
            p.setEmail("ze@upt.pt");
            gere.criarPromotor(p);
        }
        if (gere.listarParticipantes().isEmpty()) 
        {
            Participante u = new Participante();
            u.setNome("Aires Participante");
            u.setUsername("Aires");
            u.setPasswordHash("1234");
            u.setEmail("aires@upt.pt");
            gere.criarParticipante(u);
        }
    }

    // ---------------------- LOGIN ----------------------
    private static void fazerLogin() 
    {
        System.out.println("== Login ==");
        String u = ler("username: ");
        String p = ler("password (hash simples): ");
        logado = gere.autenticar(u, p);
        
        if (logado == null) 
        {
        	System.out.println("O login falhou.");
        }
        else 
        {
        	System.out.println("Olá, " + logado.getNome() + "!");
        }
    }

    // ---------------------- PEDIDO ----------------------
    private static void criarPedido() 
    {
        System.out.println("== Criar Pedido ==");
        Promotor prom = escolherPromotor();
        Departamento dep = escolherDepartamento();
        Area area = escolherAreaDoDepartamento(dep);
        TipoEvento tipo = escolherTipo();

        Pedido pd = new Pedido();
        pd.setTitulo(ler("Título: "));
        pd.setDescricaoBreve(ler("Descrição breve: "));
        pd.setLocalProposto(ler("Local proposto: "));
        pd.setCapacidadeSolicitada(lerInt("Capacidade solicitada: "));
        pd.setDataDia(LocalDate.now().plusDays(lerInt("Dias a partir de hoje (data do evento): ")));
        pd.setPromotor(prom);
        pd.setDepartamento(dep);
        pd.setArea(area);
        pd.setTipo(tipo);

        gere.criarPedido(pd);
        System.out.println("Pedido criado id=" + pd.getId() + " (estado=PENDENTE).");
    }

    // ---------------------- FORMALIZAR ----------------------
    private static void formalizarPedido() 
    {
        System.out.println("== Formalizar Pedido (gera o formulário) ==");
        Pedido p = escolherPedidoPorEstado(PedidoEstado.PENDENTE);
        if (p == null) 
        {
        	return;
        }

        FormularioEvento f = new FormularioEvento();
        f.setPedido(p);
        f.setPromotor(p.getPromotor());
        f.setTipo(p.getTipo());

        f.setTitulo(ler("Título final: "));
        f.setDescricao(ler("Descrição final: "));
        f.setDataHoraInicio(LocalDateTime.parse(ler("Início (YYYY-MM-DDTHH:MM): ")));
        f.setDataHoraFim(LocalDateTime.parse(ler("Fim (YYYY-MM-DDTHH:MM): ")));
        f.setCapacidade(lerInt("Capacidade final: "));
        f.setPrecoEntrada(new BigDecimal(ler("Preço de entrada (ex: 0.00): ")));

        f.setNomeOrganizador(ler("Nome do organizador: "));
        f.setEmailOrganizador(ler("E-mail do organizador: "));
        f.setTelefoneOrganizador(ler("Telefone do organizador (opcional): "));

        String tipo = p.getTipo().getDescricao();
        if ("Palestra".equalsIgnoreCase(tipo)) 
        {
            System.out.println("Adicionar pelo menos 1 orador (vazio para terminar):");
            while (true) 
            {
                String nome = ler("Orador: ");
                if (nome.isBlank()) 
                {
                	break;
                }
                Orador o = new Orador(); 
                o.setNome(nome);
                gere.criarOrador(o);
                f.getOradores().add(o);
            }
        } 
        else if ("Festa".equalsIgnoreCase(tipo)) 
        {
            System.out.println("Adicionar patrocinadores (vazio para terminar):");
            while (true) 
            {
                String nome = ler("Patrocinador: ");
                if (nome.isBlank()) 
                {
                	break;
                }
                Patrocinador pat = new Patrocinador(); 
                pat.setNome(nome);
                gere.criarPatrocinador(pat);
                f.getPatrocinadores().add(pat);
            }
        } 
        else if ("Workshop".equalsIgnoreCase(tipo)) 
        {
            f.setInscricaoPrevia(ler("Inscrição prévia? (s/n): ").trim().equalsIgnoreCase("s"));
            if (Boolean.TRUE.equals(f.getInscricaoPrevia())) 
            {
                f.setVagasPraticas(lerInt("Vagas práticas: "));
            }
            f.setRequisitosMateriais(ler("Requisitos/materiais do participante: "));
            f.setMateriaisFornecidos(ler("Materiais fornecidos pela organização: "));
            f.setCargaHorariaHoras(lerInt("Carga horária (h): "));

            System.out.println("Instrutores (vazio para terminar):");
            while (true) 
            {
                String nome = ler("Instrutor: ");
                if (nome.isBlank()) 
                {
                	break;
                }
                Orador o = new Orador(); 
                o.setNome(nome);
                gere.criarOrador(o);
                f.getInstrutores().add(o);
            }
        } 
        else if ("Exposição".equalsIgnoreCase(tipo)) 
        {
            System.out.println("Artistas (vazio para terminar):");
            while (true) 
            {
                String nome = ler("Artista: ");
                if (nome.isBlank())
                {
                	break;
                }
                f.getArtistas().add(nome);
            }
            System.out.println("Obras/Temas (vazio para terminar):");
            while (true) 
            {
                String ob = ler("Obra/Tema: ");
                if (ob.isBlank()) 
                {
                	break;
                }
                f.getObras().add(ob);
            }
        }

        gere.criarFormulario(f);
        System.out.println("O Formulário foi criado para pedido id=" + p.getId() + ". Agora podes avaliar.");
    }

    // ---------------------- AVALIAR PEDIDO ----------------------
    private static void avaliarPedido() 
    {
        System.out.println("== Avaliar Pedido ==");
        System.out.println("1) Aprovar  2) Rejeitar  3) Devolver");
        int op = lerInt("Escolhe: ");
        Pedido p = escolherPedidoAvaliavel();
        if (p == null) 
        {
        	return;
        }

        switch (op) 
        {
            case 1: 
                   { gere.aprovarPedido(p.getId()); 
                     System.out.println("Pedido aprovado."); 
                   }
            case 2: 
                   { gere.rejeitarPedido(p.getId(), ler("Motivo rejeição: ")); 
                     System.out.println("Pedido rejeitado."); 
                   }
            case 3: 
                   { gere.devolverPedido(p.getId(), ler("Motivo devolução: ")); 
                     System.out.println("Pedido devolvido."); 
                   }
            default: System.out.println("Opção inválida.");
        }
    }

    // ---------------------- PUBLICAR EVENTO ----------------------
    private static void publicarEventoDePedido() 
    {
        System.out.println("== Publicar Evento ==");
        Long id = (long) lerInt("ID do pedido (com formulário): ");
        Evento e = gere.publicarEventoAPartirDoFormulario(id);
        System.out.println("Evento publicado id=" + e.getId() + " | " + e.getTitulo());
    }

    // ---------------------- LISTAR / FILTRAR ----------------------
    private static void listarEventos() 
    {
        System.out.println("== Eventos ==");
        for (Evento e : gere.listarEventos()) 
        {
            System.out.printf("#%d | %s | %s -> %s | tipo=%s | estado=%s | preço=%s%n",
                    e.getId(), e.getTitulo(), e.getDataHoraInicio(), e.getDataHoraFim(),
                    e.getTipo().getDescricao(), e.getEstado(), e.getPrecoEntrada());
        }
    }

    private static void filtrarEventos() 
    {
        System.out.println(" 1) Por Estado ");
        System.out.println(" 2) Por Período (agora até +60 dias) ");
        System.out.println(" 3) Por Tipo ");
        
        int op = lerInt("Escolhe: ");
        switch (op) 
        {
            case 1: 
            {
                String st = ler("Estado (CONFIRMADO/CANCELADO): ").trim().toUpperCase();
                EventoEstado est = EventoEstado.valueOf(st);
                //Utilizamos lambda
                gere.filtrarEventosPorEstado(est).forEach(e ->
                        System.out.println(e.getId() + " - " + e.getTitulo() + " (" + e.getEstado() + ")"));
            }
            case 2:
            {
                LocalDateTime ini = LocalDateTime.now().minusDays(1);
                LocalDateTime fim = LocalDateTime.now().plusDays(60);
                gere.filtrarEventosPorIntervalo(ini, fim).forEach(e ->
                        System.out.println(e.getId() + " - " + e.getTitulo() + " (" + e.getDataHoraInicio() + ")"));
            }
            case 3: 
            {
                TipoEvento t = escolherTipo();
                gere.filtrarEventosPorTipo(t.getId()).forEach(e ->
                        System.out.println(e.getId() + " - " + e.getTitulo() + " [" + e.getTipo().getDescricao() + "]"));
            }
            default: System.out.println("Opção inválida.");
        }
    }

    // ---------------------- TIPOS / ORADORES / PATROCINADORES ----------------------
    private static void gerirTiposOradoresPatrocinadores() 
    {
        
    	System.out.println(" 1) Criar Tipo ");
    	System.out.println(" 2) Listar Tipos ");
    	System.out.println(" 3) Criar Orador ");
    	System.out.println(" 4) Listar Oradores ");
    	System.out.println(" 5) Criar Patrocinador ");
    	System.out.println(" 6) Listar Patrocinadores ");
                
        int op = lerInt("Escolhe: ");
        switch (op) 
        {
            case 1: 
            {
                var t = new TipoEvento(); t.setDescricao(ler("Descrição do tipo: "));
                gere.criarTipoEvento(t);
                System.out.println("Tipo criado.");
            }
            case 2: gere.listarTiposEvento().forEach(t -> System.out.println(t.getId() + " - " + t.getDescricao()));
            case 3: 
            {
                var o = new Orador(); o.setNome(ler("Nome orador: ")); o.setContacto(ler("Contacto (opcional): "));
                gere.criarOrador(o); System.out.println("Orador criado.");
            }
            case 4: gere.listarOradores().forEach(o -> System.out.println(o.getId() + " - " + o.getNome()));
            case 5: 
            {
                var p = new Patrocinador(); p.setNome(ler("Nome patrocinador: "));
                p.setContacto(ler("Contacto (opcional): "));
                gere.criarPatrocinador(p); System.out.println("Patrocinador criado.");
            }
            case 6: gere.listarPatrocinadores().forEach(p -> System.out.println(p.getId() + " - " + p.getNome()));
            default: System.out.println("Opção inválida.");
        }
    }

    // ---------------------- INSCRIÇÕES ----------------------
    private static void inscreverParticipanteEmEvento() 
    {
        System.out.println("== Inscrever Participante em Evento ==");
        Participante part = escolherParticipante();
        Evento e = escolherEvento();
        gere.inscreverParticipanteNoEvento(part, e);
        System.out.println("Inscrição criada.");
    }

    private static void cancelarInscricao() 
    {
        System.out.println("== Cancelar Inscrição ==");
        Evento e = escolherEvento();
        var inscs = gere.listarInscricoesDoEvento(e.getId());
        if (inscs.isEmpty()) 
        { 
        	System.out.println("Sem inscrições para este evento."); 
        	return; 
        }
        for (Inscrever i : inscs) 
        {
            System.out.printf("Inscrição #%d | Participante=%s | Estado=%s%n",
                    i.getId(), i.getParticipante().getNome(), i.getEstado());
        }
        long id = lerLong("ID da inscrição a cancelar: ");
        gere.cancelarInscricao(id);
        System.out.println("Inscrição cancelada.");
    }

    // ---------------------- CRIAR USERS ----------------------
    private static void criarUtilizadoresRapido() 
    {
    	System.out.println(" 1) Promotor ");
    	System.out.println(" 2) Participante ");
               
        int op = lerInt("Escolhe: ");
        if (op == 1) 
        {
            var p = new Promotor();
            p.setNome(ler("Nome: "));
            p.setUsername(ler("Username: "));
            p.setPasswordHash(ler("Password (hash simples): "));
            p.setEmail(ler("Email: "));
            gere.criarPromotor(p);
        } 
        else if (op == 2) 
        {
            var u = new Participante();
            u.setNome(ler("Nome: "));
            u.setUsername(ler("Username: "));
            u.setPasswordHash(ler("Password (hash simples): "));
            u.setEmail(ler("Email: "));
            gere.criarParticipante(u);
        } 
        else 
        {
            System.out.println("Opção inválida.");
        }
    }

    // ---------------------- HELPERS DE ESCOLHA ----------------------
    private static Promotor escolherPromotor() 
    {
        var lst = gere.listarPromotores();
        if (lst.isEmpty()) 
        {
        	throw new IllegalStateException("Sem promotores. Cria um primeiro.");
        }
        lst.forEach(p -> System.out.println(p.getId() + " - " + p.getNome() + " (" + p.getUsername() + ")"));
        long id = lerLong("ID do promotor: ");
        return lst.stream().filter(p -> p.getId().equals(id)).findFirst().orElseThrow();
    }

    private static Participante escolherParticipante() 
    {
        var lst = gere.listarParticipantes();
        if (lst.isEmpty()) 
        {
        	throw new IllegalStateException("Sem participantes. Cria um primeiro.");
        }
        lst.forEach(p -> System.out.println(p.getId() + " - " + p.getNome() + " (" + p.getUsername() + ")"));
        long id = lerLong("ID do participante: ");
        return lst.stream().filter(p -> p.getId().equals(id)).findFirst().orElseThrow();
    }

    private static Departamento escolherDepartamento() 
    {
        var lst = gere.listarDepartamentos();
        lst.forEach(d -> System.out.println(d.getId() + " - " + d.getNome() ));
        long id = lerLong("ID do departamento: ");
        return lst.stream().filter(d -> d.getId().equals(id)).findFirst().orElseThrow();
    }

    private static Area escolherAreaDoDepartamento(Departamento d) 
    {
        var lst = gere.listarAreasPorDepartamento(d.getId());
        lst.forEach(a -> System.out.println(a.getId() + " - " + a.getNome()));
        long id = lerLong("ID da área: ");
        return lst.stream().filter(a -> a.getId().equals(id)).findFirst().orElseThrow();
    }

    private static TipoEvento escolherTipo() 
    {
        var lst = gere.listarTiposEvento();
        lst.forEach(t -> System.out.println(t.getId() + " - " + t.getDescricao()));
        long id = lerLong("ID do tipo: ");
        return lst.stream().filter(t -> t.getId().equals(id)).findFirst().orElseThrow();
    }

    private static Pedido escolherPedidoPorEstado(PedidoEstado estado) 
    {
        var lst = gere.listarPedidosPorEstado(estado);
        if (lst.isEmpty()) 
        { 
        	System.out.println("Sem pedidos em " + estado + "."); 
        	return null; 
        }
        lst.forEach(p -> System.out.println(p.getId() + " - " + p.getTitulo()));
        long id = lerLong("ID do pedido: ");
        return lst.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    private static Pedido escolherPedidoAvaliavel() 
    {
        var lst = gere.listarPedidos(); // podes filtrar por pendente/devolvido
        if (lst.isEmpty()) 
        { 
        	System.out.println("Sem pedidos."); 
        	return null; 
        }
        lst.forEach(p -> System.out.println(p.getId() + " - " + p.getTitulo() + " | estado=" + p.getEstado()));
        long id = lerLong("ID do pedido: ");
        return lst.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    private static Evento escolherEvento() 
    {
        var lst = gere.listarEventos();
        if (lst.isEmpty()) 
        { 
        	System.out.println("Sem eventos."); 
        	return null; 
        }
        lst.forEach(e -> System.out.println(e.getId() + " - " + e.getTitulo()));
        long id = lerLong("ID do evento: ");
        return lst.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }

    // ---------------------- IO UTILS ----------------------
    private static String ler(String msg) 
    { 
    	System.out.print(msg); 
    	return in.nextLine(); 
    }
    private static int lerInt(String msg) 
    { 
    	return Integer.parseInt(ler(msg)); 
    }
    private static long lerLong(String msg) 
    { 
    	return Long.parseLong(ler(msg)); 
    }
}

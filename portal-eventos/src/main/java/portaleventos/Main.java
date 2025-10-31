package portaleventos;
/**@author aires
 * @version 1
 */

//Esta classe serve de menu do sistema

import portaleventos.Gere;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Gere gere = new Gere();
    private static final Scanner in = new Scanner(System.in);

    // Sessão corrente (login)
    private static Pessoa logado;

    public static void main(String[] args) {
        System.out.println("=== Portal de Gestão de Eventos UPT ===");

        seedMinimo(); // cria Tipos, Dep/Áreas, Utilizadores base

        boolean sair = false;
        while (!sair) {
            mostrarMenu();
            int op = lerInt("Opção: ");
            try {
                switch (op) {
                    case 1: fazerLogin(); break;                      // EL3
                    case 2: criarPedido(); break;                     // EL2
                    case 3: formalizarPedido(); break;                // EL2 + EL5
                    case 4: avaliarPedido(); break;                   // EL3
                    case 5: publicarEventoDePedido(); break;          // EL4
                    case 6: listarEventos(); break;                   // EL4
                    case 7: filtrarEventos(); break;                  // EL4 (filtros)
                    case 8: gerirTiposOradoresPatrocinadores(); break;// EL5
                    case 9: inscreverParticipanteEmEvento(); break;   // EL6
                    case 10: cancelarInscricao(); break;              // EL6
                    case 11: criarUtilizadoresRapido(); break;        // EL1
                    case 0: sair = true; break;
                    default: System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.err.println(" Erro: " + e.getMessage());
            }
        }
        System.out.println("Adeus!");
    }

    // ---------------------- MENU ----------------------
    private static void mostrarMenu() {
        System.out.println("\n -------- MENU de DEMONSTRACAO ------- ");
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
    
    //usamos a semente para criar dados rapidos sem precisar inserir manualmente, para testar porque estavamos acima do tempo
    //o seed é um conjunto de dados iniciais que são inseridos automaticamente na base de dados para que o sistema
    //tenha algo funcional logo de início
    private static void seedMinimo() {

        if (gere.listarTiposEvento().isEmpty()) {
            String[] ts = {"Festa", "Palestra", "Workshop", "Exposição"};
            for (String t : ts) {
                TipoEvento te = new TipoEvento();
                te.setDescricao(t);
                gere.criarTipoEvento(te);
            }
            System.out.println("Seed: Tipos criados.");
        }

        // Departamento/Áreas (apoio a Pedido)
        if (gere.listarDepartamentos().isEmpty()) {
            Departamento d = new Departamento();
            d.setNome("DCT");
            gere.criarDepartamento(d);

            Area a1 = new Area();
            a1.setNome("Programacao");
            a1.setDepartamento(d);
            gere.criarArea(a1);

            Area a2 = new Area();
            a2.setNome("Dados");
            a2.setDepartamento(d);
            gere.criarArea(a2);

            System.out.println("Seed: Departamento/Áreas criados.");
        }

        // Utilizadores (Promotor + Participante)
        if (gere.listarPromotores().isEmpty()) {
            Promotor p = new Promotor();
            p.setNome("Ze Promotor");
            p.setUsername("Ze");
            p.setPasswordHash("123");
            p.setEmail("ze@upt.pt");
            gere.criarPromotor(p);
        }

        if (gere.listarParticipantes().isEmpty()) {
            Participante u = new Participante();
            u.setNome("Aires Participante");
            u.setUsername("Aires");
            u.setPasswordHash("1234");
            u.setEmail("aires@upt.pt");
            gere.criarParticipante(u);
        }
    }

    // ---------------------- LOGIN ----------------------
    private static void fazerLogin() {
        System.out.println("== Login ==");
        String u = ler("username: ");
        String p = ler("password (hash simples): ");
        logado = gere.autenticar(u, p);

        if (logado == null)
            System.out.println("O login falhou.");
        else
            System.out.println("Olá, " + logado.getNome() + "!");
    }

    // ---------------------- PEDIDO ----------------------
    private static void criarPedido() {
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
    private static void formalizarPedido() {
        System.out.println("== Formalizar Pedido (gera o formulário) ==");
        Pedido p = escolherPedidoPorEstado(PedidoEstado.PENDENTE);
        if (p == null) return;

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
        if ("Palestra".equalsIgnoreCase(tipo)) {
            System.out.println("Adicionar pelo menos 1 orador (vazio para terminar):");
            while (true) {
                String nome = ler("Orador: ");
                if (nome == null || nome.trim().isEmpty()) break;
                Orador o = new Orador();
                o.setNome(nome);
                gere.criarOrador(o);
                f.getOradores().add(o);
            }
        } else if ("Festa".equalsIgnoreCase(tipo)) {
            System.out.println("Adicionar patrocinadores (vazio para terminar):");
            while (true) {
                String nome = ler("Patrocinador: ");
                if (nome == null || nome.trim().isEmpty()) break;
                Patrocinador pat = new Patrocinador();
                pat.setNome(nome);
                gere.criarPatrocinador(pat);
                f.getPatrocinadores().add(pat);
            }
        } else if ("Workshop".equalsIgnoreCase(tipo)) {
            f.setInscricaoPrevia(ler("Inscrição prévia? (s/n): ").trim().equalsIgnoreCase("s"));
            if (Boolean.TRUE.equals(f.getInscricaoPrevia())) {
                f.setVagasPraticas(lerInt("Vagas práticas: "));
            }
            f.setRequisitosMateriais(ler("Requisitos/materiais do participante: "));
            f.setMateriaisFornecidos(ler("Materiais fornecidos pela organização: "));
            f.setCargaHorariaHoras(lerInt("Carga horária (h): "));

            System.out.println("Instrutores (vazio para terminar):");
            while (true) {
                String nome = ler("Instrutor: ");
                if (nome == null || nome.trim().isEmpty()) break;
                Orador o = new Orador();
                o.setNome(nome);
                gere.criarOrador(o);
                f.getInstrutores().add(o);
            }
        } else if ("Exposição".equalsIgnoreCase(tipo)) {
            System.out.println("Artistas (vazio para terminar):");
            while (true) {
                String nome = ler("Artista: ");
                if (nome == null || nome.trim().isEmpty()) break;
                f.getArtistas().add(nome);
            }
            System.out.println("Obras/Temas (vazio para terminar):");
            while (true) {
                String ob = ler("Obra/Tema: ");
                if (ob == null || ob.trim().isEmpty()) break;
                f.getObras().add(ob);
            }
        }

        gere.criarFormulario(f);
        System.out.println("Formulário criado para pedido id=" + p.getId() + ".");
    }

    // ---------------------- AVALIAR PEDIDO ----------------------
    private static void avaliarPedido() {
        System.out.println("== Avaliar Pedido ==");
        System.out.println("1) Aprovar  2) Rejeitar  3) Devolver");
        int op = lerInt("Escolhe: ");
        Pedido p = escolherPedidoAvaliavel();
        if (p == null) return;

        switch (op) {
            case 1:
                gere.aprovarPedido(p.getId());
                System.out.println("Pedido aprovado.");
                break;
            case 2:
                gere.rejeitarPedido(p.getId(), ler("Motivo rejeição: "));
                System.out.println("Pedido rejeitado.");
                break;
            case 3:
                gere.devolverPedido(p.getId(), ler("Motivo devolução: "));
                System.out.println("Pedido devolvido.");
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    // ---------------------- PUBLICAR EVENTO ----------------------
    private static void publicarEventoDePedido() {
        System.out.println("== Publicar Evento ==");
        Long id = (long) lerInt("ID do pedido (com formulário): ");
        Evento e = gere.publicarEventoAPartirDoFormulario(id);
        System.out.println("Evento publicado id=" + e.getId() + " | " + e.getTitulo());
    }

    // ---------------------- LISTAR / FILTRAR ----------------------
    private static void listarEventos() {
        System.out.println("== Eventos ==");
        List<Evento> eventos = gere.listarEventos();
        for (Evento e : eventos) {
            System.out.printf("#%d | %s | %s -> %s | tipo=%s | estado=%s | preço=%s%n",
                    e.getId(), e.getTitulo(), e.getDataHoraInicio(), e.getDataHoraFim(),
                    e.getTipo().getDescricao(), e.getEstado(), e.getPrecoEntrada());
        }
    }

    private static void filtrarEventos() {
        System.out.println("1) Por Estado");
        System.out.println("2) Por Período (agora até +60 dias)");
        System.out.println("3) Por Tipo");
        int op = lerInt("Escolhe: ");
        switch (op) {
            case 1: {
                String st = ler("Estado (CONFIRMADO/CANCELADO): ").trim().toUpperCase();
                EventoEstado est = EventoEstado.valueOf(st);
                List<Evento> lst = gere.filtrarEventosPorEstado(est);
                for (Evento e : lst) {
                    System.out.println(e.getId() + " - " + e.getTitulo() + " (" + e.getEstado() + ")");
                }
                break;
            }
            case 2: {
                LocalDateTime ini = LocalDateTime.now().minusDays(1);
                LocalDateTime fim = LocalDateTime.now().plusDays(60);
                List<Evento> lst = gere.filtrarEventosPorIntervalo(ini, fim);
                for (Evento e : lst) {
                    System.out.println(e.getId() + " - " + e.getTitulo() + " (" + e.getDataHoraInicio() + ")");
                }
                break;
            }
            case 3: {
                TipoEvento t = escolherTipo();
                List<Evento> lst = gere.filtrarEventosPorTipo(t.getId());
                for (Evento e : lst) {
                    System.out.println(e.getId() + " - " + e.getTitulo() + " [" + e.getTipo().getDescricao() + "]");
                }
                break;
            }
            default:
                System.out.println("Opção inválida.");
        }
    }

    // ---------------------- TIPOS / ORADORES / PATROCINADORES ----------------------
    private static void gerirTiposOradoresPatrocinadores() {
        System.out.println(" 1) Criar Tipo ");
        System.out.println(" 2) Listar Tipos ");
        System.out.println(" 3) Criar Orador ");
        System.out.println(" 4) Listar Oradores ");
        System.out.println(" 5) Criar Patrocinador ");
        System.out.println(" 6) Listar Patrocinadores ");

        int op = lerInt("Escolhe: ");
        switch (op) {
            case 1: {
                TipoEvento t = new TipoEvento();
                t.setDescricao(ler("Descrição do tipo: "));
                gere.criarTipoEvento(t);
                System.out.println("Tipo criado.");
                break;
            }
            case 2: {
                List<TipoEvento> ts = gere.listarTiposEvento();
                for (TipoEvento t : ts) {
                    System.out.println(t.getId() + " - " + t.getDescricao());
                }
                break;
            }
            case 3: {
                Orador o = new Orador();
                o.setNome(ler("Nome orador: "));
                o.setContacto(ler("Contacto (opcional): "));
                gere.criarOrador(o);
                System.out.println("Orador criado.");
                break;
            }
            case 4: {
                List<Orador> os = gere.listarOradores();
                for (Orador o : os) {
                    System.out.println(o.getId() + " - " + o.getNome());
                }
                break;
            }
            case 5: {
                Patrocinador p = new Patrocinador();
                p.setNome(ler("Nome patrocinador: "));
                p.setContacto(ler("Contacto (opcional): "));
                gere.criarPatrocinador(p);
                System.out.println("Patrocinador criado.");
                break;
            }
            case 6: {
                List<Patrocinador> ps = gere.listarPatrocinadores();
                for (Patrocinador p : ps) {
                    System.out.println(p.getId() + " - " + p.getNome());
                }
                break;
            }
            default:
                System.out.println("Opção inválida.");
        }
    }

    // ---------------------- INSCRIÇÕES ----------------------
    private static void inscreverParticipanteEmEvento() {
        System.out.println("== Inscrever Participante em Evento ==");
        Participante part = escolherParticipante();
        if (part == null) return;
        Evento e = escolherEvento();
        if (e == null) return;
        gere.inscreverParticipanteNoEvento(part, e);
        System.out.println("Inscrição criada.");
    }

    private static void cancelarInscricao() {
        System.out.println("== Cancelar Inscrição ==");
        Evento e = escolherEvento();
        if (e == null) return;

        List<Inscrever> inscs = gere.listarInscricoesDoEvento(e.getId());
        if (inscs.isEmpty()) {
            System.out.println("Sem inscrições para este evento.");
            return;
        }
        for (Inscrever i : inscs) {
            System.out.printf("Inscrição #%d | Participante=%s | Estado=%s%n",
                    i.getId(), i.getParticipante().getNome(), i.getEstado());
        }
        long id = lerLong("ID da inscrição a cancelar: ");
        gere.cancelarInscricao(id);
        System.out.println("Inscrição cancelada.");
    }
    
 // ---------------------- CRIAR USERS RÁPIDO ----------------------
    private static void criarUtilizadoresRapido() {
        System.out.println(" 1) Promotor ");
        System.out.println(" 2) Participante ");
        int op = lerInt("Escolhe: ");

        if (op == 1) {
            Promotor p = new Promotor();
            p.setNome(ler("Nome: "));
            p.setUsername(ler("Username: "));
            p.setPasswordHash(ler("Password (hash simples): "));
            p.setEmail(ler("Email: "));
            gere.criarPromotor(p);
            System.out.println("Promotor criado com id=" + p.getId());
        } else if (op == 2) {
            Participante u = new Participante();
            u.setNome(ler("Nome: "));
            u.setUsername(ler("Username: "));
            u.setPasswordHash(ler("Password (hash simples): "));
            u.setEmail(ler("Email: "));
            gere.criarParticipante(u);
            System.out.println("Participante criado com id=" + u.getId());
        } else {
            System.out.println("Opção inválida.");
        }
    }

    // ---------------------- HELPERS DE ESCOLHA ----------------------
    private static Promotor escolherPromotor() {
        List<Promotor> lst = gere.listarPromotores();
        if (lst.isEmpty()) throw new IllegalStateException("Sem promotores. Cria um primeiro.");
        for (Promotor p : lst) {
            System.out.println(p.getId() + " - " + p.getNome() + " (" + p.getUsername() + ")");
        }
        long id = lerLong("ID do promotor: ");
        for (Promotor p : lst) {
            if (p.getId() != null && p.getId().equals(id)) return p;
        }
        throw new IllegalStateException("Promotor não encontrado.");
    }

    private static Participante escolherParticipante() {
        List<Participante> lst = gere.listarParticipantes();
        if (lst.isEmpty()) throw new IllegalStateException("Sem participantes. Cria um primeiro.");
        for (Participante p : lst) {
            System.out.println(p.getId() + " - " + p.getNome() + " (" + p.getUsername() + ")");
        }
        long id = lerLong("ID do participante: ");
        for (Participante p : lst) {
            if (p.getId() != null && p.getId().equals(id)) return p;
        }
        throw new IllegalStateException("Participante não encontrado.");
    }

    private static Departamento escolherDepartamento() {
        List<Departamento> lst = gere.listarDepartamentos();
        for (Departamento d : lst) {
            System.out.println(d.getId() + " - " + d.getNome());
        }
        long id = lerLong("ID do departamento: ");
        for (Departamento d : lst) {
            if (d.getId() != null && d.getId().equals(id)) return d;
        }
        throw new IllegalStateException("Departamento não encontrado.");
    }

    private static Area escolherAreaDoDepartamento(Departamento d) {
        List<Area> lst = gere.listarAreasPorDepartamento(d.getId());
        for (Area a : lst) {
            System.out.println(a.getId() + " - " + a.getNome());
        }
        long id = lerLong("ID da área: ");
        for (Area a : lst) {
            if (a.getId() != null && a.getId().equals(id)) return a;
        }
        throw new IllegalStateException("Área não encontrada.");
    }

    private static TipoEvento escolherTipo() {
        List<TipoEvento> lst = gere.listarTiposEvento();
        for (TipoEvento t : lst) {
            System.out.println(t.getId() + " - " + t.getDescricao());
        }
        long id = lerLong("ID do tipo: ");
        for (TipoEvento t : lst) {
            if (t.getId() != null && t.getId().equals(id)) return t;
        }
        throw new IllegalStateException("Tipo de evento não encontrado.");
    }

    private static Pedido escolherPedidoPorEstado(PedidoEstado estado) {
        List<Pedido> lst = gere.listarPedidosPorEstado(estado);
        if (lst.isEmpty()) {
            System.out.println("Sem pedidos em " + estado + ".");
            return null;
        }
        for (Pedido p : lst) {
            System.out.println(p.getId() + " - " + p.getTitulo());
        }
        long id = lerLong("ID do pedido: ");
        for (Pedido p : lst) {
            if (p.getId() != null && p.getId().equals(id)) return p;
        }
        System.out.println("ID não encontrado.");
        return null;
    }

    private static Pedido escolherPedidoAvaliavel() {
        List<Pedido> lst = gere.listarPedidos();
        if (lst.isEmpty()) {
            System.out.println("Sem pedidos.");
            return null;
        }
        for (Pedido p : lst) {
            System.out.println(p.getId() + " - " + p.getTitulo() + " | estado=" + p.getEstado());
        }
        long id = lerLong("ID do pedido: ");
        for (Pedido p : lst) {
            if (p.getId() != null && p.getId().equals(id)) return p;
        }
        System.out.println("ID não encontrado.");
        return null;
    }

    private static Evento escolherEvento() {
        List<Evento> lst = gere.listarEventos();
        if (lst.isEmpty()) {
            System.out.println("Sem eventos.");
            return null;
        }
        for (Evento e : lst) {
            System.out.println(e.getId() + " - " + e.getTitulo());
        }
        long id = lerLong("ID do evento: ");
        for (Evento e : lst) {
            if (e.getId() != null && e.getId().equals(id)) return e;
        }
        System.out.println("ID não encontrado.");
        return null;
    }

    // ---------------------- IO UTILS ----------------------
    //para ler os dados na consola
    private static String ler(String msg) { System.out.print(msg); return in.nextLine(); }
    private static int lerInt(String msg) { return Integer.parseInt(ler(msg)); }
    private static long lerLong(String msg) { return Long.parseLong(ler(msg)); }
}
package portaleventos;
/**@author aires
 * @version 2
 */

//Esta classe serve de menu do sistema

import com.mysql.cj.Session;
import org.hibernate.query.Query;
import portaleventos.Gere;
import portaleventos.Enum.Departamento;
import portaleventos.Enum.Estado;
import portaleventos.Enum.TipoEvento;
import portaleventos.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;


import Enum.Perfil;
import Enum.Tipo;

public class Main 
{

	private static final Gere gere = new Gere();
	private static final Scanner input = new Scanner(System.in);

	// Sessão corrente (Aires)
	private static Person logado;

	public static void main(String[] args)
	{

		gere.criarAdmin();



		while(true)
		{
			//FAZER LOGIN
			Person utilizador = loginMain();

			if (utilizador == null) {
				System.out.println("Login falhou. Tente novamente.");
				continue;
			}


			//Esta a funcionar tudo, tem tudo do Crud
			if(utilizador.getPerfil()==Perfil.Admin)
			{
				int opcao=0;
				while(opcao!=4)
				{
					//MOSTRAR MENU DO ADMIN
					opcao = menuAdmin();

					switch(opcao)
					{
						case 1:
						{
							registarUtilizadorAdmin();
							break;
						}
						case 2:
						{
							eliminarUtilizadorAdmin();
							break;
						}
						case 3:
						{
							trocarTipoAdmin();
							break;
						}
						case 4:
						{
							input.nextLine();
							System.out.println("A Sair do programa");
							break;
						}
					}
					//MOSTRAR MENU DO ADMIN
					opcao = menuAdmin();
				}
			}
			else if(utilizador.getPerfil()==Perfil.Gestor)
			{
				int opcao=0;
				while(opcao!=5)
				{
					//MOSTRAR MENU DO GESTOR
					opcao = menuGestor();
					switch(opcao)
					{
						case 1:
						{
							listarPedidosGestor();
							break;
						}
						case 2:
							//nao funciona
						{
							break;
						}
						case 3:
							//nao funciona
						{
							break;
						}
						case 4:
						{
							eliminarEventoGestor();
							break;
						}
					}
					//MOSTRAR MENU DO GESTOR
					opcao = menuGestor();
				}
			}
			else if(utilizador.getPerfil()==Perfil.Participante)
			{

			}
			else if(utilizador.getPerfil()==Perfil.Promotor)
			{
				int opcao=0;
				while(opcao!=6)
				{
					//MOSTRAR MENU DO PROMOTOR
					opcao = menuPromotor();

					switch(opcao)
					{
						//case 1 e 2 a funcionar
						case 1:
						{
							criarPedidoDeAutorizacaoPromotor();
							break;
						}
						case 2:
						{
							atualizarPedidoPromotor();
							break;
						}
						case 3:

						{
							formalizarPedidoPromotor(utilizador);
							break;
						}

						case 4:
						{
							eliminarPedidoPromotor(utilizador);
							break;
						}
						case 5:
						{

						}
						case 6:
						{
							System.out.println("A sair do programa...");
							break;
						}
					}

				}
			}
			else
			{
				System.out.println("user não encontrado...");
				utilizador = loginMain();
			}
		}
	}

	public static Person loginMain()
	{
		System.out.print("Username: ");
		String username = input.next();

		System.out.print("Password: ");
		String password = input.next();
		return gere.login(username, password);
	}

	public static int menuAdmin()
	{
		System.out.println("\n--------MENU-ADMINISTRADOR--------");
		System.out.println("1. Registar Utilizador");
		System.out.println("2. Eliminar Utilizadores");
		System.out.println("3. Trocar Tipo");
		System.out.println("4. Sair do programa");
		System.out.print("Introduza o numero da opção que deseja: ");
		return input.nextInt();
	}

	public static void registarUtilizadorAdmin()
	{
		input.nextLine();
		System.out.println("Registar Utilizador...");
		System.out.print("Introduz o Username do utilizador: ");
		String username1 = input.nextLine();
		System.out.print("Introduz a password do utilizador: ");
		String password1 = input.nextLine();
		Perfil perfil = escolherPerfil();
		Tipo tipo = escolherTipo();
		gere.criarConta(username1, password1, tipo, perfil);
	}

	public static void eliminarUtilizadorAdmin()
	{
		input.nextLine();
		System.out.print("Introduz o Username do utilizador que deseja eliminar: ");
		String username1 = input.nextLine();
		gere.eliminarConta(username1);
	}

	public static void trocarTipoAdmin()
	{
		input.nextLine();
		System.out.print("Introduz o Username do utilizador que deseja trocar o tipo: ");
		String username1 = input.nextLine();
		Tipo tipo = escolherTipo();
		gere.trocarTipo(username1, tipo);
	}



	public static int menuGestor()
	{
		System.out.println("\n--------MENU-GESTOR--------");
		System.out.println("1. Lista de Pedidos");
		System.out.println("2. Avaliar Pedido");
		System.out.println("3. Meter o evento na agenda");
		System.out.println("4. Eliminar Evento");
		System.out.println("5. sair do programa");
		System.out.print("Introduza o numero da opção que deseja: ");
		return input.nextInt();
	}

	public static void listarPedidosGestor()
	{

		List<Pedido> pedidos = gere.listarTodosPedidos();
		if (pedidos.isEmpty()) {
			System.out.println("Não há pedidos registados.");
		} else {
			System.out.println("Pedidos registados:");
			for (Pedido p : pedidos) {
				System.out.println("- Titulo: "+p.getTituloEvento()+ "\n  Estado: " + p.getEstado()); // ou outro campo relevante
			}
		}
	}

	public static void eliminarEventoGestor()
	{
		List<Evento> eventos = gere.listarTodosEventos();
		System.out.println("Eventos disponíveis:");
		for (Evento e : eventos) {
			System.out.println("ID: " + e.getId() + " | Nome: " + e.getTituloEvento());
		}
		System.out.print("Digite o ID do evento que deseja eliminar: ");
		long id = input.nextInt();
		gere.eliminarEvento(id);
	}



	public static int menuPromotor()
	{
		System.out.println("\n--------MENU-PROMOTOR--------");
		System.out.println("1. Criar um pedido de autorização");
		System.out.println("2. Atualizar pedido");
		System.out.println("3. Formalizar um pedido aceite ");
		System.out.println("4. Eliminar pedido");
		System.out.println("5. Cancelar evento");
		System.out.println("6. sair do programa");
		System.out.print("Introduza o numero da opção que deseja: ");
		return input.nextInt();
	}

	public static void criarPedidoDeAutorizacaoPromotor()
	{
		input.nextLine(); // Limpa o buffer
		System.out.println("\nCriar pedido de autorização...\n");

		System.out.print("Introduz o título do evento: ");
		String tituloE = input.nextLine();

		System.out.print("Introduz o nome dos promotores do evento: ");
		String promotoresE = input.nextLine();

		System.out.print("Introduz a data do evento (AAAA-MM-DD): ");
		String dataStr = input.nextLine();
		LocalDate data = LocalDate.parse(dataStr);

		System.out.print("Introduz a duração do evento (em minutos): ");
		int duracao = input.nextInt();

		input.nextLine();
		System.out.print("Introduz o local do evento: ");
		String local = input.nextLine();

		System.out.print("Introduz a descrição do evento: ");
		String descricao = input.nextLine();

		TipoEvento tipo = escolherTipoEvento();

		Departamento departamento = escolherDepartamento();

		System.out.print("Introduz o orçamento para o evento (em euros): ");
		int orcamento = input.nextInt();

		System.out.print("Introduz a capacidade do evento: ");
		int capacidade = input.nextInt();
		input.nextLine();

		Estado estado = escolherEstado();

		gere.criarPedido(tituloE, promotoresE, data, duracao, local, descricao, tipo, departamento, orcamento, capacidade, estado);
	}

	public static void atualizarPedidoPromotor()
	{
		input.nextLine(); // Limpa o buffer
		System.out.println("\nAtualizar pedido...\n");

		System.out.print("Introduz o título do evento que deseja alterar: ");
		String tituloAntigo = input.nextLine();

		System.out.print("Introduz o novo título do evento: ");
		String tituloNovo = input.nextLine();

		System.out.print("Introduz o novo nome dos promotores do evento: ");
		String promotoresE = input.nextLine();

		System.out.print("Introduz a nova data do evento (AAAA-MM-DD): ");
		String dataStr = input.nextLine();
		LocalDate data = LocalDate.parse(dataStr);

		System.out.print("Introduz a nova duração do evento (em minutos): ");
		int duracao = input.nextInt();

		input.nextLine();
		System.out.print("Introduz o novo local do evento: ");
		String local = input.nextLine();

		System.out.print("Introduz a nova descrição do evento: ");
		String descricao = input.nextLine();

		TipoEvento tipo = escolherTipoEvento();

		Departamento departamento = escolherDepartamento();

		System.out.print("Introduz o novo orçamento para o evento (em euros): ");
		int orcamento = input.nextInt();

		System.out.print("Introduz a nova capacidade do evento: ");
		int capacidade = input.nextInt();
		input.nextLine();

		Estado estado = escolherEstado();

		gere.atualizarPedido(tituloAntigo, tituloNovo, promotoresE, data, duracao, local, descricao, tipo, departamento, orcamento, capacidade, estado);
	}

	public static void formalizarPedidoPromotor(Person utilizador)
	{
		input.nextLine(); // Limpa o buffer
		System.out.println("\nFormalizar um pedido autorizado...\n");

		List<Pedido> pedidos = gere.listarTodosPedidosAutorizadosDoPromotor(utilizador.getId());

		if (pedidos == null)
		{
			System.out.println("Não foram efetuados pedidos autorizados...");
			return;
		}

		System.out.println("Pedidos disponíveis para formalizar:");
		for (Pedido p : pedidos) {
			System.out.println("ID: " + p.getId() + " | Nome: " + p.getTituloEvento());
		}
		System.out.print("Digite o ID do pedido de evento que deseja eliminar: ");
		long id = input.nextInt();

		//RETORNA O ID
		Pedido pedido = gere.procurarPedidoPorId(id);



		// Recolher dados adicionais
		System.out.print("Introduz a hora de início do evento (HH:mm): ");
		String horaI = input.nextLine();
		LocalTime horaInicio = LocalTime.parse(horaI);

		System.out.print("Introduz a hora do fim do evento (HH:mm): ");
		String horaF = input.nextLine();
		LocalTime horaFim = LocalTime.parse(horaF);

		System.out.print("Introduz o orçamento concreto para o evento (em euros): ");
		float orcamento = input.nextFloat();
		input.nextLine(); // Limpa o buffer

		System.out.print("Introduz o preço da entrada para o evento (em euros): ");
		float precoEntrada = input.nextFloat();
		input.nextLine(); // Limpa o buffer

		System.out.print("Introduz o nome do organizador do evento: ");
		String nomeOrganizador = input.nextLine();

		System.out.print("Introduz o email do organizador do evento: ");
		String emailOrganizador = input.nextLine();

		System.out.print("Introduz o contacto do organizador do evento: ");
		int contactoOrganizador = input.nextInt();
		input.nextLine(); // Limpa o buffer

		//EVENTO CRIADO/FORMALIZADO
		gere.criarEvento(pedido, horaInicio, horaFim, orcamento, precoEntrada, nomeOrganizador, emailOrganizador, contactoOrganizador);

	}

	public static void eliminarPedidoPromotor(Person utilizador)
	{
		System.out.println("\nEliminar pedido autorizado...\n");

		List<Pedido> pedidos = gere.listarTodosPedidosDoPromotor(utilizador.getId());

		if (pedidos == null)
		{
			System.out.println("Não foram efetuados pedidos...");
			return;
		}

		System.out.println("Pedidos disponíveis:");
		for (Pedido p : pedidos) {
			System.out.println("ID: " + p.getId() + " | Nome: " + p.getTituloEvento());
		}
		System.out.print("Digite o ID do pedido de evento que deseja eliminar: ");
		long id = input.nextInt();
		gere.eliminarPedido(id);
	}



	public static Perfil escolherPerfil() {
	    Scanner scanner = new Scanner(System.in);
	    Perfil perfilSelecionado = null;

	    System.out.println("Escolha o perfil:");
	    Perfil[] perfis = Perfil.values();

	    for (int i = 0; i < perfis.length; i++) {
	        System.out.println((i + 1) + ". " + perfis[i]);
	    }

	    while (perfilSelecionado == null) {
	        System.out.print("Digite o número correspondente: ");
	        String input = scanner.nextLine();

	        try {
	            int escolha = Integer.parseInt(input);
	            if (escolha >= 1 && escolha <= perfis.length) {
	                perfilSelecionado = perfis[escolha - 1];
	            } else {
	                System.out.println("Número inválido. Tente novamente.");
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("Entrada inválida. Digite um número.");
	        }
	    }

	    return perfilSelecionado;
	}
	
	public static Tipo escolherTipo() {
	    Scanner scanner = new Scanner(System.in);
	    Tipo tipoSelecionado = null;

	    System.out.println("Escolha o tipo:");
	    Tipo[] tipos = Tipo.values();

	    for (int i = 0; i < tipos.length; i++) {
	        System.out.println((i + 1) + ". " + tipos[i]);
	    }

	    while (tipoSelecionado == null) {
	        System.out.print("Digite o número correspondente: ");
	        String input = scanner.nextLine();

	        try {
	            int escolha = Integer.parseInt(input);
	            if (escolha >= 1 && escolha <= tipos.length) {
	                tipoSelecionado = tipos[escolha - 1];
	            } else {
	                System.out.println("Número inválido. Tente novamente.");
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("Entrada inválida. Digite um número.");
	        }
	    }

	    return tipoSelecionado;
	}
	
	public static TipoEvento escolherTipoEvento() {
	    Scanner scanner = new Scanner(System.in);
	    TipoEvento tipoEventoSelecionado = null;

	    System.out.println("Escolha o tipo de evento:");
	    TipoEvento[] tipos = TipoEvento.values();

	    for (int i = 0; i < tipos.length; i++) {
	        System.out.println((i + 1) + ". " + tipos[i]);
	    }

	    while (tipoEventoSelecionado == null) {
	        System.out.print("Digite o número correspondente: ");
	        String input = scanner.nextLine();

	        try {
	            int escolha = Integer.parseInt(input);
	            if (escolha >= 1 && escolha <= tipos.length) {
	                tipoEventoSelecionado = tipos[escolha - 1];
	            } else {
	                System.out.println("Número inválido. Tente novamente.");
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("Entrada inválida. Digite um número.");
	        }
	    }

	    return tipoEventoSelecionado;
	}

	public static Departamento escolherDepartamento() {
	    Scanner scanner = new Scanner(System.in);
	    Departamento departamentoSelecionado = null;

	    System.out.println("Escolha o departamento:");
	    Departamento[] departamentos = Departamento.values();

	    for (int i = 0; i < departamentos.length; i++) {
	        System.out.println((i + 1) + ". " + departamentos[i] + " - " + departamentos[i].getDepartamento());
	    }

	    while (departamentoSelecionado == null) {
	        System.out.print("Digite o número correspondente: ");
	        String input = scanner.nextLine();

	        try {
	            int escolha = Integer.parseInt(input);
	            if (escolha >= 1 && escolha <= departamentos.length) {
	                departamentoSelecionado = departamentos[escolha - 1];
	            } else {
	                System.out.println("Número inválido. Tente novamente.");
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("Entrada inválida. Digite um número.");
	        }
	    }

	    return departamentoSelecionado;
	}

	public static Estado escolherEstado() {
	    Scanner scanner = new Scanner(System.in);
	    Estado estadoSelecionado = null;

	    System.out.println("Escolha o estado do pedido:");
	    Estado[] estados = Estado.values();

	    for (int i = 0; i < estados.length; i++) {
	        System.out.println((i + 1) + ". " + estados[i]);
	    }

	    while (estadoSelecionado == null) {
	        System.out.print("Digite o número correspondente: ");
	        String input = scanner.nextLine();

	        try {
	            int escolha = Integer.parseInt(input);
	            if (escolha >= 1 && escolha <= estados.length) {
	                estadoSelecionado = estados[escolha - 1];
	            } else {
	                System.out.println("Número inválido. Tente novamente.");
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("Entrada inválida. Digite um número.");
	        }
	    }

	    return estadoSelecionado;
	}


}




























/*
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
*/
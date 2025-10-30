package portaleventos;
/**@author aires
 * @version 1
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
		Tipo tipo = Tipo.Ativo;
		gere.criarConta(username1, password1, tipo, perfil);
	}

	public static void eliminarUtilizadorAdmin()
	{
        List<Person> persons = gere.listaUtilizadores();
        System.out.println("Utilizadores disponíveis:");
        for (Person p : persons) {
            System.out.println("ID: " + p.getId() + " | Nome: " + p.getUsername());
        }
        System.out.print("Digite o ID do utilizador que deseja eliminar: ");
        long id = input.nextLong();
        gere.eliminarConta(id);
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
				System.out.println("-Id: "+p.getId()+ "\n Titulo: "+p.getTituloEvento()+ "\n  Estado: " + p.getEstado()); // ou outro campo relevante
			}
		}
	}

    public static void avaliarPedidosGestor()
    {
        listarPedidosGestor();
        System.out.println("Escolha um pedido para avaliar! (pelo id)");
        long pedidoId = input.nextLong();
        Pedido pedido = gere.procurarPedidoPorId(pedidoId);


    }

    public static void meterEventoAgendaGestor()
    {

    }

	public static void eliminarEventoGestor()
	{
		List<Evento> eventos = gere.listarTodosEventos();
		System.out.println("Eventos disponíveis:");
		for (Evento e : eventos) {
			System.out.println("ID: " + e.getId() + " | Nome: " + e.getTituloEvento());
		}
		System.out.print("Digite o ID do evento que deseja eliminar: ");
		long id = input.nextLong();
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

		Estado estado = Estado.PENDENTE;

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





















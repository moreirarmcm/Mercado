package view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.Carrinho;
import model.Produto;
import util.Utilitario;

/**
 * "Moreira's Shop"
 * 
 * Sistema de compra para um mercado fictício.
 * 
 * O programa permite cadastrar produtos, listar, comprar (inserindo
 * no carrinho de compras) e vizualizar carrinhos.
 * Os produtos são inseridos e consultados de um banco de dados mySQL
 * 
 * 
 * @author Renan Moreira
 * 
 */
public class Mercado {
	
	private static Scanner sc = new Scanner (System.in);
	private static List<Carrinho> carrinho = new ArrayList<>();
	static double valorTotal = 0;

	//private static List<> carrinhos = new ArrayList<>();
	static Connection conexao = null;
	static String listar = "SELECT codigo, nome, preco FROM produtos";
	static String inserir = "INSERT INTO produtos (nome, preco) VALUES (?,?)";
	
	public static void main (String [] args) throws SQLException {
			
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexao = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/mercado", "renan","1234");
		
			System.out.println("=========================================================================================");
			System.out.println("================================ Bem Vindo (a) ==========================================");
			System.out.println("=============================== Moreira's Shop ==========================================");
			Mercado.menu();
			
		}catch (ClassNotFoundException e) {
			System.out.println("Não foi possível se conectar com o banco de dados 'Mercado'\n" + e.getMessage());
		}finally {
			if (conexao != null) {
				conexao.close();
			}
		}
	}
	
	private static void menu() throws SQLException {
		System.out.println("=========================================================================================");
		System.out.println("\n");
		System.out.println("1 - Listar produtos");
		System.out.println("2 - Cadastrar produtos");
		System.out.println("3 - Comprar produtos");
		System.out.println("4 - Vizualizar o carrinho");
		System.out.println("5 - Sair");
		int escolha_usuario = 0;
		try{
			escolha_usuario = Integer.parseInt(sc.nextLine());
			}catch (Exception e) {
			System.out.println("Escolha incorreta, escolha uma opção correta.");
			Utilitario.Pausar(3);
			Mercado.menu();
		}
		switch (escolha_usuario) {
		case 1 :
			System.out.println("================= Lista de produtos ============================");
			Mercado.ListarProdutos();
			break;
		case 2:
			System.out.println("================= Cadastro de Produtos ============================");
			Mercado.CadastrarProdutos();
			break;
		case 3:
			Mercado.ComprarProdutos();
			break;
		case 4:
			Mercado.VisualizarCarrinho();
			break;
		case 5:
			System.out.println("A Moreira's Shop agradece sua presenïça. Volte sempre!!");
			Utilitario.Pausar(5);
			System.exit(0);
		default:
			System.out.println("Opï¿½ï¿½o invï¿½lida, escolha outra:");
			Utilitario.Pausar(2);
			Mercado.menu();
		}
	}
	
	public static void ListarProdutos() throws SQLException {
		Statement lista_produtos = conexao.createStatement();
		ResultSet resultado = lista_produtos.executeQuery(listar); //redundante.
		while (resultado.next()) {
			System.out.println("Produto: " + resultado.getString("nome") + "  |  Preço: " + resultado.getFloat("preco"));
			//Utilitario.Pausar(1);
		}
		System.out.println("Listagem completa.");
		Utilitario.Pausar(3);
		Mercado.menu();
	}
	
	public static void CadastrarProdutos() throws SQLException {
		String nome = null;
		float preco = 0f;
		System.out.println("");
		System.out.print("Qual o nome do produto?: ");
		nome = sc.nextLine();
		System.out.println("");
		System.out.print("Qual o Valor do produto '" + nome + "' ?:");
		System.out.println("");
		try {
			preco = Float.parseFloat(sc.nextLine());
		}catch (Exception e) {
			System.out.println("Valor incorreto!\n\nDigite um valor numérico.");
			Utilitario.Pausar(3);
			Mercado.menu();
		} 
		
		PreparedStatement adiciona_produtos = null;
		try {
			adiciona_produtos = conexao.prepareStatement(inserir);
			adiciona_produtos.setString(1, nome);
			adiciona_produtos.setFloat(2, preco);
			adiciona_produtos.execute();
			System.out.println("O produto: " + nome + " foi cadastrado com sucesso!\n\n");

		} catch (SQLException eX) {
			System.out.println("Erro de query. \n" + eX.getMessage());
		}
		//Utilitario.Pausar(3);
		System.out.println("Deseja cadastrar um novo produto?\n<1 para 'SIM'    0 para 'NÃO'");
		int escolha;
		try {
			escolha = Integer.parseInt(sc.nextLine()); // com nextInt o scanner printa o nome e preço de uma vez - revisar.
			if (escolha == 1) {
				CadastrarProdutos();
			}else {	
				Mercado.menu();
			}	
		}catch (Exception e) {
			System.out.println("É 1 ou 0!");
			Mercado.menu();
		}
		
	}	

	
	public static void ComprarProdutos() throws SQLException {
		ResultSet resultado = conexao.createStatement().executeQuery(listar);
		if (!resultado.next()) {
			System.out.println("Não há produtos em estoque.");
			Utilitario.Pausar(3);
			Mercado.menu();
		}else {
			int escolha_compra = 0;
			boolean tem = false;
			System.out.println();
			System.out.println("Produtos disponíveis no mercado: ");
			resultado = conexao.createStatement().executeQuery(listar);
			while (resultado.next()) {
				System.out.println("Código : " + resultado.getInt("codigo") + "   |   Produto: " + resultado.getString("nome") + "  |  Preço: " + resultado.getFloat("preco"));
			}
			System.out.println("Informe o código do produto: ");
			try {
				escolha_compra = Integer.parseInt(sc.nextLine());
			}catch (Exception e) {
				System.out.println("Desculpe, nï¿½o foi possï¿½vel realizar a compra...");
				Utilitario.Pausar(2);
				Mercado.menu();
			}
			resultado = conexao.createStatement().executeQuery(listar);
			while (resultado.next()) {
				int codigo = resultado.getInt("codigo");
				if ( codigo == escolha_compra) {
					int qtd = 0;
					try {
						qtd = Mercado.carrinho.get(codigo).getQuantidade();
						Mercado.carrinho.get(codigo).setQuantidade(qtd+1);
					}catch (Exception e) {
						Mercado.carrinho.add(new Carrinho(
								resultado.getInt("codigo"), 
								resultado.getString("nome"),
								resultado.getFloat("preco"),
								qtd+1));
						System.out.println("O produto '" + resultado.getString("nome") + "' foi adicionado com sucesso!");
						tem = true;
					}
					if (tem) {
						System.out.println("Deseja adicionar outros produtos?\n1 para 'SIM'.\n0 - para 'NÃO'.");
						int escolha2 = Integer.parseInt(sc.nextLine());
						if (escolha2 == 1) {
							resultado = conexao.createStatement().executeQuery(listar);
							Mercado.ComprarProdutos();
							
						}else {
							System.out.println("Por favor, aguarde enquanto fechamos o seu pedido...");
							Utilitario.Pausar(4);
							FecharPedido();
						}
					}else {
						System.out.println("Produto com código informado não encontrado");
						Utilitario.Pausar(2);
						Mercado.menu();
					}
				}			
			}
				
		}
	}

	private static void FecharPedido() throws SQLException {
		System.out.println("Produtos do Carrinho");
		System.out.println("---------------------");
		carrinho.forEach(c -> {
			int qtd = c.getQuantidade();
			valorTotal += qtd * c.getPreco();
			
			System.out.println(c.getNome() + " - Quantidade: " + qtd);
			System.out.println("-----------");
		});
		System.out.println("Sua fatura é: " + Utilitario.doubleParaString((double) valorTotal));
		Mercado.carrinho.clear();
		valorTotal = 0;
		Mercado.menu();
		
	}
	
	public static void VisualizarCarrinho() throws SQLException {
		if (Mercado.carrinho.size() == 0 ) {
			System.out.println("Ainda nï¿½o existem produtos no carrinho");
			Utilitario.Pausar(2);
			Mercado.menu();
		}else {
			System.out.println("Produtos no carrinho:");
			carrinho.forEach(System.out::println);
			Utilitario.Pausar(5);
			Mercado.menu();
		}
	}
}



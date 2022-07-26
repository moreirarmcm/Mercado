package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.Produto;
import utilitario.Utils;

public class Mercado {
	private static Scanner sc = new Scanner (System.in);
	private static List<Produto> produtos;
	private static Map<Produto, Integer> carrinho;
	
	public static void main (String [] args) {
		produtos = new ArrayList<Produto>();
		carrinho = new HashMap<Produto, Integer>();
		produtos.add(new Produto("Sabão", 25.90));
		produtos.add(new Produto("Escova", 9.90));
		produtos.add(new Produto("Amaciante de roupa", 25.90));
		produtos.add(new Produto("Filé de peixe", 50));
		produtos.add(new Produto("Bolo de amêndoas", 30.50));
		
		System.out.println("=========================================================================================");
		System.out.println("================================ Bem Vindo (a) ==========================================");
		System.out.println("=============================== Moreira's Shop ==========================================");
		Menu();
		
	}
	
	private static void Menu() {
	
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
			System.out.println("Opção escolha incorreta, escolha uma opção correta.");
			Mercado.Menu();
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
		case 4:
			Mercado.VisualizarCarrinho();
			break;
		case 3:
			Mercado.ComprarProdutos();
			break;
		case 5:
			System.out.println("A Moreira's Shop agradece sua presença. Volte sempre!!");
			Utils.Pausar(5);
			System.exit(0);
		default:
			System.out.println("Opção inválida, escolha outra:");
			Utils.Pausar(2);
			Mercado.Menu();
		}
	}
	
	public static void ListarProdutos() {
		produtos.forEach(System.out::println);
		System.out.println("\n");
		Mercado.Menu();
	}
	
	public static void CadastrarProdutos() {
		String nome = null;
		double preco = 0;
		System.out.println("");
		System.out.print("Qual o nome do produto?: ");
		nome = sc.nextLine();
		System.out.println("");
		System.out.print("Qual o Valor do produto '" + nome + "' ?:");
		System.out.println("");
		try {
			preco = Double.parseDouble(sc.nextLine());
		}catch (Exception e) {
			System.out.println("Valor incorreto!\n\nDigite um valor numérico.");
			Utils.Pausar(2);
			Mercado.Menu();
		}
		produtos.add(new Produto(nome, preco));
		System.out.println("O produto: " + nome + " foi cadastrado com sucesso!");
		Utils.Pausar(2);
	
		if (sc.nextInt() == 1) {
			CadastrarProdutos();
		}else {	Mercado.Menu();	}
	}
	public static void ComprarProdutos() {
		if (Mercado.produtos.size() == 0) {
			
		}else {
			int escolha_compra = 0;
			boolean tem = false;
			System.out.println();
			System.out.println("Produtos disponíveis no mercado: ");
			produtos.forEach(System.out::println);
			System.out.println("Informe o código do produto:");

			try {
				escolha_compra = Integer.parseInt(sc.nextLine());
			}catch (Exception e) {
				System.out.println("Desculpe, não foi possível realizar a compra...");
				Utils.Pausar(2);
				Mercado.Menu();
			}
			for (Produto p : Mercado.produtos) {
				tem = false;
				if (p.getCodigo() == escolha_compra) {
					int qtd = 0;
					try {
						qtd = Mercado.carrinho.get(p);
						Mercado.carrinho.put(p, qtd+1);
					}catch (Exception e) {
						Mercado.carrinho.put(p, 1);
					}
					System.out.println("O produto" + p.getNome() + " foi adicionado com sucesso!");
					tem = true;
				}
				if (tem) {
					System.out.println("Deseja adicionar outros produtos?\n1 - para Sim.\n0 - para Não.");
					int op = Integer.parseInt(Mercado.sc.nextLine());
					if (op == 1) {
						Mercado.ComprarProdutos();
					}else {
						System.out.println("Por favor, aguarde enquanto fechamos o seu pedido...");
						FecharPedido();
					}
				}else {
					System.out.println("Não foi encontrado o produto com o código informado.");
					Utils.Pausar(2);
					Mercado.Menu();
				}
			}
				
		}
	}
	private static void FecharPedido() {
		Double valorTotal = 0.0;
		System.out.println("Produtos do Carrinho");
		System.out.println("---------------------");
		for (Produto p : Mercado.carrinho.keySet()) {
			int qtd = Mercado.carrinho.get(p);
			valorTotal +=p.getPreco() * qtd;
			System.out.println(p);
			System.out.println("Quantidade: " + qtd);
			System.out.println("-----------");
		}
		System.out.println("Sua fatura é: " + Utils.doubleParaString(valorTotal));
		Mercado.carrinho.clear();
		Mercado.Menu();
	}
	
	public static void VisualizarCarrinho() {
		if (Mercado.carrinho.size() == 0 ) {
			System.out.println("Ainda não existem produtos no carrinho");
			Utils.Pausar(2);
		}else {
			System.out.println("Produtos no carrinho:");
			for (Produto p : carrinho.keySet()){
				System.out.println("Produto: " + p + "| preço: " + Mercado.carrinho.get(p));
			}
		}
	}
}

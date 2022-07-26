package model;

public class Produto {
	private static int contador = 1;
	private int codigo;
	private String nome;
	private double preco;
		
	public Produto(String nome, double preco) {
		this.codigo = Produto.contador;
		Produto.contador++;
		this.nome = nome;
		this.preco = preco;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	public int getCodigo() {
		return codigo;
	}
	@Override
	public String toString() {
		return "{" + this.getCodigo() + ", "+ this.getNome() + ", " + utilitario.Utils.doubleParaString(this.getPreco()) + "}";
	}
}

package model;

public class Carrinho {
	private int codigo;
	private String nome;
	private float preco;
	private int quantidade;
	
	public Carrinho(int codigo, String nome, float preco, int quantidade) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.preco = preco;
		this.quantidade = quantidade;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
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

	public void setPreco(float preco) {
		this.preco = preco;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public String toString() {
		return "{" + this.getCodigo() + ", "+ this.getNome() + ", " + util.Utilitario.doubleParaString((double) this.getPreco()) + "}";
	}

	
}

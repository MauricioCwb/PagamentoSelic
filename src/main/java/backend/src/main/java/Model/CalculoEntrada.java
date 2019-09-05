package Model;

import java.util.List;

public class CalculoEntrada {
	private List<Produto> produto;
	private List<CondicaoPagamento> condicaoPagamento;
	
	public List<Produto> getProduto() {
		return produto;
	}
	public void setProduto(List<Produto> produto) {
		this.produto = produto;
	}
	public List<CondicaoPagamento> getCondicaoPagamento() {
		return condicaoPagamento;
	}
	public void setCondicaoPagamento(List<CondicaoPagamento> condicaoPagamento) {
		this.condicaoPagamento = condicaoPagamento;
	}
	
	
}

package DTO;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Entrada")
public class CalculoEntrada {
	
	@ApiModelProperty(value = "Produto")
	private List<Produto> produto;
	
	@ApiModelProperty(value = "Condicao de Pagamento")
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

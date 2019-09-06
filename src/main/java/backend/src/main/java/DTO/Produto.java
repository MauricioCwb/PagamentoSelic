package DTO;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Prduto")
public class Produto {
	
	@ApiModelProperty(value = "Codigo do Produto")
	private long codigo;
	
	@ApiModelProperty(value = "Nome do Produto")
	private String nome;
	
	@ApiModelProperty(value = "Valor do Produto")
	private BigDecimal valor;
	
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}

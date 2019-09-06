package DTO;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Saída")
public class CalculoSaida {
	
	@ApiModelProperty(value = "Parcela")
	private long parcela;
	
	@ApiModelProperty(value = "Valor")
	private BigDecimal valor;
	
	@ApiModelProperty(value = "Taxa de Juros")
	private BigDecimal taxaJuros;
	
	public long getParcela() {
		return parcela;
	}
	public void setParcela(long parcela) {
		this.parcela = parcela;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public BigDecimal getTaxaJuros() {
		return taxaJuros;
	}
	public void setTaxaJuros(BigDecimal taxaJuros) {
		this.taxaJuros = taxaJuros;
	}
}

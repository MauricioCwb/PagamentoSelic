package DTO;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Condicao de Pagamento")
public class CondicaoPagamento {
	
	@ApiModelProperty(value = "Valor da Entrada")
	private BigDecimal valorEntrada;
	
	@ApiModelProperty(value = "Quantidade de Parcelas")
	private long qtdeParcelas;
	
	public BigDecimal getValorEntrada() {
		return valorEntrada;
	}
	public void setValorEntrada(BigDecimal valorEntrada) {
		this.valorEntrada = valorEntrada;
	}
	public long getQtdeParcelas() {
		return qtdeParcelas;
	}
	public void setQtdeParcelas(long qtdeParcelas) {
		this.qtdeParcelas = qtdeParcelas;
	}
}

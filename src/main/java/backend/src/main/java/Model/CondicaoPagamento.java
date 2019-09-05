package Model;

import java.math.BigDecimal;

public class CondicaoPagamento {
	private BigDecimal valorEntrada;
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

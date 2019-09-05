package Model;

import java.math.BigDecimal;

public class CalculoSaida {
	private long parcela;
	private BigDecimal valor;
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

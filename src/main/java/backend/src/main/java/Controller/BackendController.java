package Controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import Model.CalculoEntrada;
import Model.CalculoSaida;

@RestController
public class BackendController {

	@RequestMapping(value = "/calculoSelic", method = RequestMethod.POST)
	public ResponseEntity<List<CalculoSaida>> updateWithMultipleObjects(@RequestBody CalculoEntrada calculoEntrada) {

		List<CalculoSaida> calculosSaida = new ArrayList<CalculoSaida>();
		if(calculoEntrada == null || calculoEntrada.getProduto() == null || calculoEntrada.getCondicaoPagamento() == null ||
				calculoEntrada.getProduto().size() == 0 || calculoEntrada.getCondicaoPagamento().size() == 0) {
			return new ResponseEntity<List<CalculoSaida>>(calculosSaida, HttpStatus.BAD_REQUEST);
		}
		
		BigDecimal taxaSelic = new BigDecimal(1.15);
		BigDecimal valor = calculoEntrada.getProduto().get(0).getValor();
		BigDecimal valorEntrada = calculoEntrada.getCondicaoPagamento().get(0).getValorEntrada();
		long qtdParcelas = calculoEntrada.getCondicaoPagamento().get(0).getQtdeParcelas();
		BigDecimal valorParcela = new BigDecimal(0);
		
		if(valor.compareTo(new BigDecimal(0)) <= 0 || qtdParcelas <= 0L || valorEntrada.compareTo(new BigDecimal(0)) < 0) {
			return new ResponseEntity<List<CalculoSaida>>(calculosSaida, HttpStatus.BAD_REQUEST);
		}
		
		valor = valor.subtract(valorEntrada);
		for(long noParcela = 1; noParcela <= qtdParcelas; noParcela++) {
			valor = valor.multiply(new BigDecimal(1).add(taxaSelic.divide(new BigDecimal(100))));
		}
		valorParcela = valor.divide(new BigDecimal(qtdParcelas));
		
		for(long noParcela = 1; noParcela <= qtdParcelas; noParcela++) {
			CalculoSaida calculoSaida = new CalculoSaida();
			calculoSaida.setParcela(noParcela);
			calculoSaida.setValor(valorParcela.setScale(2, RoundingMode.HALF_EVEN));
			calculoSaida.setTaxaJuros(taxaSelic.setScale(2, RoundingMode.HALF_EVEN));
			calculosSaida.add(calculoSaida);
		}

	    return new ResponseEntity<List<CalculoSaida>>(calculosSaida, HttpStatus.OK);
	}

    @RequestMapping("/")
    public String ping() {
        return "Sorry! My answers are limited. You have to ask the right questions!";
    }

    @RequestMapping("/test")
    public String test() {
    	return "Testing 1, 2, 3!";
    }
}
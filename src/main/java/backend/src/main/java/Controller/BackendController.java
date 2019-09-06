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

import DTO.CalculoEntrada;
import DTO.CalculoSaida;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value="Calculo Selic API")
@RestController
public class BackendController {

	@ApiOperation(value="Calcula as parcelas a serem pagas", notes="Consulta síncrona.")
	@RequestMapping(value = "/calculoSelic", method = RequestMethod.POST)
	public ResponseEntity<List<CalculoSaida>> updateWithMultipleObjects(
			@ApiParam(value="exemplo {\r\n" + 
					"    \"produto\": [\r\n" + 
					"        {\r\n" + 
					"            \"codigo\": 123,\r\n" + 
					"            \"nome\": \"Nome do Produto\",\r\n" + 
					"            \"valor\": 100\r\n" + 
					"        }\r\n" + 
					"    ],\r\n" + 
					"    \"condicaoPagamento\": [\r\n" + 
					"        {\r\n" + 
					"            \"valorEntrada\": 0,\r\n" + 
					"            \"qtdeParcelas\": 10\r\n" + 
					"        }\r\n" + 
					"    ]\r\n" + 
					"}") 
			@RequestBody CalculoEntrada calculoEntrada) {

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

	@ApiOperation(value="Verifica se o servico está ativo")
    @RequestMapping("/")
    public String ping() {
        return "Sorry! My answers are limited. You have to ask the right questions!";
    }

	@ApiOperation(value="Teste")
    @RequestMapping("/test")
    public String test() {
    	return "Testing 1, 2, 3!";
    }
}
package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import DTO.Product;
import DTO.Sheet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value="Calculo Selic API")
@RestController
public class BackendController {

	// Variáveis locais como não foi definido o uso de um Banco de dados
	private List<Product> products = null;
	private Queue sheetsQueue = new LinkedList<Sheet>();
	private List<Sheet> sheets = new ArrayList<Sheet>();

	////////////////////////////////////////////
	// Serviços
	////////////////////////////////////////////
	
	@ApiOperation(value="Administra produtos", notes="Carregamento assíncrono.")
	@RequestMapping(value = "/produtos/carregar", method = RequestMethod.POST)
	public void carregarPlanilha(
			@ApiParam(value="<pathCompleto>\nomeDaPlanilha") 
			@RequestBody String pathNomePlanilha) {

		if(products == null) {
			products = new ArrayList<Product>();
			iniciarThread();
		}
		Sheet planilha = new Sheet();
		planilha.setName(pathNomePlanilha);
		planilha.setStatus("Aguardando");
		sheetsQueue.add(planilha);
		sheets.add(planilha);
	}

	@ApiOperation(value="Administra produtos", notes="Consulta a fila de processamento.")
	@RequestMapping(value = "/produtos/consultar", method = RequestMethod.GET)
	public ResponseEntity<List<Sheet>> consultarFila() {
		return new ResponseEntity<List<Sheet>>(sheets, HttpStatus.OK);
	}

	@ApiOperation(value="Administra produtos", notes="Consulta a produtos.")
	@RequestMapping(value = "/produtos/produto", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> consultarProdutos() {
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	@ApiOperation(value="Administra produtos", notes="Consulta um produto por im")
	@RequestMapping(value = "/produtos/produto/{im}", method = RequestMethod.GET)
	public ResponseEntity<Product> consultarProduto(
			@PathVariable("im") long im) {
		
		for(Product product : products) {
			if(product.getIm() == im) {
				return new ResponseEntity<Product>(product, HttpStatus.OK);
			}
		}
		return new ResponseEntity<Product>(new Product(), HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value="Administra produtos", notes="Atualiza um produto por im")
	@RequestMapping(value = "/produtos/produto/{im}", method = RequestMethod.PUT)
	public ResponseEntity<Product> atualizarProduto(
			@PathVariable("im") long im,
			@RequestBody Product produto) {
		
		int contador = -1;
		for(Product product : products) {
			contador ++;
			if(product.getIm() == im) {
				products.set(contador, produto);
				return new ResponseEntity<Product>(produto, HttpStatus.OK);
			}
		}
		return new ResponseEntity<Product>(produto, HttpStatus.BAD_REQUEST);
	}

	@ApiOperation(value="Administra produtos", notes="Deleta um produto por im")
	@RequestMapping(value = "/produtos/produto/{im}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletarProduto(@PathVariable("im") long im) {
		for(Product product : products) {
			if(product.getIm() == im) {
				products.remove(product);
				return new ResponseEntity<Void>(HttpStatus.OK);
			}
		}
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}
	
	////////////////////////////////////////
	// Processos
	////////////////////////////////////////
	
	public void iniciarThread() {
		new Thread() {
			@Override
			public void run() {
				while(true) {
					if(sheetsQueue.peek() == null) {
						try {
							System.out.print(".");
							Thread.sleep(1000 * 5);
						} catch (InterruptedException e) {
						}
					} else {
						processarPlanilha();
					}
				}
			}
		}.start(); 		
	}

	private void processarPlanilha() {
		Sheet planilhaProcessar = (Sheet) sheetsQueue.remove();

		System.out.println("Processando " + planilhaProcessar.getName());
		List<String> retornos = lerPlanilha(planilhaProcessar.getName());

		int posicao = sheets.lastIndexOf(planilhaProcessar);
		if(posicao >= 0) {
			Sheet planilha = sheets.get(posicao);
			planilha.setStatus(retornos.size() == 0 ? "Processada com Sucesso" : "Processada com Erros");
			planilha.setDataHora(Calendar.getInstance());
			planilha.setErros(retornos);
		}
	}

	@SuppressWarnings("deprecation")
	private List<String> lerPlanilha(String pathNomePlanilha) {

		List<String> retornos = new ArrayList<String>();
		try {
			FileInputStream file = new FileInputStream(new File(pathNomePlanilha));

			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			String celula = "", categoria = "";
			int contador = 0;

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext())
			{
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				Product produto = null;

				while (cellIterator.hasNext()) 	
				{
					Cell cell = cellIterator.next();
					celula = cell.getCellType() == HSSFCell.CELL_TYPE_STRING ? cell.getStringCellValue() : ""+(long) cell.getNumericCellValue();

					if(!"".equals(celula)) {
						if("Próxima".equalsIgnoreCase(categoria)) {
							categoria = celula;
							contador = 0;
						} else if("Category".equalsIgnoreCase(celula)) {
							categoria = "Próxima";
						} else if("lm name free_shipping description price".indexOf(celula) < 0) {
							if(contador == 0) {
								produto = new Product();
								produto.setCategory(Long.parseLong(categoria));
							}
							switch (contador) 
							{
							case 0: produto.setIm((long) Long.parseLong(celula));			break;
							case 1: produto.setName(celula);								break;
							case 2:	produto.setFreeShipping((long) Long.parseLong(celula));	break;
							case 3: produto.setDescription(celula);							break;
							case 4:	
								produto.setPrice(new BigDecimal(cell.getStringCellValue()));
								if(products.contains(produto)) {
									retornos.add("O IM "+ produto.getIm() + "-" + produto.getName() + " já existe no cadastro e não será inserido!");
									System.out.println("O IM "+ produto.getIm() + "-" + produto.getName() + " já existe no cadastro e não será inserido!");
								} else {
									products.add(produto);
									System.out.println("Incluído " + produto.getName());
								}
								contador = -1;
								break;
							}
							contador++;
						}
					}
				}
			}
			file.close();
		} catch (Exception e) {
			retornos.add("Erro de leitura da planilha " + pathNomePlanilha + " !");
			System.out.println(retornos);
		}

		return retornos;
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
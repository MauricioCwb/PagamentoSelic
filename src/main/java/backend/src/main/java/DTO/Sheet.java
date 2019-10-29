package DTO;

import java.util.Calendar;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Planilha")
public class Sheet {
	
	@ApiModelProperty(value = "Nome da planilha")
	private String name;
	
	@ApiModelProperty(value = "Status da planilha")
	private String status;

	@ApiModelProperty(value = "Data/Hora processamento da planilha")
	private Calendar dataHora;
	
	@ApiModelProperty(value = "Lista de erros no processamento da planilha")
	private List<String> errors;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Calendar getDataHora() {
		return dataHora;
	}

	public void setDataHora(Calendar dataHora) {
		this.dataHora = dataHora;
	}

	public List<String> getErros() {
		return errors;
	}

	public void setErros(List<String> erros) {
		this.errors = erros;
	}
	
}

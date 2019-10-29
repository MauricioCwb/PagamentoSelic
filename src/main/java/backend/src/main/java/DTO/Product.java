package DTO;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Produto")
public class Product {
	
	@ApiModelProperty(value = "Categoria do Produto")
	private long category;
	
	@ApiModelProperty(value = "Codigo do Produto")
	private long im;
	
	@ApiModelProperty(value = "Nome do Produto")
	private String name;
	
	@ApiModelProperty(value = "Envio grátis")
	private long freeShipping;
	
	@ApiModelProperty(value = "Descrição do Produto")
	private String description;
	
	@ApiModelProperty(value = "Preço do Produto")
	private BigDecimal price;

	public long getCategory() {
		return category;
	}

	public void setCategory(long category) {
		this.category = category;
	}

	public long getIm() {
		return im;
	}

	public void setIm(long im) {
		this.im = im;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getFreeShipping() {
		return freeShipping;
	}

	public void setFreeShipping(long freeShipping) {
		this.freeShipping = freeShipping;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (im ^ (im >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (im != other.im)
			return false;
		return true;
	}
	
}

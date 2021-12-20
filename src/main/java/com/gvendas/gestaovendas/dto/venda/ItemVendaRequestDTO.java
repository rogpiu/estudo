package com.gvendas.gestaovendas.dto.venda;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Itens da venda requisicao DTO")
public class ItemVendaRequestDTO {

	@ApiModelProperty(name = "Codigo do Produto")
	@NotNull(message = "Codigo do Produto")
	private Long codigoProduto;
	
	@ApiModelProperty(name = "Quantidade")
	@NotNull(message = "Quantidade")
	@Min(value = 1, message = "Quantidade")
	private Integer quantidade;
	
	@ApiModelProperty(name = "Preco Vendido")
	@NotNull(message = "Preco Vendido")
	private BigDecimal precoVendido;

	public Long getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(Long codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPrecoVendido() {
		return precoVendido;
	}

	public void setPrecoVendido(BigDecimal precoVendido) {
		this.precoVendido = precoVendido;
	}
	
	
	
	
}

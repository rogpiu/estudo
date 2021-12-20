package com.gvendas.gestaovendas.dto.produto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.gvendas.gestaovendas.entidades.Categoria;
import com.gvendas.gestaovendas.entidades.Produto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Produto Requisicao DTO")
public class ProdutoRequestDTO {

	@ApiModelProperty(name = "Descrição")
	@NotBlank(message = "Descrição")
	@Length(min = 3, max = 100, message = "Descrição")
	private String descricao;

	@ApiModelProperty(name = "Quantidade")
	@NotNull(message = "Quantidade")
	private Integer quantidade;

	@ApiModelProperty(name = "Preço de Custo")
	@NotNull(message = "Preco Custo")
	private BigDecimal precoCurto;

	@ApiModelProperty(name = "Preço de Venda")
	@NotNull(message = "Preco Venda")
	private BigDecimal precoVenda;

	@ApiModelProperty(name = "Observação")
	@Length(max = 500, message = "Observação")
	private String observacao;
	
	
	public Produto converterParaEntidade(Long codigoCategoria) {
		return new Produto(descricao, quantidade, precoCurto, precoVenda, observacao, new Categoria(codigoCategoria));
	}

	public Produto converterParaEntidade(Long codigo, Long codigoCategoria) {
		return new Produto(codigo, descricao, quantidade, precoCurto, precoVenda, observacao, new Categoria(codigoCategoria));
	}

	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public Integer getQuantidade() {
		return quantidade;
	}


	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}


	public BigDecimal getPrecoCurto() {
		return precoCurto;
	}


	public void setPrecoCurto(BigDecimal precoCurto) {
		this.precoCurto = precoCurto;
	}


	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}


	public void setPrecoVenda(BigDecimal precoVenda) {
		this.precoVenda = precoVenda;
	}


	public String getObservacao() {
		return observacao;
	}


	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
		
	
}

package com.gvendas.gestaovendas.dto.produto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.gvendas.gestaovendas.dto.categoria.CategoriaResponseDTO;
import com.gvendas.gestaovendas.entidades.Categoria;
import com.gvendas.gestaovendas.entidades.Produto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Produto Retorno DTO")
public class ProdutoResponseDTO {

	@ApiModelProperty(name = "Código")
	private Long codigo;

	@ApiModelProperty(name = "Descrição")
	private String descricao;

	@ApiModelProperty(name = "Quantidade")
	private Integer quantidade;

	@ApiModelProperty(name = "Preço de Custo")
	private BigDecimal precoCurto;

	@ApiModelProperty(name = "Preço de Venda")
	private BigDecimal precoVenda;

	@ApiModelProperty(name = "Observação")
	private String observacao;

	@ApiModelProperty(name = "Categoria")
	private CategoriaResponseDTO categoria;

	public ProdutoResponseDTO(Long codigo, String descricao, Integer quantidade, BigDecimal precoCurto,
			BigDecimal precoVenda, String observacao, CategoriaResponseDTO categoria) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.quantidade = quantidade;
		this.precoCurto = precoCurto;
		this.precoVenda = precoVenda;
		this.observacao = observacao;
		this.categoria = categoria;
	}

	public static ProdutoResponseDTO converterParaProdutoDTO(Produto produto) {
		return new ProdutoResponseDTO(produto.getCodigo(), produto.getDescricao(), produto.getQuantidade(),
				produto.getPrecoCurto(), produto.getPrecoVenda(), produto.getObservacao(),
				CategoriaResponseDTO.converterParaCategoriaDTO(produto.getCategoria()));
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
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

	public CategoriaResponseDTO getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaResponseDTO categoria) {
		this.categoria = categoria;
	}

}

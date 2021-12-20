package com.gvendas.gestaovendas.dto.venda;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Cliente venda retorno DTO")
public class ClienteVendaResponseDTO {

	@ApiModelProperty(name = "Nome Cliente")
	private String nome;
	
	@ApiModelProperty(name = "Venda")
	private List<VendaResponseDTO> vendaResponseDTOs;

	public ClienteVendaResponseDTO(String nome, List<VendaResponseDTO> vendaResponseDTOs) {
		this.nome = nome;
		this.vendaResponseDTOs = vendaResponseDTOs;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<VendaResponseDTO> getVendaResponseDTOs() {
		return vendaResponseDTOs;
	}

	public void setVendaResponseDTOs(List<VendaResponseDTO> vendaResponseDTOs) {
		this.vendaResponseDTOs = vendaResponseDTOs;
	}
	
	
}

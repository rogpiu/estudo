package com.gvendas.gestaovendas.dto.venda;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Venda Requisicao DTO")
public class VendaRequestDTO {

	@ApiModelProperty(value = "Data")
	@NotNull(message = "Data")
	private LocalDate date;
	
	@ApiModelProperty(value = "Itens da venda")
	@NotNull(message = "Itens de Venda")
	@Valid
	private List<ItemVendaRequestDTO> itemVendaDto;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<ItemVendaRequestDTO> getItemVendaDto() {
		return itemVendaDto;
	}

	public void setItemVendaDto(List<ItemVendaRequestDTO> itemVendaDto) {
		this.itemVendaDto = itemVendaDto;
	}
	
	
}

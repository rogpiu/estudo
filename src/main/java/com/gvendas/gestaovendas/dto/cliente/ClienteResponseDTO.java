package com.gvendas.gestaovendas.dto.cliente;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.gvendas.gestaovendas.entidades.Cliente;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Cliente Response DTO")
public class ClienteResponseDTO {

	@ApiModelProperty(value = "Código")
	private Long codigo;

	@ApiModelProperty(value = "Nome")
	private String nome;

	@ApiModelProperty(value = "Telefone")
	private String telefone;

	@ApiModelProperty(value = "Ativo")
	private Boolean ativo;

	@ApiModelProperty(value = "Endereco")
	private EnderecoResponseDTO enderecoResponseDTO;

	public ClienteResponseDTO(Long codigo, String nome, String telefone, Boolean ativo,
			EnderecoResponseDTO enderecoResponseDTO) {
		this.codigo = codigo;
		this.nome = nome;
		this.telefone = telefone;
		this.ativo = ativo;
		this.enderecoResponseDTO = enderecoResponseDTO;
	}

	public static ClienteResponseDTO convertoParaClienteDTO(Cliente cliente) {
		EnderecoResponseDTO enderecoResponseDTO2 = new EnderecoResponseDTO(cliente.getEndereco().getLogradouro(),
				cliente.getEndereco().getNumero(), cliente.getEndereco().getComplemento(),
				cliente.getEndereco().getBairro(), cliente.getEndereco().getCep(), cliente.getEndereco().getCidade(),
				cliente.getEndereco().getEstado());
		return new ClienteResponseDTO(cliente.getCodigo(), cliente.getNome(), cliente.getTelefone(), cliente.getAtivo(),
				enderecoResponseDTO2);
	}

	public EnderecoResponseDTO getEnderecoResponseDTO() {
		return enderecoResponseDTO;
	}

	public void setEnderecoResponseDTO(EnderecoResponseDTO enderecoResponseDTO) {
		this.enderecoResponseDTO = enderecoResponseDTO;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

}

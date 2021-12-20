package com.gvendas.gestaovendas.controlador;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gvendas.gestaovendas.dto.produto.ProdutoResponseDTO;
import com.gvendas.gestaovendas.dto.venda.ClienteVendaResponseDTO;
import com.gvendas.gestaovendas.dto.venda.VendaRequestDTO;
import com.gvendas.gestaovendas.servico.ProdutoServico;
import com.gvendas.gestaovendas.servico.VendaServico;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Venda")
@RestController
@RequestMapping("/venda")
public class VendaControlador {
	
	
	@Autowired
	private VendaServico vendaServico;
	
	@ApiOperation(value = "Listar venda por cliente", nickname = "ListarPorCliente")
	@GetMapping("/cliente/{codigo}")
	public ResponseEntity<ClienteVendaResponseDTO>  listarVendasCliente(@PathVariable Long codigo) {
		return   ResponseEntity.ok(vendaServico.listaVendaPorCliente(codigo));
	}

	@ApiOperation(value = "Listar venda por cliente", nickname = "ListarPorCliente")
	@GetMapping("/{codigoVenda}")
	public ResponseEntity<ClienteVendaResponseDTO>  listarPorCodigoVenda(@PathVariable Long codigoVenda) {
		return   ResponseEntity.ok(vendaServico.listarVendaPorCodigo(codigoVenda));
	}
	
	@ApiOperation(value = "Salvar venda por cliente", nickname = "SalvarPorCliente")
	@PostMapping("/cliente/{codigo}")
	public ResponseEntity<ClienteVendaResponseDTO>  salvar(@PathVariable Long codigo, @Valid  @RequestBody VendaRequestDTO vendaRequestDto) {
		return   ResponseEntity.status(HttpStatus.CREATED).body(vendaServico.salvar(codigo, vendaRequestDto));
	}
	
	@ApiOperation(value = "Atualizar venda por cliente", nickname = "AtualizarVendaPorCliente")
	@PutMapping("/{codigoVenda}/cliente/{codigoCliente}")
	public ResponseEntity<ClienteVendaResponseDTO>  atualizar(@PathVariable Long codigoCliente, 
			                                                  @PathVariable Long codigoVenda, 
			                                                  @Valid  @RequestBody VendaRequestDTO vendaRequestDto) {
		return   ResponseEntity.ok(vendaServico.atualizar(codigoVenda, codigoCliente, vendaRequestDto));
	}
	
	@ApiOperation(value = "Deletar Venda", nickname = "DeletarVenda")
	@DeleteMapping("/{codigoVenda}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable long codigoVenda) {
		vendaServico.deletar(codigoVenda);
	}
}

package com.gvendas.gestaovendas.servico;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gvendas.gestaovendas.dto.venda.ClienteVendaResponseDTO;
import com.gvendas.gestaovendas.dto.venda.ItemVendaRequestDTO;
import com.gvendas.gestaovendas.dto.venda.ItemVendaResponseDTO;
import com.gvendas.gestaovendas.dto.venda.VendaRequestDTO;
import com.gvendas.gestaovendas.dto.venda.VendaResponseDTO;
import com.gvendas.gestaovendas.entidades.Cliente;
import com.gvendas.gestaovendas.entidades.ItemVenda;
import com.gvendas.gestaovendas.entidades.Produto;
import com.gvendas.gestaovendas.entidades.Venda;
import com.gvendas.gestaovendas.excecao.RegraNegocioException;
import com.gvendas.gestaovendas.repositorio.ItemVendaRepositorio;
import com.gvendas.gestaovendas.repositorio.ProdutoRepositorio;
import com.gvendas.gestaovendas.repositorio.VendaRepositorio;

@Service
public class VendaServico extends AbstractVendaServico {

	private ClienteServico clienteServico;
	private VendaRepositorio vendaRepositorio;
	private ItemVendaRepositorio itemVendaRepositorio;
	private ProdutoServico produtoServico;

	@Autowired
	public VendaServico(ClienteServico clienteServico, VendaRepositorio vendaRepositorio,
			ItemVendaRepositorio itemVendaRepositorio, ProdutoServico produtoServico) {
		this.clienteServico = clienteServico;
		this.vendaRepositorio = vendaRepositorio;
		this.itemVendaRepositorio = itemVendaRepositorio;
		this.produtoServico = produtoServico;
	}

	public ClienteVendaResponseDTO listaVendaPorCliente(Long codigoCliente) {
		Cliente clienteEncontrado = validarClienteExiste(codigoCliente);
		List<VendaResponseDTO> vendaResponseDtoList = vendaRepositorio.findByClienteCodigo(codigoCliente).stream().map(
				venda -> criandoVendaResponseDTO(venda, itemVendaRepositorio.findByVendaPorCodigo(venda.getCodigo())))
				.collect(Collectors.toList());
		return new ClienteVendaResponseDTO(clienteEncontrado.getNome(), vendaResponseDtoList);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public ClienteVendaResponseDTO salvar(Long codigoCliente, VendaRequestDTO vendaRequestDto) {
		Cliente clienteEncontrado = validarClienteExiste(codigoCliente);
		validarProdutoExiste(vendaRequestDto.getItemVendaDto());
		Venda vendaSalva = salvarVenda(clienteEncontrado, vendaRequestDto);
		return retornaClienteVendaResponseDto(vendaSalva,
				itemVendaRepositorio.findByVendaPorCodigo(vendaSalva.getCodigo()));

	}

	public void deletar(Long codigoVenda) {
		validarVendaExiste(codigoVenda);
		List<ItemVenda> itensVenda = itemVendaRepositorio.findByVendaPorCodigo(codigoVenda);
		validarProdutoExisteEDevolverEstoque(itensVenda);
		itemVendaRepositorio.deleteAll(itensVenda);
		vendaRepositorio.deleteById(codigoVenda);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	private void validarProdutoExisteEDevolverEstoque(List<ItemVenda> itensVenda) {
		itensVenda.forEach(item -> {
			Produto produto = produtoServico.validarProdutoExiste(item.getProduto().getCodigo());
			produto.setQuantidade(produto.getQuantidade() + item.getQuantidade());
			produtoServico.atualizaProduto(produto);
		});
	}

	public ClienteVendaResponseDTO atualizar(Long codigoVenda, Long codigoCliente, VendaRequestDTO vendaRequestDTO) {
		validarVendaExiste(codigoVenda);
		Cliente clienteEncontrado = validarClienteExiste(codigoCliente);
		List<ItemVenda> itensVenda = itemVendaRepositorio.findByVendaPorCodigo(codigoVenda);
		validarProdutoExisteEDevolverEstoque(itensVenda);
		validarProdutoExiste(vendaRequestDTO.getItemVendaDto());
		itemVendaRepositorio.deleteAll(itensVenda);
		Venda atualizarVenda = atualizarVenda(codigoVenda, clienteEncontrado, vendaRequestDTO);
		return retornaClienteVendaResponseDto(atualizarVenda, itemVendaRepositorio.findByVendaPorCodigo(codigoVenda));
	}
	
	private void validarProdutoExiste(List<ItemVendaRequestDTO> itemVendaDto) {
		itemVendaDto.forEach(item -> {
			Produto produto = produtoServico.validarProdutoExiste(item.getCodigoProduto());
			produtoServico.atualizaQuantidade(produto, item.getQuantidade());
		});
	}

	private Venda salvarVenda(Cliente cliente, VendaRequestDTO vendaDto) {
		Venda vendaSalva = vendaRepositorio.save(new Venda(vendaDto.getDate(), cliente));
		vendaDto.getItemVendaDto().stream().map(itemVendaDto -> criandoItemVenda(itemVendaDto, vendaSalva))
				.forEach(itemVendaRepositorio::save);
		return vendaSalva;
	}
	
	private Venda atualizarVenda(Long codigoVenda, Cliente cliente, VendaRequestDTO vendaDto) {
		Venda vendaSalva = vendaRepositorio.save(new Venda(codigoVenda, vendaDto.getDate(), cliente));
		vendaDto.getItemVendaDto().stream().map(itemVendaDto -> criandoItemVenda(itemVendaDto, vendaSalva))
				.forEach(itemVendaRepositorio::save);
		return vendaSalva;
	}

	public ClienteVendaResponseDTO listarVendaPorCodigo(Long codigoVenda) {
		Venda venda = validarVendaExiste(codigoVenda);
		return retornaClienteVendaResponseDto(venda, itemVendaRepositorio.findByVendaPorCodigo(venda.getCodigo()));

	}

	private Venda validarVendaExiste(Long codigoVenda) {
		Optional<Venda> vendaEncontrada = vendaRepositorio.findById(codigoVenda);
		if (vendaEncontrada.isEmpty()) {
			throw new RegraNegocioException(String.format("A venda de código %s informado não existe", codigoVenda));
		}
		return vendaEncontrada.get();

	}

	private Cliente validarClienteExiste(Long codigoCliente) {
		Optional<Cliente> clienteEncontrado = clienteServico.buscarPorId(codigoCliente);
		if (clienteEncontrado.isEmpty()) {
			throw new RegraNegocioException(String.format("O cliente de código %s informado não exite", codigoCliente));
		}
		return clienteEncontrado.get();
	}

}

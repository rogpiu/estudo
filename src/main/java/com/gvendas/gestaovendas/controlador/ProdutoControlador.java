package com.gvendas.gestaovendas.controlador;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gvendas.gestaovendas.dto.produto.ProdutoRequestDTO;
import com.gvendas.gestaovendas.dto.produto.ProdutoResponseDTO;
import com.gvendas.gestaovendas.entidades.Categoria;
import com.gvendas.gestaovendas.entidades.Produto;
import com.gvendas.gestaovendas.servico.CategoriaServico;
import com.gvendas.gestaovendas.servico.ProdutoServico;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Produto")
@RestController
@RequestMapping("/produto")
public class ProdutoControlador {

	@Autowired
	private ProdutoServico produtoServico;

	@ApiOperation(value = "Listar", nickname = "ListarTodas")
	@GetMapping
	public List<ProdutoResponseDTO> listarTodas() {
		return produtoServico.listarTodas().stream().map(produto -> ProdutoResponseDTO.converterParaProdutoDTO(produto))
				.collect(Collectors.toList());
	}

	@ApiOperation(value = "Listar por Codigo")
	@GetMapping("/{codigo}")
	public ResponseEntity<ProdutoResponseDTO> buscaPorId(@PathVariable Long codigo) {
		Optional<Produto> produto = produtoServico.buscarPorId(codigo);
		return produto.isPresent() ? ResponseEntity.ok(ProdutoResponseDTO.converterParaProdutoDTO(produto.get()))
				: ResponseEntity.notFound().build();
	}

	@ApiOperation(value = "Listar por Codigo Categoria")
	@GetMapping("/categoria/{codigo}")
	public List<ProdutoResponseDTO> buscaPorCodigoCategoria(@PathVariable Long codigo) {
		return produtoServico.listarTodasCategorias(codigo).stream()
				.map(produto -> ProdutoResponseDTO.converterParaProdutoDTO(produto))
				.collect(Collectors.toList());
	}

	@ApiOperation(value = "Listar por Codigo Categoria e Codigo Produto")
	@GetMapping("/{codigo}/categoria/{codigoCategoria}")
	public ResponseEntity<ProdutoResponseDTO> buscaPorCodigoProdutoCategoria(@PathVariable Long codigo,
			@PathVariable Long codigoCategoria) {
		Optional<Produto> produto = produtoServico.buscarPorCodigo(codigo, codigoCategoria);
		return produto.isPresent() ? ResponseEntity.ok(ProdutoResponseDTO.converterParaProdutoDTO(produto.get())) : ResponseEntity.notFound().build();

	}

	@ApiOperation(value = "Atualizar", nickname = "atualizarProduto")
	@PutMapping("/{codigo}/categoria/{codigoCategoria}")
	public ResponseEntity<ProdutoResponseDTO> atualizar(@Valid @RequestBody ProdutoRequestDTO produto, @PathVariable Long codigo,
			@PathVariable Long codigoCategoria) {
		Produto produtoAtualizado = produtoServico.atualizar(codigoCategoria, codigo, produto.converterParaEntidade(codigo, codigoCategoria));
		return ResponseEntity.ok(ProdutoResponseDTO.converterParaProdutoDTO(produtoAtualizado));
	}

	@ApiOperation(value = "Deletar", nickname = "deletarProduto")
	@DeleteMapping("/{codigoProduto}/categoria/{codigoCategoria}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long codigoProduto, @PathVariable Long codigoCategoria) {
		produtoServico.deletar(codigoProduto, codigoCategoria);
	}

	@ApiOperation(value = "Salvar Produto")
	@PostMapping("/categoria/{codigoCategoria}")
	public ResponseEntity<ProdutoResponseDTO> salvar(@PathVariable Long codigoCategoria, @Valid @RequestBody ProdutoRequestDTO produto) {
		Produto produtoSalvo = produtoServico.salvar(codigoCategoria, produto.converterParaEntidade(codigoCategoria));
		return ResponseEntity.status(HttpStatus.CREATED).body(ProdutoResponseDTO.converterParaProdutoDTO(produtoSalvo));
	}

}

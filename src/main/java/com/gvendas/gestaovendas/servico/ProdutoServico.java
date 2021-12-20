package com.gvendas.gestaovendas.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gvendas.gestaovendas.entidades.Categoria;
import com.gvendas.gestaovendas.entidades.ItemVenda;
import com.gvendas.gestaovendas.entidades.Produto;
import com.gvendas.gestaovendas.excecao.RegraNegocioException;
import com.gvendas.gestaovendas.repositorio.ProdutoRepositorio;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Service
public class ProdutoServico {

	@Autowired
	private ProdutoRepositorio produtoRepositorio;
	
	@Autowired
	private CategoriaServico categoriaServico;
	
	public List<Produto> listarTodas() {
		return produtoRepositorio.findAll();
	}

	public Optional<Produto> buscarPorId(Long id) {
		return produtoRepositorio.findById(id);
	}
	
	public List<Produto> listarTodasCategorias(Long codigoCategoria){
		return produtoRepositorio.findByCategoriaCodigo(codigoCategoria);
	}
	
	public Optional<Produto> buscarPorCodigo(Long codigo, long codigoCategoria){
		return produtoRepositorio.buscarPorCodigo(codigo, codigoCategoria);
	}
	
	public Produto salvar(Long codigoCategoria, Produto produto) {
		validarCategoriaProdutoExite(codigoCategoria);
		validarProdutoDuplicado(produto);
		return produtoRepositorio.save(produto);
	}
	
	public Produto atualizar(Long codigoCategoria, Long codigoProduto, Produto produto) {
		Produto produtoSalvar = validarProdutoExiste(codigoProduto, codigoCategoria);
		validarProdutoDuplicado(produto);
		validarCategoriaProdutoExite(codigoCategoria);
		BeanUtils.copyProperties(produto, produtoSalvar, "codigo");
		return produtoRepositorio.save(produtoSalvar);
		
	}
	

	public void atualizaProduto(Produto produto) {
		produtoRepositorio.save(produto);
	}
	
	public void atualizaQuantidade(Produto produto, Integer quantidadeItem) {
		validarQuantidadeProdutoExiste(produto, quantidadeItem);
		produto.setQuantidade(produto.getQuantidade() - quantidadeItem);
		produtoRepositorio.save(produto);
	}
	
	private void validarQuantidadeProdutoExiste(Produto produto, Integer quantidadeItem) {
		if (!(produto.getQuantidade() >= quantidadeItem)) {
			throw new RegraNegocioException(String.format("Nao existe a quantidade %s para o produto %s", 
					quantidadeItem,produto.getQuantidade()));
		}
	}
	
	public void deletar(Long codigoProduto, Long codigoCategoria) {
		validarProdutoExiste(codigoProduto, codigoCategoria);
		produtoRepositorio.deleteById(codigoProduto);
	}
	
	protected Produto validarProdutoExiste(Long codigoProduto) {
		Optional<Produto> produtoEncontrado = produtoRepositorio.findById(codigoProduto);
		if(produtoEncontrado.isEmpty()) {
			throw new RegraNegocioException(String.format("Produto de codigo %s nao encontrado", codigoProduto));
		}
		return produtoEncontrado.get();
		
	}
	
	private Produto validarProdutoExiste(Long codigoProduto, Long codigoCategoria) {
		Optional<Produto> produtoEncontrado = buscarPorCodigo(codigoProduto, codigoCategoria);
		if(produtoEncontrado.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		return produtoEncontrado.get();
		
	}

	private void validarProdutoDuplicado(Produto produto) {
		
		Optional<Produto> produtoEncontrado = produtoRepositorio.findByCategoriaCodigoAndDescricao(produto.getCategoria().getCodigo(), produto.getDescricao());
		if(produtoEncontrado.isPresent() && produtoEncontrado.get().getCodigo() != produto.getCodigo()) {
			throw new RegraNegocioException(String.format("O produto %s ja esta cadastrado", produto.getDescricao()));
		}
	}
	
	private void validarCategoriaProdutoExite(Long codigoCategoria) {
		if(codigoCategoria == null) {
			throw new RegraNegocioException("A categoria nao pode ser nula");
		}
		
		if(categoriaServico.buscarPorId(codigoCategoria).isEmpty()) {
			throw new RegraNegocioException(String.format("A categoria de codigo %s nao existe no cadastro", codigoCategoria));
		}
	}
}

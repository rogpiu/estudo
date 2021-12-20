package com.gvendas.gestaovendas.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gvendas.gestaovendas.entidades.Categoria;
import com.gvendas.gestaovendas.entidades.Cliente;
import com.gvendas.gestaovendas.excecao.RegraNegocioException;
import com.gvendas.gestaovendas.repositorio.ClienteRepositorio;

@Service
public class ClienteServico {

	@Autowired
	private ClienteRepositorio clienteRepositorio;

	public List<Cliente> listarTodas() {
		return clienteRepositorio.findAll();
	}

	public Optional<Cliente> buscarPorId(Long id) {
		return clienteRepositorio.findById(id);
	}

	public Cliente salvar(Cliente cliente) {
		validarClienteDuplicado(cliente);
		return clienteRepositorio.save(cliente);
	}
	
	public void deletar(Long codigo) {
		clienteRepositorio.deleteById(codigo);
	}
	
	public Cliente atualizar(Long codigo, Cliente cliente) {
		Cliente clienteEncontrado = verificaClienteExiste(codigo);
		validarClienteDuplicado(cliente);
		BeanUtils.copyProperties(cliente, clienteEncontrado, "codigo");
		return clienteRepositorio.save(clienteEncontrado);
	}
	
	public Cliente verificaClienteExiste(Long codigo) {
		Optional<Cliente> cliente = buscarPorId(codigo);
		if(cliente.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		return cliente.get();
	}

	private void validarClienteDuplicado(Cliente cliente) {
		Cliente clientePorNome = clienteRepositorio.findByNome(cliente.getNome());
		if (clientePorNome != null && clientePorNome.getCodigo() != cliente.getCodigo()) {
			throw new RegraNegocioException(
					String.format("O cliente %s ja esta cadastrado", cliente.getNome().toUpperCase()));
		}
	}

}

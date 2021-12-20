package com.gvendas.gestaovendas.controlador;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

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

import com.gvendas.gestaovendas.dto.categoria.CategoriaRequestDTO;
import com.gvendas.gestaovendas.dto.categoria.CategoriaResponseDTO;
import com.gvendas.gestaovendas.dto.cliente.ClienteRequestDTO;
import com.gvendas.gestaovendas.dto.cliente.ClienteResponseDTO;
import com.gvendas.gestaovendas.entidades.Categoria;
import com.gvendas.gestaovendas.entidades.Cliente;
import com.gvendas.gestaovendas.servico.ClienteServico;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Cliente")
@RestController
@RequestMapping("/cliente")
public class ClienteControlador {

	@Autowired
	private ClienteServico clienteServico;

	@ApiOperation(value = "Listar", nickname = "ListarTodosClientes")
	@GetMapping
	public List<ClienteResponseDTO> listarTodas() {
		return clienteServico.listarTodas().stream().map(cliente -> ClienteResponseDTO.convertoParaClienteDTO(cliente))
				.collect(Collectors.toList());
	}

	@ApiOperation(value = "Listar por Codigo", nickname = "ListarClienteId")
	@GetMapping("/{codigo}")
	public ResponseEntity<ClienteResponseDTO> buscaPorId(@PathVariable Long codigo) {
		Optional<Cliente> cliente = clienteServico.buscarPorId(codigo);
		return cliente.isPresent() ? ResponseEntity.ok(ClienteResponseDTO.convertoParaClienteDTO(cliente.get()))
				: ResponseEntity.notFound().build();

	}

	@ApiOperation(value = "Salvar")
	@PostMapping
	public ResponseEntity<ClienteResponseDTO> salvar(@Valid @RequestBody ClienteRequestDTO clienteDTO) {
		Cliente clienteSalvo = clienteServico.salvar(clienteDTO.converterParaEntidade());
		return ResponseEntity.status(HttpStatus.CREATED).body(ClienteResponseDTO.convertoParaClienteDTO(clienteSalvo));
	}
	
	@ApiOperation(value = "Deletar")
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long codigo) {
		clienteServico.deletar(codigo);
	}
	
	
	@ApiOperation(value = "Atualizar")
	@PutMapping("/{codigo}")
	public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Long codigo, @Valid @RequestBody ClienteRequestDTO clienteDTO)  {
		Cliente clienteAtualizado = clienteServico.atualizar(codigo, clienteDTO.converterParaEntidade(codigo));
		return ResponseEntity.ok(ClienteResponseDTO.convertoParaClienteDTO(clienteAtualizado));
	}

}

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gvendas.gestaovendas.dto.categoria.CategoriaRequestDTO;
import com.gvendas.gestaovendas.dto.categoria.CategoriaResponseDTO;
import com.gvendas.gestaovendas.entidades.Categoria;
import com.gvendas.gestaovendas.servico.CategoriaServico;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Categoria")
@RestController
@RequestMapping("/categoria")
public class CategoriaControlador {

	@Autowired
	private CategoriaServico categoriaServico;

	@ApiOperation(value = "Listar")
	@GetMapping
	public List<CategoriaResponseDTO> listarTodas(){
		return categoriaServico.listarTodas().stream().map(categoria -> CategoriaResponseDTO.converterParaCategoriaDTO(categoria) )
	                         .collect(Collectors.toList());
	}

	@ApiOperation(value = "Listar por Codigo")
	@GetMapping("/{codigo}")
	public ResponseEntity<CategoriaResponseDTO> buscaPorId(@PathVariable Long codigo) {
		Optional<Categoria> categoria = categoriaServico.buscarPorId(codigo);
		return categoria.isPresent() ? ResponseEntity.ok(CategoriaResponseDTO.converterParaCategoriaDTO(categoria.get())) : ResponseEntity.notFound().build();

	}

	@ApiOperation(value = "Deletar")
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long codigo) {
		categoriaServico.deletar(codigo);
	}

	@ApiOperation(value = "Salvar")
	@PostMapping
	public ResponseEntity<CategoriaResponseDTO> salvar(@Valid @RequestBody CategoriaRequestDTO categoriaDTO) {
		Categoria categoriaSalva = categoriaServico.salvar(categoriaDTO.converterParaEntidade());
		return ResponseEntity.status(HttpStatus.CREATED).body(CategoriaResponseDTO.converterParaCategoriaDTO(categoriaSalva));
	}

	@ApiOperation(value = "Atualizar")
	@PutMapping("/{codigo}")
	public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable Long codigo, @Valid @RequestBody CategoriaRequestDTO categoriaDTO) {
		return ResponseEntity.ok(CategoriaResponseDTO.converterParaCategoriaDTO(categoriaServico.atualizar(codigo, categoriaDTO.converterParaEntidade(codigo))));
}
 /****  teste ****/

package com.gvendas.gestaovendas.excecao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.Pattern;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GestaoVendasExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String CONSTANT_VALIDARION_NOT_BLANK = "NotBlank";
	private static final String CONSTANT_VALIDARION_NOT_NULL = "NotNull";
	private static final String CONSTANT_VALIDARION_LENGTH = "Length";
	private static final String CONSTANT_VALIDARION_PATTERN = "Pattern";
	private static final String CONSTANT_VALIDARION_MIN = "Min";

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Erro> erros = gerarListaErros(ex.getBindingResult());

		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public  ResponseEntity<Object>  handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request){
		String msgUsuario = "Recurso nao encontrado";
		String msgDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(msgUsuario, msgDesenvolvedor));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public  ResponseEntity<Object>  handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request){
		String msgUsuario = "Recurso nao encontrado";
		String msgDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(msgUsuario, msgDesenvolvedor));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(RegraNegocioException.class)
	public ResponseEntity<Object> handleRegraNegocioException(RegraNegocioException ex, WebRequest request){
		String msgUsuario = ex.getMessage();
		String msgDesenvolvedor = ex.getMessage();
		List<Erro> erros = Arrays.asList(new Erro(msgUsuario, msgDesenvolvedor));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	private List<Erro> gerarListaErros(BindingResult bindingResult) {
		List<Erro> erros = new ArrayList<Erro>();
		bindingResult.getFieldErrors().forEach(fieldError -> {
			String msgUsuario = tratarMensagemErroParaUsuario(fieldError);
			String msgDesenvolvedor = fieldError.toString();
			erros.add(new Erro(msgUsuario, msgDesenvolvedor));
		});
		return erros;

	}

	private String tratarMensagemErroParaUsuario(FieldError fieldError) {
		if (fieldError.getCode().equals(CONSTANT_VALIDARION_NOT_BLANK)) {
			return fieldError.getDefaultMessage().concat(" é obrigatorio");
		}
		if (fieldError.getCode().equals(CONSTANT_VALIDARION_LENGTH)) {
             return fieldError.getDefaultMessage().concat(String.format(" deve ter entre %s e %s caracteres",
				fieldError.getArguments()[2], fieldError.getArguments()[1]));
		}
		if (fieldError.getCode().equals(CONSTANT_VALIDARION_NOT_NULL)) {
			return fieldError.getDefaultMessage().concat(" é obrigatorio");
		}
		if (fieldError.getCode().equals(CONSTANT_VALIDARION_PATTERN)) {
			return fieldError.getDefaultMessage().concat(" formato inválido");
		}
		if (fieldError.getCode().equals(CONSTANT_VALIDARION_MIN)) {
			return fieldError.getDefaultMessage().concat(String.format(" deve ser maior ou igual a %s", 
					fieldError.getArguments()[1]));
		}
		return fieldError.toString();
	}
}

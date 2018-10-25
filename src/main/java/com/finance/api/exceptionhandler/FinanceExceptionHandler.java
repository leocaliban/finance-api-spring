package com.finance.api.exceptionhandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.finance.api.exceptionhandler.util.ErroMessage;

@ControllerAdvice
public class FinanceExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		// Gera duas mensagens, uma para o cliente e uma técnica para o desenvolvedor
		String mensagemUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.getCause().toString();

		ErroMessage erro = new ErroMessage(mensagemUsuario, mensagemDesenvolvedor);
		return handleExceptionInternal(ex, erro, headers, HttpStatus.BAD_REQUEST, request);
	}

	/**
	 * Faz a captura da exceção causada por argumentos que não puderam ser validados
	 * Salva em uma lista para apresentar as mensagens para todos os elementos
	 * inválidos
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<ErroMessage> erros = criarListaDeErros(ex.getBindingResult());

		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	private List<ErroMessage> criarListaDeErros(BindingResult bindingResult) {
		List<ErroMessage> erros = new ArrayList<>();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String mensagemDesenvolvedor = fieldError.toString();
			erros.add(new ErroMessage(mensagemUsuario, mensagemDesenvolvedor));
		}
		return erros;
	}

}

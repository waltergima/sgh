package br.org.ccb.sgh.controller.handler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.org.ccb.sgh.exception.StandardError;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	private static final String WITH_ID = "with id";

	@ExceptionHandler({ ObjectNotFoundException.class })
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Registro com id " + e.getIdentifier() +  " não encontrado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler({ EntityNotFoundException.class })
	public ResponseEntity<StandardError> objectNotFound(EntityNotFoundException e, HttpServletRequest request) {
		String[] split = splitNotFoundMessage(e);
		
		return this.objectNotFound(new ObjectNotFoundException(split[1].trim(), split[0].substring(14).trim()), request);
	}
	
	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<StandardError> objectNotFound(EmptyResultDataAccessException e, HttpServletRequest request) {
		String[] split = splitNotFoundMessage(e);
		
		return this.objectNotFound(new ObjectNotFoundException(split[1].substring(0, 3).trim(), split[0].substring(8).trim()), request);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityViolationException e, HttpServletRequest request) {
		
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Não é possível remover este registro pois está sendo utilizado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		String errors = "";
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			errors = errors.concat(String.format("%s: %s ", x.getField(), x.getDefaultMessage()));
		}
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação: " + errors, e.getMessage(), request.getRequestURI());
				
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
	}
	
	private String[] splitNotFoundMessage(RuntimeException e) {
		return e.getMessage().split(WITH_ID);
	}
}

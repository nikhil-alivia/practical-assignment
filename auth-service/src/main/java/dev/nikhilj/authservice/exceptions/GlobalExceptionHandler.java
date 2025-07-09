package dev.nikhilj.authservice.exceptions;

import dev.nikhilj.common.exceptions.APIException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(
			MethodArgumentNotValidException ex
	) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(
				error -> errors.put(
						error.getField(),
						error.getDefaultMessage()
				)
		);
		return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, String>> handleMissingRequestBody(HttpMessageNotReadableException ex) {
		Map<String, String> errors = new HashMap<>();
		errors.put("error", "Request body is missing or malformed");
		return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(APIException.class)
	public ResponseEntity<Map<String, String>> handleAPIException(APIException ex) {
		Map<String, String> errors = new HashMap<>();
		errors.put("error", ex.getMessage());
		return new ResponseEntity<>(errors, ex.getStatus());
	}

}

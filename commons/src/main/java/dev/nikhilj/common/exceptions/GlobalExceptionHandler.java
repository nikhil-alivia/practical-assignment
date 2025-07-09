package dev.nikhilj.common.exceptions;

import dev.nikhilj.common.dtos.GenericErrorResponseDTO;
import dev.nikhilj.common.dtos.MethodArgumentNotValidDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request
	) {
		Map<String, String> validationErrors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			validationErrors.put(fieldName, errorMessage);
		});

		MethodArgumentNotValidDTO errorResponse = new MethodArgumentNotValidDTO(
				HttpStatus.BAD_REQUEST.value(),
				"Validation Failed",
				LocalDateTime.now(),
				validationErrors
		);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleDataIntegrityViolation(
			DataIntegrityViolationException ex
	) {
		Map<String, Object> body = new HashMap<>();

		String message = "A database constraint was violated. This may be due to a duplicate entry.";

		if (ex.getRootCause() != null && ex.getRootCause().getMessage().contains("UNIQUE KEY constraint")) {
			message = "A record with this value already exists. Please use a unique value.";
		}

		body.put("status", HttpStatus.CONFLICT.value());
		body.put("error", "Data Integrity Violation");
		body.put("message", message);

		return new ResponseEntity<>(body, HttpStatus.CONFLICT);

	}

	@ExceptionHandler(APIException.class)
	public ResponseEntity<GenericErrorResponseDTO> handleAPIException(
			APIException ex, WebRequest request
	) {
		GenericErrorResponseDTO errorResponseDTO = new GenericErrorResponseDTO(
				ex.getStatus().value(),
				ex.getMessage(),
				LocalDateTime.now()
		);
		return new ResponseEntity<>(errorResponseDTO, ex.getStatus());
	}

}

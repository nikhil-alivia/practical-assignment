package dev.nikhilj.common.security.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class APIException extends RuntimeException {
	private HttpStatus status;
	private String message;
}

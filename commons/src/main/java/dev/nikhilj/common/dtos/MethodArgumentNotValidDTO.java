package dev.nikhilj.common.dtos;

import java.time.LocalDateTime;
import java.util.Map;

public record MethodArgumentNotValidDTO(
		int statusCode,
		String message,
		LocalDateTime timestamp,
		Map<String, String> validationErrors
) {
}

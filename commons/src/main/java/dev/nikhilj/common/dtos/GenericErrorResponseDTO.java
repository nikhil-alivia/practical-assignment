package dev.nikhilj.common.dtos;

import java.time.LocalDateTime;
import java.util.Map;

public record GenericErrorResponseDTO(
		int statusCode,
		String message,
		LocalDateTime timestamp
) {
}

package dev.nikhilj.authservice.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record LoginDTO(
		@NotEmpty
		@Email
		String email,
		@NotEmpty
		String password
) {
}

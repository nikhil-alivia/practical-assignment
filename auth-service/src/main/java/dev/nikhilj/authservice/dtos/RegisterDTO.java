package dev.nikhilj.authservice.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RegisterDTO(
		@NotEmpty
		@Size(min = 2, message = "First Name should have at least 2 characters")
		String firstName,
		@NotEmpty
		@Size(min=3, message="Last name should have at least 3 characters")
		String lastName,
		@NotEmpty
		@Size(min=4, message = "Username should have at least 4 characters")
		String username,
		@NotEmpty
		@Email(message = "Email should be valid")
		String email,
		@NotEmpty
		String password
) {
}

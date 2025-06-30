package dev.nikhilj.authservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ProfileDTO(
		@JsonProperty(access = JsonProperty.Access.READ_ONLY)
		Long id,
		@NotEmpty
		@Size(min = 2, message = "First Name should have at least 2 characters")
		String firstName,
		@NotEmpty
		@Size(min = 3, message = "Last name should have at least 3 characters")
		String lastName,
		@NotEmpty
		@Size(min = 4, message = "Username should have at least 4 characters")
		String username,
		@JsonProperty(access = JsonProperty.Access.READ_ONLY)
		String email
) {
}

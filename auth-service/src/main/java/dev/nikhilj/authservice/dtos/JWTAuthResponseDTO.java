package dev.nikhilj.authservice.dtos;

public record JWTAuthResponseDTO(
		String accessToken,
		String tokenType
) {
}

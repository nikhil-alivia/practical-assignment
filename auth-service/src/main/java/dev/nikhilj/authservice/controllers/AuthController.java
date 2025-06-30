package dev.nikhilj.authservice.controllers;

import dev.nikhilj.authservice.dtos.JWTAuthResponseDTO;
import dev.nikhilj.authservice.dtos.LoginDTO;
import dev.nikhilj.authservice.dtos.RegisterDTO;
import dev.nikhilj.authservice.services.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
		String token = authService.login(loginDTO);
		return ResponseEntity.ok(
				new JWTAuthResponseDTO(token, "Bearer")
		);
	}

	@PostMapping("/register")
	public ResponseEntity<Void> register(@Valid @RequestBody RegisterDTO registerDTO) {
		authService.register(registerDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}

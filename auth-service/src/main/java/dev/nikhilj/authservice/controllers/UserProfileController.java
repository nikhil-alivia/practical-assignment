package dev.nikhilj.authservice.controllers;

import dev.nikhilj.authservice.dtos.ProfileDTO;
import dev.nikhilj.authservice.services.CustomUserDetailsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users/profile")
public class UserProfileController {


	private CustomUserDetailsService userDetailsService;

	@GetMapping
	public ResponseEntity<ProfileDTO> getProfile(Principal principal) {
		String username = principal.getName();
		var profile = userDetailsService.loadUserProfile(username);
		return ResponseEntity.ok(profile);
	}

	@PutMapping
	public ResponseEntity<ProfileDTO> saveProfile(
			@Valid @RequestBody ProfileDTO profileDTO,
			Principal principal
	) {
		String username = principal.getName();
		var updatedProfile = userDetailsService.saveUserProfile(profileDTO, username);
		return ResponseEntity.ok(updatedProfile);
	}


}

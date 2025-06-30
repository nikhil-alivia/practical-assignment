package dev.nikhilj.authservice.controllers;

import dev.nikhilj.authservice.dtos.ProfileDTO;
import dev.nikhilj.authservice.entitites.User;
import dev.nikhilj.authservice.services.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserProfileController {


	private CustomUserDetailsService userDetailsService;

	@GetMapping("/profile")
	public ResponseEntity<ProfileDTO> getProfile(Principal principal) {
		String username = principal.getName();
		var profile = userDetailsService.loadUserProfile(username);
		return ResponseEntity.ok(profile);
	}


}

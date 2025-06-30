package dev.nikhilj.authservice.services;

import dev.nikhilj.authservice.dtos.LoginDTO;
import dev.nikhilj.authservice.dtos.RegisterDTO;
import dev.nikhilj.authservice.entitites.Role;
import dev.nikhilj.authservice.entitites.User;
import dev.nikhilj.authservice.exceptions.APIException;
import dev.nikhilj.authservice.repositories.RoleRepository;
import dev.nikhilj.authservice.repositories.UserRepository;
import dev.nikhilj.authservice.security.JWTProvider;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthService {
	private AuthenticationManager authenticationManager;
	private PasswordEncoder passwordEncoder;

	private JWTProvider jwtProvider;

	private UserRepository userRepository;
	private RoleRepository roleRepository;


	public String login(LoginDTO loginDTO) {
		Authentication authn = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginDTO.email(),
						loginDTO.password()
				)
		);
		return jwtProvider.generateToken(authn);
	}

	public void register(RegisterDTO registerDTO) {
		if (userRepository.existsByEmail(registerDTO.email())) {
			throw new APIException(HttpStatus.BAD_REQUEST, "Email already exists");
		}
		if (userRepository.existsByUsername(registerDTO.username())) {
			throw new APIException(HttpStatus.BAD_REQUEST, "Username already exists");
		}
		User user = new User();

		user.setFirstName(registerDTO.firstName());
		user.setLastName(registerDTO.lastName());
		user.setEmail(registerDTO.email());
		user.setUsername(registerDTO.username());
		user.setPassword(
				passwordEncoder.encode(registerDTO.password())
		);

		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(
				() -> new APIException(HttpStatus.BAD_REQUEST, "ROLE_USER not found.")
		);
		roles.add(userRole);
		user.setRoles(roles);

		userRepository.save(user);

	}

}

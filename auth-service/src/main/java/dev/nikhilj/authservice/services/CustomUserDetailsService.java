package dev.nikhilj.authservice.services;

import dev.nikhilj.authservice.dtos.ProfileDTO;
import dev.nikhilj.authservice.entitites.User;
import dev.nikhilj.common.security.UserPrincipal;
import dev.nikhilj.common.security.exceptions.APIException;
import dev.nikhilj.authservice.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private UserRepository userRepository;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsernameOrEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
		Set<SimpleGrantedAuthority> authorities = user.getAuthorities();
		return new UserPrincipal(
				user.getId(),
				user.getEmail(),
				user.getPassword(),
				authorities
		);
	}

	public ProfileDTO loadUserProfile(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsernameOrEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
		return mapToDTO(user);
	}

	public ProfileDTO saveUserProfile(ProfileDTO profileDTO, String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsernameOrEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
		if (userRepository.existsByUsernameAndIdNot(profileDTO.username(), user.getId())) {
			throw new APIException(HttpStatus.BAD_REQUEST, "Username already exists");
		}
		user.setUsername(profileDTO.username());
		user.setFirstName(profileDTO.firstName());
		user.setLastName(profileDTO.lastName());
		userRepository.save(user);
		return mapToDTO(user);
	}

	private ProfileDTO mapToDTO(User user) {
		return new ProfileDTO(
				user.getId(),
				user.getFirstName(),
				user.getLastName(),
				user.getUsername(),
				user.getEmail()
		);
	}


}

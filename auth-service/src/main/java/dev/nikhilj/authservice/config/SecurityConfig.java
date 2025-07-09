package dev.nikhilj.authservice.config;

import dev.nikhilj.authservice.services.CustomUserDetailsService;
import dev.nikhilj.common.security.utils.JWTAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

	private AuthenticationEntryPoint authenticationEntryPoint;
	private JWTAuthenticationFilter authenticationFilter;
	private AccessDeniedHandler accessDeniedHandler;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public static AuthenticationManager authenticationManager(
			AuthenticationConfiguration authenticationConfiguration
	) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}


	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(List.of("*"));
		corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		corsConfig.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Auth-Token"));
		corsConfig.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
	}

	@Bean
	SecurityFilterChain securityFilterChain(
			HttpSecurity httpSecurity
	) throws Exception {
		httpSecurity.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(
						authorize -> authorize
								.requestMatchers("/api/auth/**").permitAll()
								.anyRequest().authenticated()
				)
				.exceptionHandling(
						exc -> exc.authenticationEntryPoint(authenticationEntryPoint)
								.accessDeniedHandler(accessDeniedHandler)
				)
				.sessionManagement(
						session -> session.sessionCreationPolicy(
								SessionCreationPolicy.STATELESS
						)
				);
		httpSecurity.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
	}

}

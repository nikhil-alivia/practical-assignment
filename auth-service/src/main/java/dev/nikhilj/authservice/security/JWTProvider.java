package dev.nikhilj.authservice.security;

import dev.nikhilj.authservice.entitites.User;
import dev.nikhilj.authservice.exceptions.APIException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class JWTProvider {

	@Value("${app.jwt-secret}")
	private String jwtSecret;

	@Value("${app.jwt-expiration-milliseconds}")
	private long jwtExpirationDate;

	public String generateToken(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		Date now = new Date();
		Date expDate = new Date(now.getTime() + jwtExpirationDate);


		var authorities = user.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.toList();

		return Jwts.builder()
				.subject(user.getUsername())
				.claim("authorities", authorities)
				.issuedAt(now)
				.expiration(expDate)
				.signWith(key())
				.compact();
	}

	private Key key() {
		return Keys.hmacShaKeyFor(
				Decoders.BASE64.decode(jwtSecret)
		);
	}

	public String getUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	public List<SimpleGrantedAuthority> getAuthorities(String token) {
		List<String> authorities = extractAllClaims(token).get("authorities", List.class);
		if (authorities == null) return Collections.emptyList();
		return authorities.stream()
				.map(SimpleGrantedAuthority::new)
				.toList();
	}


	public boolean validateToken(String token) {
		try {
			Jwts.parser()
					.verifyWith((SecretKey) key())
					.build()
					.parse(token);
			return true;
		} catch (MalformedJwtException exc) {
			throw new APIException(HttpStatus.BAD_REQUEST, "Invalid JWT Token");
		} catch (ExpiredJwtException exc) {
			throw new APIException(HttpStatus.BAD_REQUEST, "Expired JWT Token");
		} catch (UnsupportedJwtException exc) {
			throw new APIException(HttpStatus.BAD_REQUEST, "Unsupported JWT Token");
		} catch (IllegalArgumentException exc) {
			throw new APIException(HttpStatus.BAD_REQUEST, "JWT claims string is null or empty");
		}
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith((SecretKey) key())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

}

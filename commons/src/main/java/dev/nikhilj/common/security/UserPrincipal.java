package dev.nikhilj.common.security;


import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class UserPrincipal implements UserDetails {

	final private Long id;
	final private String username;
	final private String password; // Can be null or empty if not needed downstream
	final private Collection<? extends GrantedAuthority> authorities;

	// Constructor, getters, and setters...

	public UserPrincipal(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}
	public boolean isAdmin() {
		for (GrantedAuthority authority : authorities) {
			if ("ROLE_ADMIN".equals(authority.getAuthority())) {
				return true;
			}
		}
		return false;
	}
}

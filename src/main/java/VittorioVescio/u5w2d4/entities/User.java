package VittorioVescio.u5w2d4.entities;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

	@Id
	@GeneratedValue
	private UUID id;
	private String username;
	private String nome;
	private String email;
	private Boolean active = true;

	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;

	public User(String username, String nome, String email, String password) {
		super();
		this.username = username;
		this.nome = nome;
		this.email = email;
		this.password = password;
		this.role = Role.USER;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}

package keter.domain;

import static javax.persistence.InheritanceType.JOINED;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * User which may log in to the administration panel. Has at least one
 * authority.
 * 
 * @author Dawid Fatyga
 * 
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "account" }))
@Inheritance(strategy = JOINED)
public class User {

	@Id
	@GeneratedValue
	private Long id;

	@ElementCollection(targetClass = Authority.class)
	@Enumerated(EnumType.STRING)
	private Set<Authority> authorities = new HashSet<Authority>();

	@NotEmpty
	private String account;
	
	@NotEmpty
	private String username;

	@NotEmpty
	private String password;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return authorities as unmodifiable set.
	 */
	public Set<Authority> getAuthorities() {
		return Collections.unmodifiableSet(authorities);
	}

	/**
	 * Adds authority for user.
	 * 
	 * @param authority
	 * @throws NullPointerException
	 *             - if the specified element is null
	 */
	public void addAuthority(Authority authority) {
		authorities.add(authority);
	}
	
	public void removeAuthority(Authority authority) {
		authorities.remove(authority);
	}
}

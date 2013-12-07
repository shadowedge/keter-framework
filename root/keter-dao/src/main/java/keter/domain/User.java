package keter.domain;

import static javax.persistence.InheritanceType.JOINED;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED", nullable = false, updatable = false)
	private Date created = new Date();

	
//	// FIXME:未生效
//	@Column(updatable = false, insertable = false)
//	@org.hibernate.annotations.Generated(org.hibernate.annotations.GenerationTime.ALWAYS)
//	private Date lastModified;

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
	
	public Date getCreated() {
		return created;
	}
}


//package keter.domain;
//
//import static javax.persistence.InheritanceType.JOINED;
//
//import java.util.Collections;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.persistence.Column;
//import javax.persistence.ElementCollection;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.Inheritance;
//import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//import javax.persistence.UniqueConstraint;
//
//import org.hibernate.annotations.Formula;
//import org.hibernate.validator.constraints.NotEmpty;
//
///**
// * User which may log in to the administration panel. Has at least one
// * authority.
// * 
// * @author gulixing@msn.com
// */
//@Entity
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "account" }))
//@Inheritance(strategy = JOINED)
//public class User {
//
//	private String account;
//
//	private Set<Authority> authorities = new HashSet<Authority>();
//
//	private Date created = new Date();
//
//	private Long id;
//
//	private Date lastModified;
//
//	private String password;
//
//	private int total;
//
//	private String username;
//
//	public User() {
//	}
//
//	public void addAuthority(Authority authority) {
//		authorities.add(authority);
//	}
//
//	@NotEmpty
//	public String getAccount() {
//		return account;
//	}
//
//	@ElementCollection(targetClass = Authority.class)
//	@Enumerated(EnumType.STRING)
//	public Set<Authority> getAuthorities() {
//		return Collections.unmodifiableSet(authorities);
//	}
//

//	public Date getCreated() {
//		return created;
//	}
//
//	@Id
//	@GeneratedValue
//	public Long getId() {
//		return id;
//	}
//
//	// FIXME:未生效
//	@Column(updatable = false, insertable = false)
//	@org.hibernate.annotations.Generated(org.hibernate.annotations.GenerationTime.ALWAYS)
//	public Date getLastModified() {
//		return lastModified;
//	}
//
//	@NotEmpty
//	public String getPassword() {
//		return password;
//	}
//
//	// FIXME:未生效
//	@Formula("(select 123 from user)")
//	public int getTotal() {
//		return total;
//	}
//
//	@NotEmpty
//	public String getUsername() {
//		return username;
//	}
//
//	public void removeAuthority(Authority authority) {
//		authorities.remove(authority);
//	}
//
//	public void setAccount(String account) {
//		this.account = account;
//	}
//
//	public void setAuthorities(Set<Authority> authorities) {
//		this.authorities = authorities;
//	}
//
//	public void setCreated(Date created) {
//		this.created = created;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public void setLastModified(Date lastModified) {
//		this.lastModified = lastModified;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public void setTotal(int total) {
//		this.total = total;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//}

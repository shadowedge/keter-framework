package keter.domain;

import static javax.persistence.InheritanceType.JOINED;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
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
@org.hibernate.annotations.DynamicInsert
// 动态生成SQL
@org.hibernate.annotations.DynamicUpdate
@Inheritance(strategy = JOINED)
public class User {

	@Id
	@Column(name = "USER_ID")
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

	
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    @org.hibernate.annotations.BatchSize(size = 10)
	private List<Address> address = new ArrayList();

	public List<Address> getAddress() {
		return address;
	}

	public void addAddress(Address bid) {
		if (bid == null)
			throw new IllegalArgumentException("Can't add a null Bid.");
		this.getAddress().add(bid);
	}

//	private String desc;
//
//	public String getDesc() {
//		return desc;
//	}
//
//	public void setDesc(String desc) {
//		this.desc = desc;
//	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED", nullable = false, updatable = false)
	private Date created = new Date();

	// FIXME:可以看见Sql但未生效
	// @org.hibernate.annotations.Formula("(select count(*) from user)")
	//[注意]默认情况下，Linux下的MySql表名是区分大小写的（Windows下统一为小写）。
	//因此直接写Sql必须保证大小写一致，否则容易出错
	//
	@org.hibernate.annotations.Formula("(select count(*) from User)")
	private int total;

	// FIXME:未生效
	@Column(updatable = false, insertable = false)
	@org.hibernate.annotations.Generated(org.hibernate.annotations.GenerationTime.ALWAYS)
	private Date lastModified;

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

	public int getTotal() {
		return total;
	}

	public Date getLastModified() {
		return lastModified;
	}
}
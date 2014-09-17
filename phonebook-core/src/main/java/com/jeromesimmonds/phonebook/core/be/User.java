package com.jeromesimmonds.phonebook.core.be;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

/**
 * @author Jerome Simmonds
 *
 */
@Entity
@Table(name="users")
public class User implements Serializable {

	private static final long serialVersionUID = -7032785346267814988L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id; // not immutable (final) on purpose

	@Column(name="username")
	private String username;
	@Column(name="email")
	private String email;
	@Column(name="password")
	private String password;
	@Column(name="disabled")
	private boolean disabled = false;
	@Column(name="confirmed")
	private boolean confirmed = false;
	
	@Column(name="created", updatable=false)
	private Date created;
	@Column(name="modified", insertable=false, updatable=false)
	private Date modified;

    @PrePersist
    protected void doPrePersist() {
    	created = new Date();
	}
    
	public User() {
		super();
	}
	
	public User(int id) {
		super();
		this.id = id;
	}

	public User(int id, String username) {
		super();
		this.id = id;
		this.username = username;
	}
	
	public int getId() {
		return id;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public boolean isConfirmed() {
		return confirmed;
	}
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}
	public Date getCreated() {
		return created;
	}
	public Date getModified() {
		return modified;
	}
}

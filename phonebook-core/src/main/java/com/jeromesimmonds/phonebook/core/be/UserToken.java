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
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Jerome Simmonds
 *
 */
@Entity
@Table(name="user_tokens")
public class UserToken implements Serializable {

	private static final long serialVersionUID = -1819276586073392911L;
	
	public static final int TYPE_EMAILCONFIRMATION = 1;
	public static final int TYPE_FORGOTPASSWORD = 2;
	public static final int TYPE_EMAILCHANGECANCELLATION = 3;
	
	@Id
	@Column(name="token")
	private String token;
	@Column(name="type_id")
	private int typeId;
	@Column(name="email")
	private String email;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;

	@Column(name="created", updatable=false)
	private Date created;
	@Column(name="expiration_time")
	private Date expirationTime;

    @PrePersist
    protected void doPrePersist() {
    	created = new Date();
    }
    
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}
}

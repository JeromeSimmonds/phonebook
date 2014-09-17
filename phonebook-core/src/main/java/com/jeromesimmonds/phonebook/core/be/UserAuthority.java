package com.jeromesimmonds.phonebook.core.be;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="users_authorities")
public class UserAuthority implements Serializable {

	private static final long serialVersionUID = -5708703640964738464L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName="id")
	private User user;
	@ManyToOne
	@JoinColumn(name="authority_id", referencedColumnName="id")
	private Authority authority;

	@Column(name="created", updatable=false)
	private Date created;
	@Column(name="modified", insertable=false, updatable=false)
	private Date modified;

    @PrePersist
    protected void doPrePersist() {
    	created = new Date();
	}

	public UserAuthority() {
		super();
	}
	
	public UserAuthority(int id) {
		super();
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public Authority getAuthority() {
		return authority;
	}
	public void setAuthority(Authority authority) {
		this.authority = authority;
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
	public Date getModified() {
		return modified;
	}
}

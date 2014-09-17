package com.jeromesimmonds.phonebook.core.be;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

/**
 * @author Jerome Simmonds
 *
 */
@Entity
@Table(name="authorities")
public class Authority implements Serializable {
	
	private static final long serialVersionUID = -150538446164959990L;
	
	public static final int ROLE_USER_ID = 1;
	public static final int ROLE_ADMIN_ID = 2;
	
	@Id
	@Column(name="id")
	private int id; //TODO: problem with "final" and Spring Security (requires default constructor)
	
	@Enumerated(EnumType.STRING)
	@Column(name="name")
	private AuthorityType type = null;

	@Column(name="created", updatable=false)
	private Date created;
	@Column(name="modified", insertable=false, updatable=false)
	private Date modified;

    @PrePersist
    protected void doPrePersist() {
    	this.created = new Date();
	}

	public Authority() {
		super();
	}

	public Authority(int id) {
		super();
		this.id = id;
	}
	
	public String getAuthority() {
		if(type != null)
			return type.getValue();
		else 
			return null;
	}

	public AuthorityType getType() {
		return type;
	}
	public void setType(AuthorityType type) {
		this.type = type;
	}
	public Date getCreated() {
		return created;
	}
	public Date getModified() {
		return modified;
	}
}

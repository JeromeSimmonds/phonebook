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
@Table(name="phone_numbers")
public class PhoneNumber implements Serializable {

	private static final long serialVersionUID = 1747167513588689578L;

	public static final int TYPE_HOME = 1;
	public static final int TYPE_CELLPHONE = 2;
	public static final int TYPE_OFFICE = 3;
	public static final int TYPE_OTHER = 4;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id; // not immutable (final) on purpose

	@Column(name="number")
	private String number;
	@Column(name="type_id")
	private int type;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="contact_id")
	private Contact contact;
	
	@Column(name="created", updatable=false)
	private Date created;
	@Column(name="modified", insertable=false, updatable=false)
	private Date modified;

    @PrePersist
    protected void doPrePersist() {
    	created = new Date();
	}
    
	public PhoneNumber() {
		super();
	}
	
	public PhoneNumber(int id) {
		super();
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Date getCreated() {
		return created;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public Date getModified() {
		return modified;
	}
}

package com.jeromesimmonds.phonebook.web.form;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jeromesimmonds.phonebook.core.be.PhoneNumber;

/**
 * @author Jerome Simmonds
 *
 */
public class ContactForm {

	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String photo;
	private MultipartFile photoFile;
	private List<PhoneNumber> phoneNumbers;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public MultipartFile getPhotoFile() {
		return photoFile;
	}
	public void setPhotoFile(MultipartFile photoFile) {
		this.photoFile = photoFile;
	}
	public List<PhoneNumber> getPhoneNumbers() {
		return phoneNumbers;
	}
	public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}
}

package com.suski.zilch.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {

	@NotBlank(message= "Please provide user name")
	@Size(min=3, message="User first name must contain at least 3 characters")
	protected String firstname;
	
	protected String lastname;
	
	@NotNull
	@Email(message = "Please provide correct email adress")
	protected String email;

	public User() {
	}

	public User(String firstname,String lastname,String email) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}

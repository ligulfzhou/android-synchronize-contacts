package com.keshe.contacts_keshe.bean;

public class Contact {
	public String id;
	public String name;
//	public String email;
	public String number;

	public Contact(String id, String name) {
		this.id = id;
		this.name = name;
	}


	//	public Contact(String id, String name, String email, String number) {
	public Contact(String id, String name, String number) {
		this.id = id;
		this.name = name;
//		this.email = email;
		this.number = number;
	}

	@Override
	public String toString() {
//		return "name: " + name + "mobile: " + number + "email: " + email;
		return name + "," + number;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}

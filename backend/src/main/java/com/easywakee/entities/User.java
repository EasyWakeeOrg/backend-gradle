package com.easywakee.entities;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;

public class User {

	@Id
	private String email;
	private String pseudo;
	private String password;
	private int time;//the time param in minutes
	private Address address;
	private ArrayList<String> transport;
	private Address schoolPlace;
	private String deviceId;
	
	public User(String em, String pseudo, String password, int time, Address address,
			ArrayList<String> transport, Address schoolPlace, String deviceId){
		this.email = em;
		this.pseudo=pseudo;
		this.password=password;
		this.time = time;
		this.address = address;
		this.transport = transport;
		this.schoolPlace = schoolPlace;
		this.deviceId = deviceId;
	}
	
	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void changeAddress(int nb, String street, int pc, String city){
		this.address.setNb(nb);
		this.address.setStreet(street);
		this.address.setPostalCode(pc);
		this.address.setCity(city);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public ArrayList<String> getTransport() {
		return transport;
	}

	public void setTransport(ArrayList<String> transport) {
		this.transport = transport;
	}

	public Address getSchoolPlace() {
		return schoolPlace;
	}

	public void setSchoolPlace(Address schoolPlace) {
		this.schoolPlace = schoolPlace;
	}

    @Override
    public String toString() {
        return "User[email=" + email + ", pseudo=" +pseudo +", password="
        		+ password + ", time=" + time + ", address=" +
        		address.toString() + ", transports= " + transport.toString() +
        		", schoolPlace=" + schoolPlace.toString() +"]";
    }
}

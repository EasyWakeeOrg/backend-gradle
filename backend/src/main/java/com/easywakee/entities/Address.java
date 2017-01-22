package com.easywakee.entities;

public class Address {
	private int nb;
	private String street;
	private int postalCode;
	private String city;
	
	public Address(int nb, String street, int pc, String city){
		this.nb = nb;
		this.street = street;
		this.postalCode = pc;
		this.city = city;
	}

	public int getNb() {
		return nb;
	}

	public void setNb(int nb) {
		this.nb = nb;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
    @Override
    public String toString() {
        return "Address[" + nb + " " + street + ", " + postalCode + " " + 
        		city +"]";
    }
}

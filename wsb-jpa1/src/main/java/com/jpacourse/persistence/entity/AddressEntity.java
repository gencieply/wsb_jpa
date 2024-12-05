package com.jpacourse.persistence.entity;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS")
public class AddressEntity {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "CITY", nullable = false, length = 100)
	private String city;

	@Column(name = "ADDRESS_LINE_1", nullable = false, length = 150)
	private String addressLine1;

	@Column(name = "ADDRESS_LINE_2", length = 150)
	private String addressLine2;

	@Column(name = "POSTAL_CODE", nullable = false, length = 20)
	private String postalCode;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

}

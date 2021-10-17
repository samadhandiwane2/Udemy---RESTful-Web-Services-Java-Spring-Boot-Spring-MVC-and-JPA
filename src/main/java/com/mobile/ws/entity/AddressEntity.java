package com.mobile.ws.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "addresses")
public class AddressEntity implements Serializable {

	private static final long serialVersionUID = 4953163684452751563L;

	@Id
	@GeneratedValue
	private long id;
	
	@Column(length = 30, nullable = false)
	private String addressId;
	
	@Column(length = 20, nullable = false)
	private String city;
	
	@Column(length = 20, nullable = false)
	private String country;
	
	@Column(length = 100, nullable = false)
	private String streetName;
	
	@Column(length = 30, nullable = false)
	private String type;
	
	@Column(length = 7)
	private int zipCode;
	
	@ManyToOne
	@JoinColumn(name = "users_id")
	private UserEntity userDetails;

}

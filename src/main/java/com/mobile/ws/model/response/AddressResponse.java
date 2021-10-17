package com.mobile.ws.model.response;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse extends RepresentationModel {

	private String addressId;
	private String city;
	private String country;
	private String streetName;
	private String type;
	private int zipCode;
}

package com.mobile.ws.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {

	private String city;
	private String country;
	private String streetName;
	private String type;
	private int zipCode;

}

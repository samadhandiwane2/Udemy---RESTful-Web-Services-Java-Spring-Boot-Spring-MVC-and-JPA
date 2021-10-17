package com.mobile.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
	
	private long id;
	private String addressId;
	private String city;
	private String country;
	private String streetName;
	private String type;
	private int zipCode;
	private UserDto userDetails;

}

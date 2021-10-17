package com.mobile.ws.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	List<AddressResponse> addresses;

}

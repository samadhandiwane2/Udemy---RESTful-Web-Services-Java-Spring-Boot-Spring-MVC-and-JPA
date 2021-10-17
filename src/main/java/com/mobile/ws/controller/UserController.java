package com.mobile.ws.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.mobile.ws.dto.AddressDto;
import com.mobile.ws.dto.UserDto;
import com.mobile.ws.error.ErrorMessages;
import com.mobile.ws.exception.UserServiceException;
import com.mobile.ws.model.request.UserDetailsRequest;
import com.mobile.ws.model.response.AddressResponse;
import com.mobile.ws.model.response.OperationStatusModel;
import com.mobile.ws.model.response.RequestOperationNames;
import com.mobile.ws.model.response.RequestOperationStatus;
import com.mobile.ws.model.response.UserResponse;
import com.mobile.ws.service.AddressService;
import com.mobile.ws.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	AddressService addressService;

	@GetMapping(path = "/{userId}", 
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserResponse getUser(@PathVariable String userId) {

		UserResponse response = new UserResponse();
		UserDto userDto = userService.getUserById(userId);
		BeanUtils.copyProperties(userDto, response);
		return response;
	}

	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<UserResponse> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit) {

		List<UserResponse> responseList = new ArrayList<>();

		List<UserDto> users = userService.getUsers(page, limit);
		for (UserDto user : users) {
			UserResponse response = new UserResponse();
			BeanUtils.copyProperties(user, response);
			responseList.add(response);
		}
		return responseList;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserResponse createUser(@RequestBody UserDetailsRequest request) throws Exception {
		UserResponse response = new UserResponse();

		if (request.getFirstName().isEmpty()) {
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELDS.getErrorMessage());
		}
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(request, UserDto.class);

		UserDto createdUser = userService.createUser(userDto);
		response = modelMapper.map(createdUser, UserResponse.class);

		return response;
	}

	@PutMapping(path = "/{userId}", 
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserResponse updateUser(@PathVariable String userId, @RequestBody UserDetailsRequest request) {
		UserResponse response = new UserResponse();
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(request, userDto);

		UserDto updatedUser = userService.updateUser(userId, userDto);
		BeanUtils.copyProperties(updatedUser, response);
		return response;
	}

	@DeleteMapping(path = "/{userId}", 
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public OperationStatusModel deleteUser(@PathVariable String userId) {

		OperationStatusModel response = new OperationStatusModel();
		response.setOperationName(RequestOperationNames.DELETE.name());
		userService.deleteUser(userId);
		response.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return response;
	}

	@SuppressWarnings("deprecation")
	@GetMapping(path = "/{userId}/addresses", 
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE, "application/hal+json" })
	public CollectionModel<AddressResponse> getUserAddresses(@PathVariable String userId) {

		List<AddressResponse> addressResponseList = new ArrayList<>();

		List<AddressDto> addressesDto = addressService.getAddresses(userId);
		
		if (addressesDto != null && !addressesDto.isEmpty()) {
			Type listType = new TypeToken<List<AddressResponse>>() {
			}.getType();
			addressResponseList = new ModelMapper().map(addressesDto, listType);
			for(AddressResponse address : addressResponseList) {
				Link addressLink = linkTo(methodOn(UserController.class).getUserAddress(userId, address.getAddressId())).withSelfRel();
				address.add(addressLink);
				
				Link userLink = linkTo(methodOn(UserController.class).getUserAddresses(userId)).withRel("user");
				address.add(userLink);
			}
		}
		return new CollectionModel<>(addressResponseList);
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping(path = "/{userId}/addresses/{addressId}", 
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE, "application/hal+json" })
	public EntityModel<AddressResponse> getUserAddress(@PathVariable String userId, @PathVariable String addressId) {

		AddressResponse response = new AddressResponse();
		AddressDto addressesDto = addressService.getAddress(addressId);	
		
		Link addressLink = linkTo(methodOn(UserController.class).getUserAddress(userId, addressId)).withSelfRel();
		Link addressesLink = linkTo(UserController.class).slash(userId).slash("addresses").withRel("addresses");
		Link userLink = linkTo(methodOn(UserController.class).getUserAddresses(userId)).withRel("user");
		
		if (addressesDto != null) {
			response = new ModelMapper().map(addressesDto, AddressResponse.class);
		}
		response.add(addressLink);
		response.add(userLink);
		response.add(addressesLink);
		
		return new EntityModel<>(response);
	}

}

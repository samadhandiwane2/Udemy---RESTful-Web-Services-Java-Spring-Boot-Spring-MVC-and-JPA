package com.mobile.ws.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mobile.ws.dto.AddressDto;

@Service
public interface AddressService {

	List<AddressDto> getAddresses(String userId);

	AddressDto getAddress(String addressId);

}

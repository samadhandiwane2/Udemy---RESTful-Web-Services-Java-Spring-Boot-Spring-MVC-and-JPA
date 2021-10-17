package com.mobile.ws.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobile.ws.dto.AddressDto;
import com.mobile.ws.entity.AddressEntity;
import com.mobile.ws.entity.UserEntity;
import com.mobile.ws.repository.AddressRepository;
import com.mobile.ws.repository.UserRepository;
import com.mobile.ws.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	AddressRepository addressRepo;

	@Autowired
	UserRepository userRepo;

	@Override
	public List<AddressDto> getAddresses(String userId) {
		List<AddressDto> responseList = new ArrayList<>();
		ModelMapper mapper = new ModelMapper();

		UserEntity userEntity = userRepo.findByUserId(userId);
		if (userEntity == null)
			return responseList;

		Iterable<AddressEntity> addresses = addressRepo.findAllByUserDetails(userEntity);
		for (AddressEntity addressEntity : addresses) {
			responseList.add(mapper.map(addressEntity, AddressDto.class));
		}
		return responseList;
	}

	@Override
	public AddressDto getAddress(String addressId) {
		
		AddressDto addressDto = new AddressDto();		
		AddressEntity addressEntity = addressRepo.findByAddressId(addressId);
		
		if(addressEntity != null) {
			addressDto = new ModelMapper().map(addressEntity, AddressDto.class);
		}
		return addressDto;
	}

}

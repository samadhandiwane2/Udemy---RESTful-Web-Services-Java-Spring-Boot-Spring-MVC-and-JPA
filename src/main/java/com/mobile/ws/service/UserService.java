package com.mobile.ws.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.mobile.ws.dto.UserDto;

@Service
public interface UserService extends UserDetailsService {

	public UserDto createUser(UserDto user);
	public UserDto getUser(String email);
	public UserDto getUserById(String id);
	public UserDto updateUser(String userId, UserDto userDto);
	public void deleteUser(String userId);
	public List<UserDto> getUsers(int page, int limit);

}

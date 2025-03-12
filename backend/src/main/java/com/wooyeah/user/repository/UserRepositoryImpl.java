package com.wooyeah.user.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wooyeah.user.dto.RequestSignUpDto;
import com.wooyeah.user.dto.RequestUpdateDto;
import com.wooyeah.user.dto.UserDto;
import com.wooyeah.user.entity.ProfileEntity;
import com.wooyeah.user.entity.UserEntity;
import com.wooyeah.user.mapper.UserMapper;

@Repository
public class UserRepositoryImpl implements UserRepository {

	private final UserMapper userMapper;
	
	public UserRepositoryImpl(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	@Override
	public int getUsersById(String inputId) {
		return userMapper.selectCountById(inputId);
	}
	
	@Override
	public void saveUser(RequestSignUpDto requestSignUpDto) {
		userMapper.insertUser(requestSignUpDto);
		
	}
	
	@Override
	public void saveProfile(RequestSignUpDto requestSignUpDto) {
		userMapper.insertProfileFileByUserId(requestSignUpDto);
	}

	@Override
	public UserEntity getUser(String uuid) {
		return userMapper.selectByUuid(uuid);
	}

	@Override
	public ProfileEntity getUserProfile(String userId) {
		return userMapper.selectProfileByUserId(userId);
	}

	@Override
	public void updateUser(String userId, RequestUpdateDto requestUpdateDto) {
		Map<String, Object> info = new HashMap<>();
		info.put("userId", userId);
		info.put("dto", requestUpdateDto);

		userMapper.updateUserByUserId(info);
	}

	@Override
	public void deleteProfile(String userId, RequestUpdateDto requestUpdateDto) {

		userMapper.deleteProfileFileByUserId(userId);
	}

	@Override
	public void updateProfile(String userId, RequestUpdateDto requestUpdateDto) {
		Map<String, Object> info = new HashMap<>();
		info.put("userId", userId);
		info.put("dto", requestUpdateDto.getUserProfile());

		userMapper.updateProfileFileByUserId(info);
	}

	@Override
	public UserEntity getUserInfo(String userId) {
		return userMapper.selectByUserId(userId);
	}

}

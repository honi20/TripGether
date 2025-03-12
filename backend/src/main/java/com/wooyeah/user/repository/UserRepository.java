package com.wooyeah.user.repository;

import com.wooyeah.user.dto.RequestSignUpDto;
import com.wooyeah.user.dto.RequestUpdateDto;
import com.wooyeah.user.dto.UserDto;
import com.wooyeah.user.entity.ProfileEntity;
import com.wooyeah.user.entity.UserEntity;

public interface UserRepository {

	int getUsersById(String inputId);
	
	void saveUser(RequestSignUpDto requestSignUpDto);

	void saveProfile(RequestSignUpDto requestSignUpDto);
	
	UserEntity getUser(String uuid);

	ProfileEntity getUserProfile(String userId);

	void updateUser(String userId, RequestUpdateDto requestUpdateDto);

	void deleteProfile(String userId, RequestUpdateDto requestUpdateDto);

	void updateProfile(String userId, RequestUpdateDto requestUpdateDto);

	UserEntity getUserInfo(String userId);
}

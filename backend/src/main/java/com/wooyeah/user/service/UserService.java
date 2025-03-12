package com.wooyeah.user.service;

import com.wooyeah.user.dto.RequestSignUpDto;
import com.wooyeah.user.dto.RequestUpdateDto;
import com.wooyeah.user.dto.UserDto;

public interface UserService {

	void checkId(String inputId);

	void signUp(RequestSignUpDto requestSignUpDto);
	
	UserDto getUser(String accessToken);

	void modify(String userId, RequestUpdateDto requestUpdateDto);


}

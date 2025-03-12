package com.wooyeah.auth.repository;

import com.wooyeah.auth.dto.RequestLoginDto;
import com.wooyeah.auth.dto.TokenDto;
import com.wooyeah.user.entity.UserEntity;

public interface AuthRepository {
	
	UserEntity getUser(RequestLoginDto requestLoginDto);

	int saveToken(TokenDto accessToken);

	void setInvalid(String userUuid);

	UserEntity getUser(String userUuid);

	void setInvalid(String userUuid, String refreshToken);

	boolean getValidStatusToken(String token);
}

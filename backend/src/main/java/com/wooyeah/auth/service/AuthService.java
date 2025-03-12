package com.wooyeah.auth.service;

import com.wooyeah.auth.dto.AuthDto;
import com.wooyeah.auth.dto.RequestLoginDto;
import com.wooyeah.auth.dto.TokenDto;
import com.wooyeah.exception.NotFoundUserException;

public interface AuthService {

	AuthDto login(RequestLoginDto requestLoginDto) throws NotFoundUserException;

	void logout(String accessToken);

	TokenDto reGenerateToken(String refreshToken);

	boolean isValidToken(String token);
}

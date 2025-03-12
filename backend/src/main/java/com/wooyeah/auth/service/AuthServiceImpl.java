package com.wooyeah.auth.service;

import org.springframework.stereotype.Service;

import com.wooyeah.auth.dto.AuthDto;
import com.wooyeah.auth.dto.RequestLoginDto;
import com.wooyeah.auth.dto.TokenDto;
import com.wooyeah.auth.repository.AuthRepository;
import com.wooyeah.auth.util.HashUtil;
import com.wooyeah.auth.util.JwtUtil;
import com.wooyeah.exception.NotFoundUserException;
import com.wooyeah.user.entity.UserEntity;


@Service
public class AuthServiceImpl implements AuthService {

	private final AuthRepository authRepository;
	private final JwtUtil jwtUtil;
	private final HashUtil hashUtil;
	
	public AuthServiceImpl(AuthRepository authRepository, JwtUtil jwtUtil, HashUtil hashUtil) {
		this.authRepository = authRepository;
		this.jwtUtil = jwtUtil;
		this.hashUtil = hashUtil;
	}

	public AuthDto login(RequestLoginDto requestLoginDto) throws NotFoundUserException {
		
		UserEntity userEntity = authRepository.getUser(requestLoginDto);
		
		if(userEntity == null) {
			throw new NotFoundUserException();
		}
		
		TokenDto accessToken = jwtUtil.generateToken(userEntity, "AccessToken");
		TokenDto refreshToken = jwtUtil.generateToken(userEntity, "RefreshToken");
		
		authRepository.saveToken(accessToken);
		authRepository.saveToken(refreshToken);
		
		AuthDto authDto = new AuthDto();
		authDto.setAccessToken(accessToken.getToken());
		authDto.setRefreshToken(refreshToken.getToken());
		authDto.setMaxAge(refreshToken.getExpiration());
		
		return authDto;
	}
	
	@Override
	public void logout(String accessToken) {
		String userUuid = jwtUtil.getUuid(accessToken, "AccessToken");
		
		authRepository.setInvalid(userUuid);
		
	}

	@Override
	public TokenDto reGenerateToken(String refreshToken) {

		String userUuid = jwtUtil.getUuid(refreshToken, "RefreshToken");
		authRepository.setInvalid(userUuid, hashUtil.getDigest(refreshToken));
		
		UserEntity userEntity = authRepository.getUser(userUuid);
		TokenDto newAccessToken = jwtUtil.generateToken(userEntity, "AccessToken");
		
		System.out.println("newAccessToken => " + newAccessToken);
		authRepository.saveToken(newAccessToken);
		
		return newAccessToken;
	}

	@Override
	public boolean isValidToken(String token) {
		return authRepository.getValidStatusToken(token);
	}

}

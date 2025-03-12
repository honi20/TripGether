package com.wooyeah.auth.repository;

import org.springframework.stereotype.Repository;

import com.wooyeah.auth.dto.RequestLoginDto;
import com.wooyeah.auth.dto.TokenDto;
import com.wooyeah.auth.mapper.AuthMapper;
import com.wooyeah.auth.util.HashUtil;
import com.wooyeah.user.entity.UserEntity;

@Repository
public class AuthRepositoryImpl implements AuthRepository {

	private final AuthMapper authMapper;
	private final HashUtil hashUtil;
	
	public AuthRepositoryImpl(AuthMapper authMapper, HashUtil hashUtil) {
		this.authMapper = authMapper;
		this.hashUtil = hashUtil;
	}
	
	@Override
	public UserEntity getUser(RequestLoginDto requestLoginDto) {
		requestLoginDto.setUserPassword(requestLoginDto.getUserPassword());
		return authMapper.selectByRequestLoginDto(requestLoginDto);
	}

	@Override
	public int saveToken(TokenDto tokenDto) {
		return authMapper.insertTokenDto(tokenDto);
	}

	@Override
	public void setInvalid(String userUuid) {
		System.out.println(userUuid);
		authMapper.updateValidTokenToInvalidByUserUuid(userUuid);
	}

	@Override
	public UserEntity getUser(String userUuid) {
		return authMapper.selectByUserUuid(userUuid);
	}

	@Override
	public void setInvalid(String userUuid, String hashedRefreshToken) {
		authMapper.updateValidTokenToInvalidByUserUuidAndRefreshToken(userUuid, hashedRefreshToken);
	}

	@Override
	public boolean getValidStatusToken(String token) {
		token = hashUtil.getDigest(token);
		return authMapper.selectValidStatusByToken(token);
	}

	
}

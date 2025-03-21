package com.wooyeah.auth.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.wooyeah.auth.dto.TokenDto;
import com.wooyeah.user.entity.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtUtil {
	
	private final HashUtil hashUtil;
	
	public JwtUtil(HashUtil hashUtil) {
		this.hashUtil = hashUtil;
	}
	
	private final long ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60 * 15; // 15분
	private final long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 4; // 4시간
	
	@Value("${jwt.secretkey.accesstoken}")
	private String accessTokenSecretKey;
	
	@Value("${jwt.secretkey.refreshtoken}")
	private String refreshTokenSecretKey;

	
	private SecretKey getSecretKey(String type) {
		// AccessToken과 RefreshToken을 서로 다른 키를 이용하여 발급.
		// AccessToken과 RefreshToken을 서로 변경하여 인증 시도를 방지.
		
		if(type.equals("AccessToken")) {
			return Keys.hmacShaKeyFor(accessTokenSecretKey.getBytes());
		}
		else if(type.equals("RefreshToken")) {
			return Keys.hmacShaKeyFor(refreshTokenSecretKey.getBytes());
		}
		
		return null;
	}
	
	private long getExpiration(long issuedAt, String type) {
		if(type.equals("AccessToken")) {
			return issuedAt + ACCESS_TOKEN_EXPIRE_TIME;
		}
		else if(type.equals("RefreshToken")) {
			return issuedAt + REFRESH_TOKEN_EXPIRE_TIME;
		}
		
		return 0L;
	}
	
	public TokenDto generateToken(UserEntity userEntity, String type) {
		
		// 발급 시간과 만료 시간을 계산.
		long issuedAt = System.currentTimeMillis();
		long expiration = getExpiration(issuedAt, type);
		
		String token = Jwts.builder()
				.claim("userUuid", userEntity.getUuid())
				.claim("userId", userEntity.getId())
				.issuedAt(new Date(issuedAt))
				.expiration(new Date(expiration))
				.signWith(getSecretKey(type))
				.compact();
		
		TokenDto tokenDto = new TokenDto();
		tokenDto.setUuid(userEntity.getUuid());
		tokenDto.setToken(token);
		tokenDto.setHashedToken(hashUtil.getDigest(token));
		tokenDto.setIssuedAt(issuedAt);
		tokenDto.setExpiration(expiration);
		
		return tokenDto;
	}

	public String getUuid(String token, String type) {
		
		if(isValidToken(token, type)) {
			Claims payload = Jwts.parser()
					.verifyWith(getSecretKey(type))
					.build()
					.parseSignedClaims(token)
					.getPayload();
			
			return payload.get("userUuid", String.class);
		}
		
		return null;
	}

	public boolean isValidToken(String token, String type) {
		Jws<Claims> payload = null;
		
		try {
			payload = Jwts.parser()
					.verifyWith(getSecretKey(type))
					.build()
					.parseSignedClaims(token);
		} catch(ExpiredJwtException | SignatureException | MalformedJwtException e) {
			return false;
		}
		
		return true;
	}
	
}
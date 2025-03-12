package com.wooyeah.auth.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wooyeah.auth.dto.AuthDto;
import com.wooyeah.auth.dto.RequestLoginDto;
import com.wooyeah.auth.dto.TokenDto;
import com.wooyeah.auth.service.AuthService;
import com.wooyeah.auth.util.HeaderUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody RequestLoginDto requestLoginDto) {
		
		AuthDto authDto = authService.login(requestLoginDto);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HeaderUtil.getAuthorizationHeaderName(), HeaderUtil.getTokenPrefix() + authDto.getAccessToken());
		
		ResponseCookie responseCookie = ResponseCookie
				.from("REFRESH_COOKIE", authDto.getRefreshToken())
				.domain("localhost") // 어떤 사이트에서 쿠키를 사용할 수 있도록 허용할 지 설정.
				.path("/") // 위 사이트에서 쿠키를 허용할 경로를 설정.
				.httpOnly(true) // HTTP 통신을 위해서만 사용하도록 설정.
				.secure(true) // Set-Cookie 설정.
				.maxAge(authDto.getMaxAge() / 1000) // RefreshToken과 동일한 만료 시간으로 설정.
				.sameSite("None") // 동일한 사이트에서 사용할 수 있도록 설정 None: 동일한 사이트가 아니어도 된다.
				.build();
		
		return ResponseEntity.ok()
				.headers(httpHeaders)
				.header(HttpHeaders.SET_COOKIE, responseCookie.toString())
				.build();
	}
	
	@DeleteMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest httpServletRequest) {
		
		String accessToken = HeaderUtil.getAccessToken(httpServletRequest);

		authService.logout(accessToken);
		
		System.out.println("logout cookie");
		HttpHeaders httpHeaders = new HttpHeaders();
		ResponseCookie responseCookie = ResponseCookie
				.from("REFRESH_COOKIE", "")
				.domain("localhost") // 어떤 사이트에서 쿠키를 사용할 수 있도록 허용할 지 설정.
				.path("/") // 위 사이트에서 쿠키를 허용할 경로를 설정.
				.httpOnly(true) // HTTP 통신을 위해서만 사용하도록 설정.
				.secure(true) // Set-Cookie 설정.
				.maxAge(0) // RefreshToken을 삭제하기 위해 시간을 0으로 설정.
				.sameSite("None") // 동일한 사이트에서 사용할 수 있도록 설정 None: 동일한 사이트가 아니어도 된다.
				.build();


		System.out.println(responseCookie);
		return ResponseEntity.noContent()
				.headers(httpHeaders).header(HttpHeaders.SET_COOKIE, responseCookie.toString())
				.build();
	}
	
	@GetMapping("/refresh")
	public ResponseEntity<?> refresh(HttpServletRequest httpServletRequest) {
		
		String refreshToken = HeaderUtil.getRefreshToken(httpServletRequest);

		TokenDto newAccessToken = authService.reGenerateToken(refreshToken);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HeaderUtil.getAuthorizationHeaderName(), HeaderUtil.getTokenPrefix() + newAccessToken.getToken());
		
		return ResponseEntity.ok()
				.headers(httpHeaders)
				.build();
	}
}

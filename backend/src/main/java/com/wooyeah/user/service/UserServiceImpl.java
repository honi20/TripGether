package com.wooyeah.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wooyeah.auth.util.JwtUtil;
import com.wooyeah.exception.InvalidIdException;
import com.wooyeah.user.dto.FileInfoDto;
import com.wooyeah.user.dto.RequestSignUpDto;
import com.wooyeah.user.dto.RequestUpdateDto;
import com.wooyeah.user.dto.UserDto;
import com.wooyeah.user.entity.ProfileEntity;
import com.wooyeah.user.entity.UserEntity;
import com.wooyeah.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	
	@Value("${file.path.upload-profiles}")
	private String uploadProfilePath;
	
	public UserServiceImpl(UserRepository userRepository, JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public void checkId(String inputId) {
		
		if (userRepository.getUsersById(inputId) != 0)
			throw new InvalidIdException();	
	}

	@Override
	@Transactional
	public void signUp(RequestSignUpDto requestSignUpDto) {
		// 회원 정보 입력
		userRepository.saveUser(requestSignUpDto);
		
		// 회원 이미지 파일 입력
		FileInfoDto profile = requestSignUpDto.getUserProfile();
		userRepository.saveProfile(requestSignUpDto);
	}

	@Override
	public UserDto getUser(String accessToken) {
		String uuid = jwtUtil.getUuid(accessToken, "AccessToken");
		UserEntity userEntity = userRepository.getUser(uuid);
		
		UserDto user = new UserDto();
		user.setUserId(userEntity.getId());
		user.setUserPassword(userEntity.getPassword());
		user.setUserName(userEntity.getName());
		user.setUserGender(userEntity.getGender());
		user.setUserBirthday(userEntity.getBirthday());

		// 회원 프로필 사진 가져오기
		ProfileEntity profileEntity = userRepository.getUserProfile(userEntity.getId());
		if (profileEntity != null && profileEntity.getSave_folder() != null) {
			String profilePath = uploadProfilePath + "/" + profileEntity.getSave_folder() + "/" + profileEntity.getSave_file();
			user.setUserProfile(profilePath);
		}
		
		return user;
	}

	@Override
	@Transactional
	public void modify(String userId, RequestUpdateDto requestUpdateDto) {
		// 회원 정보 업데이트
		userRepository.updateUser(userId, requestUpdateDto);
		
		// 회원 이미지 파일 업데이트
		FileInfoDto updateProfile = requestUpdateDto.getUserProfile();
		// 새로운 이미지 파일 등록 시, 정보 갱신
		if (updateProfile != null && updateProfile.getSaveFolder() != null) {
			userRepository.updateProfile(userId, requestUpdateDto);
		}
	}

}

package com.wooyeah.user.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.wooyeah.auth.util.HeaderUtil;
import com.wooyeah.user.dto.FileInfoDto;
import com.wooyeah.user.dto.RequestSignUpDto;
import com.wooyeah.user.dto.RequestUpdateDto;
import com.wooyeah.user.dto.UserDto;
import com.wooyeah.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
	
//	@Value("${file.path}")
//	private String uploadPath;
	
	@Value("${file.path.upload-profiles}")
	private String uploadProfilePath;
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/checkId/{inputId}")
	public ResponseEntity<?> checkId(@PathVariable("inputId") String inputId) {
		userService.checkId(inputId);
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value = "/signUp", consumes= {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> signUp(@RequestPart("data") RequestSignUpDto requestSignUpDto, @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {	
		System.out.println(file);
		
		// file upload 관련
		if (file != null) {
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder = uploadProfilePath + File.separator + today;
			
			File folder = new File(saveFolder);
			if (!folder.exists())
				folder.mkdirs();
			
			FileInfoDto fileInfoDto = new FileInfoDto();
			String originalFileName = file.getOriginalFilename();
			
			if (!originalFileName.isEmpty()) {
				String saveFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf('.'));
				
				fileInfoDto.setSaveFolder(today);
				fileInfoDto.setOriginalFile(originalFileName);
				fileInfoDto.setSaveFile(saveFileName);
				
				file.transferTo(new File(folder, saveFileName));
			}
			requestSignUpDto.setUserProfile(fileInfoDto);
		}
		
		userService.signUp(requestSignUpDto);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/myPage")
	public ResponseEntity<?> check(HttpServletRequest request) {
		String accessToken = HeaderUtil.getAccessToken(request);

		UserDto userDto = userService.getUser(accessToken);
		
		return ResponseEntity.ok().body(userDto);
	}
	
	// 회원 정보 수정
	@PutMapping("/update")
	public ResponseEntity<?> update(HttpServletRequest request, @RequestPart("data") RequestUpdateDto requestUpdateDto, @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
		String accessToken = HeaderUtil.getAccessToken(request);
		String userId = userService.getUser(accessToken).getUserId();
		
		if (file != null) {
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder = uploadProfilePath + File.separator + today;
					
			File folder = new File(saveFolder);
			if (!folder.exists())
				folder.mkdirs();
					
			FileInfoDto fileInfoDto = new FileInfoDto();
			String originalFileName = file.getOriginalFilename();
					
			if (!originalFileName.isEmpty()) {
				String saveFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf('.'));
						
				fileInfoDto.setSaveFolder(today);
				fileInfoDto.setOriginalFile(originalFileName);
				fileInfoDto.setSaveFile(saveFileName);
						
				file.transferTo(new File(folder, saveFileName));
			}
			requestUpdateDto.setUserProfile(fileInfoDto);
		}
		userService.modify(userId, requestUpdateDto);
		
		return ResponseEntity.ok().build();
	}

}

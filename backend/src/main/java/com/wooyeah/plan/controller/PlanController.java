package com.wooyeah.plan.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.wooyeah.auth.util.HeaderUtil;
import com.wooyeah.matching.service.MatchingService;
import com.wooyeah.plan.dto.*;
import com.wooyeah.plan.service.PlanService;
import com.wooyeah.user.dto.UserDto;
import com.wooyeah.user.entity.UserEntity;
import com.wooyeah.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/plan")
public class PlanController {
	
	private final PlanService planService;
	private final UserService userService;
	
	public PlanController(PlanService planService, UserService userService) {
		this.planService = planService;
		this.userService = userService;
	}
	
	@Value("${file.path.upload-planThumbnail}")
	private String uploadPlanThumbnailPath;
	
	// 계획 생성
	@PostMapping(value= "/regist", consumes= {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> regist(HttpServletRequest request, @RequestPart("data") RequestPlanRegistDto requestPlanRegistDto, @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
		
		// access token 정보 가져옴
		String accessToken = HeaderUtil.getAccessToken(request);
		// 유저 정보 가져옴
		UserDto user = userService.getUser(accessToken);
		
		requestPlanRegistDto.setWriter(user.getUserId());
		
		// file upload 관련
		if (file != null) {
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder = uploadPlanThumbnailPath + File.separator + today;
			
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
			requestPlanRegistDto.setPlanProfile(fileInfoDto);
		}
		
		planService.createPlan(requestPlanRegistDto);
		
		return ResponseEntity.ok().build();
		
	}
	
	// 계획 조회
	// 전체 계획 리스트 조회
	@GetMapping("/list")
	public ResponseEntity<?> list(HttpServletRequest request) {
		// access token 정보 가져옴
		String accessToken = HeaderUtil.getAccessToken(request);
		
		// 유저 정보 가져옴
		UserDto user = userService.getUser(accessToken);
		
		List<PlanListItemDto> plans = planService.list(user.getUserId());
		
		return ResponseEntity.ok().body(plans);
	}
	
	// 계획 상세 조회
	@GetMapping("/{plan-uuid}")
	public ResponseEntity<?> detail(@PathVariable("plan-uuid") String uuid){
		ResponseDetailDto responseDetailDto = planService.getDetail(uuid);
		
		return ResponseEntity.ok().body(responseDetailDto);
	}
	
	// 계획 수정
	
	// 계획 삭제	
	@DeleteMapping("/{plan-uuid}")
	public void deletePlan(HttpServletRequest request, @PathVariable("plan-uuid") String planUuid) {
		
		String accessToken = HeaderUtil.getAccessToken(request);				
		UserDto user = userService.getUser(accessToken);
		
		planService.removePlan(user.getUserId(), planUuid);
	}
}
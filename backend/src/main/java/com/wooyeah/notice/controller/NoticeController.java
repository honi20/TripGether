package com.wooyeah.notice.controller;

import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wooyeah.auth.util.HeaderUtil;
import com.wooyeah.notice.dto.*;
import com.wooyeah.notice.service.NoticeService;
import com.wooyeah.user.dto.UserDto;
import com.wooyeah.user.entity.UserEntity;
import com.wooyeah.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/notice")
//@CrossOrigin("*")
public class NoticeController {
	
	private final NoticeService noticeService;
	private final UserService userService;
	// 셍성자 주입
	public NoticeController(NoticeService noticeService, UserService userService) {
		this.noticeService = noticeService;
		this.userService = userService;
	}
	
	// Create
	// 신청에 대한 알림 생성
	@PostMapping("/create/apply/{matchingUuid}")
	public ResponseEntity<?> createApplyNotice(HttpServletRequest request, @PathVariable("matchingUuid") String matchingUuid, @RequestBody RequestCreateApplyDto requestCreateApplyDto) {
		// 현재 로그인한 사람의 id를 받아온다.
		String accessToken = HeaderUtil.getAccessToken(request);		
		UserDto user = userService.getUser(accessToken);
		
		// 알림 보내기
		noticeService.createApply(user.getUserId(), matchingUuid, requestCreateApplyDto);
		
		return ResponseEntity.ok().build();
	}
	
	// 응답에 대한 알림 생성 (나에게 신청 보낸 사람에게 다시 전달)
	@PostMapping("/create/return/{matchingUuid}")
	public ResponseEntity<?> createReturnNotice(HttpServletRequest request, @PathVariable("matchingUuid") String matchingUuid, @RequestBody RequestCreateReturnDto requestCreateReturnDto) {
		// 현재 로그인한 사람의 id를 받아온다.
		String accessToken = HeaderUtil.getAccessToken(request);		
		UserDto user = userService.getUser(accessToken);
		// 알림 보내기
		noticeService.createReturn(user.getUserId(), matchingUuid, requestCreateReturnDto);
		
		return ResponseEntity.ok().build();
	}
	
	// Read
	// 알림 리스트 조회
	@GetMapping("/list")
	public ResponseEntity<?> viewList(HttpServletRequest request) throws ParseException{
		// 현재 로그인한 사람의 id를 받아온다.
		String accessToken = HeaderUtil.getAccessToken(request);		
        UserDto user = userService.getUser(accessToken);
        String currentUserId = user.getUserId();
		
		// id로 받은 전체 알림을 조회한다.
		List<ResponseListDto> noticeList = noticeService.getTotalList(currentUserId);
		
		return ResponseEntity.ok(noticeList);
	}
	
	// 알림 상세 조회
	@GetMapping("/detail/{notice-uuid}")
	public ResponseEntity<?> readDetail(@PathVariable("notice-uuid") String uuid){
		ResponseDetailDto detailinfo = noticeService.getDetail(uuid);
		
		return ResponseEntity.ok(detailinfo);
	}
	
	// Update	
	// 알림 읽음 처리
	@PutMapping("/read/{notice-uuid}")
	public void read(@PathVariable("notice-uuid") String uuid) {
		// 해당 uuid를 가진 알림의 read_status를 변경한다.
		noticeService.readNotice(uuid);
	}
	
	
	// 신청 처리 (수락 or 거절)
	@PutMapping("/process/{notice-uuid}")
	public void process(@PathVariable("notice-uuid") String uuid) {
		// 해당 uuid를 가진 알림의 read_status를 변경한다.
		noticeService.processNotice(uuid);
	}
	
	
	// Delete
	// 알림 삭제
	@DeleteMapping("/{notice-uuid}")
	public void deleteNotice(@PathVariable("notice-uuid") String uuid ){
		// 해당 uuid를 가진 알림을 삭제한다.
		noticeService.removeNotice(uuid);
	}
	
}

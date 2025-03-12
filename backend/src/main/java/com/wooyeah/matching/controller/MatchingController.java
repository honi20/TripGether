package com.wooyeah.matching.controller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wooyeah.auth.util.HeaderUtil;
import com.wooyeah.matching.dto.RequestGroupManageDto;
import com.wooyeah.matching.dto.ResponseGroupMemberListDto;
import com.wooyeah.matching.dto.SearchDto;
import com.wooyeah.matching.dto.ThemeInfoDto;
import com.wooyeah.matching.dto.request.RequestModifyDto;
import com.wooyeah.matching.dto.request.RequestRegistDto;
import com.wooyeah.matching.dto.request.RequestSearchDto;
import com.wooyeah.matching.dto.response.ResponseListItemDto;
import com.wooyeah.matching.dto.response.ResponseMatchingDetailDto;
import com.wooyeah.matching.dto.response.ResponseSearchDto;
import com.wooyeah.matching.entity.MatchingEntity;
import com.wooyeah.matching.repository.MatchingRepository;
import com.wooyeah.matching.service.MatchingService;
import com.wooyeah.plan.dto.ResponseDetailDto;
import com.wooyeah.plan.service.PlanService;
import com.wooyeah.user.dto.UserDto;
import com.wooyeah.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/matching")
public class MatchingController {
	private final MatchingService matchingService;
	private final UserService userService;
	private final PlanService planService;
	
	// 생성자 주입
	public MatchingController(MatchingService matchingService, UserService userService, PlanService planService) {
		this.matchingService = matchingService;
		this.userService = userService; 
		this.planService = planService;
	}
	
	// 동행매칭 모집글 작성
	@PostMapping("/regist")
	public ResponseEntity<?> regist(HttpServletRequest request, @RequestBody RequestRegistDto requestRegistDto) throws ParseException {

		// 로그인 유저 정보
		String accessToken = HeaderUtil.getAccessToken(request);
		UserDto userDto = userService.getUser(accessToken);

		matchingService.registMatchingPost(userDto.getUserId(), requestRegistDto);
		
		return ResponseEntity.ok().build();
	}
	
	// 마감임박 게시글 k개 조회
	@GetMapping("/list/popular")
	public ResponseEntity<?> listOfPopular(@RequestParam(value="maxCnt") int maxCnt) {
		List<ResponseListItemDto> list = matchingService.getPopularList(maxCnt);
		return ResponseEntity.ok().body(list);
	}
	
	// 유저가 포함된 모든 매칭 정보 가져오기
	@GetMapping("/myList")
	public ResponseEntity<?> myList(HttpServletRequest request) {
		// 로그인 유저 정보
		String accessToken = HeaderUtil.getAccessToken(request);
		UserDto userDto = userService.getUser(accessToken);
					
		List<ResponseListItemDto> list = matchingService.getMyList(userDto.getUserId());
			
		return ResponseEntity.ok().body(list);
	}
	
	// 유저가 모집한 동행 매칭 리스트 전체 조회
	@GetMapping("/list/recruit")
	public ResponseEntity<?> listOfRecruit(HttpServletRequest request) {

		// 로그인 유저 정보
		String accessToken = HeaderUtil.getAccessToken(request);
		UserDto userDto = userService.getUser(accessToken);
		
		List<ResponseListItemDto> list = matchingService.getRecruitList(userDto.getUserId());
		
		return ResponseEntity.ok().body(list);
	}
	
	// 유저가 가입한 동행 매칭 리스트 전체 조회
	@GetMapping("/list/join")
	public ResponseEntity<?> listOfJoin(HttpServletRequest request) {
		// 로그인 유저 정보
		String accessToken = HeaderUtil.getAccessToken(request);
		UserDto userDto = userService.getUser(accessToken);
		
		List<ResponseListItemDto> list = matchingService.getJoinList(userDto.getUserId());
		
		return ResponseEntity.ok().body(list);
	}
	
	// 동행매칭 게시글 상세 조회
	@GetMapping("/detail/{matchingUuid}")
	public ResponseEntity<?> detail(@PathVariable("matchingUuid") String matchingUuid) {
		
		ResponseMatchingDetailDto matchingDetail = matchingService.getDetail(matchingUuid);
		
		return ResponseEntity.ok().body(matchingDetail);
	}
	
	// 동행매칭 모집글 삭제
	@DeleteMapping("/{matchingUuid}")
	public ResponseEntity<?> delete(HttpServletRequest request, @PathVariable("matchingUuid") String matchingUuid) {
		// 로그인 유저 정보
		String accessToken = HeaderUtil.getAccessToken(request);
		UserDto userDto = userService.getUser(accessToken);
				
		matchingService.deleteMatching(userDto.getUserId(), matchingUuid);
		return ResponseEntity.ok().build();
	}
	
	
	// 동행매칭 모집글 필터링 검색
	@GetMapping("/list/search")
	public ResponseEntity<?> search(RequestSearchDto requestSearchDto) {
		
		System.out.println(requestSearchDto);
		List<ResponseSearchDto> matchingList = matchingService.searchFilter(requestSearchDto);
		
		System.out.println(matchingList.size());
		return ResponseEntity.ok().body(matchingList);
	}
	
	// 동행매칭 게시글 클릭 시 조회수 증가
	@PutMapping("/view/{matchingUuid}")
	public ResponseEntity<?> increaseView(@PathVariable("matchingUuid") String matchingUuid) {
		matchingService.increaseViewCnt(matchingUuid);
		
		return ResponseEntity.ok().build();
	}
	
	// 동행매칭 모집글 수정
	@PutMapping("/modify/{matchingUuid}")
	public ResponseEntity<?> modify(@PathVariable("matchingUuid") String matchingUuid, @RequestBody RequestModifyDto requestModifyDto) {
		matchingService.modifyMatching(matchingUuid, requestModifyDto);
		
		return ResponseEntity.ok().build();
	}
	
	// 그룹에 구성원 생성
	@PostMapping("/group")
	public ResponseEntity<?> registerToGroup(@RequestBody RequestGroupManageDto requestGroupManageDto){
		matchingService.addToGroup(requestGroupManageDto);		
		return ResponseEntity.ok().build();
	}
	
	
	// 그룹에서 구성원 삭제
	@DeleteMapping("/group/{matchingUuid}/{userId}")
	public ResponseEntity<?> removeFromGroup(@PathVariable("matchingUuid") String matchingUuid, @PathVariable("userId") String userId){
		matchingService.deletefromGroup(matchingUuid, userId);
		return ResponseEntity.ok().build();
	}
	
	// 그룹 구성원 목록
	@GetMapping("/group/{matchingUuid}")
	public ResponseEntity<?> groupMemberList(@PathVariable("matchingUuid") String matchingUuid){
		
		List<ResponseGroupMemberListDto> memberList = matchingService.getGroupMemberList(matchingUuid);
		
		return ResponseEntity.ok().body(memberList);
	}
	
}

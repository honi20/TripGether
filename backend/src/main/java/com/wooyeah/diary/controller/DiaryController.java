package com.wooyeah.diary.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wooyeah.auth.util.HeaderUtil;
import com.wooyeah.diary.dto.FileInfoDto;
import com.wooyeah.diary.dto.RequestRegistDto;
import com.wooyeah.diary.dto.ResponseDiaryDetailDto;
import com.wooyeah.diary.dto.ResponseDiaryItemDto;
import com.wooyeah.diary.entity.DiaryEntity;
import com.wooyeah.diary.service.DiaryService;
import com.wooyeah.user.dto.UserDto;
import com.wooyeah.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/diary")
public class DiaryController {

	@Value("${file.path.upload-diaryImg}")
	private String uploadDiaryImgPath;

	private final DiaryService diaryService;
	private final UserService userService;

	public DiaryController(DiaryService diaryService, UserService userService) {
		this.diaryService = diaryService;
		this.userService = userService;
	}

	/**
	 * 개인 기록 등록
	 * 
	 * @return
	 * @throws Exception 
	 */
	@PostMapping(value = "/regist")
	public ResponseEntity<?> regist(HttpServletRequest request, @RequestPart("data") RequestRegistDto requestRegistDto,
			@RequestParam(value = "files", required = false) MultipartFile[] files) throws Exception {
		
		System.out.println(requestRegistDto);
		// file upload
		if (files != null && !files[0].isEmpty()) {
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder = uploadDiaryImgPath + File.separator + today;
			
			File folder = new File(saveFolder);
			if (!folder.exists())
				folder.mkdirs();
			
			List<FileInfoDto> fileInfos = new ArrayList<FileInfoDto>();
			
			for (MultipartFile mfile : files) {
				FileInfoDto fileInfoDto = new FileInfoDto();
				String originalFileName = mfile.getOriginalFilename();
				
				if (!originalFileName.isEmpty()) {
					String saveFileName = UUID.randomUUID().toString()
							+ originalFileName.substring(originalFileName.lastIndexOf('.'));
					
					fileInfoDto.setSaveFolder(today);
					fileInfoDto.setOriginalFile(originalFileName);
					fileInfoDto.setSaveFile(saveFileName);
					
					mfile.transferTo(new File(folder, saveFileName));
				}
				fileInfos.add(fileInfoDto);
			}
			requestRegistDto.setImages(fileInfos);
		}
		
		// 로그인 유저 정보
		String accessToken = HeaderUtil.getAccessToken(request);
		UserDto userDto = userService.getUser(accessToken);

		diaryService.registPost(userDto.getUserId(), requestRegistDto);

		return ResponseEntity.ok().build();
	}

	/**
	 * 개인 기록 조회
	 * 
	 * @return
	 * @throws ParseException
	 */
	@GetMapping("/list")
	public ResponseEntity<?> list(HttpServletRequest request) throws ParseException {
		// 로그인 유저 정보
		String accessToken = HeaderUtil.getAccessToken(request);
		UserDto userDto = userService.getUser(accessToken);
		
		List<ResponseDiaryItemDto> list = diaryService.getUserList(userDto.getUserId());

		return ResponseEntity.ok().body(list);
	}

	/**
	 * 기록 그룹별 조회
	 * 
	 * @return
	 */
	@GetMapping("/list/{matchingUuid}")
	public ResponseEntity<?> groupList(HttpServletRequest request, @PathVariable("matchingUuid") String matchingUuid) throws ParseException {
		// 로그인 유저 정보
		String accessToken = HeaderUtil.getAccessToken(request);
		UserDto userDto = userService.getUser(accessToken);
		
		List<ResponseDiaryItemDto> list = diaryService.getGroupList(userDto.getUserId(), matchingUuid);
		
		return ResponseEntity.ok().body(list);
	}

	/**
	 * 기록 상세 조회
	 * 
	 * @return
	 */
	@GetMapping("/detail/{diaryUuid}")
	public ResponseEntity<?> detail(@PathVariable("diaryUuid") String diaryUuid) {
		ResponseDiaryDetailDto diary = diaryService.getPostDetail(diaryUuid);
		return ResponseEntity.ok(diary);
	}

	/**
	 * 개인 기록 삭제
	 * 
	 * @return
	 */
	@DeleteMapping("/delete/{diaryUuid}")
	public ResponseEntity<?> delete(@PathVariable("diaryUuid") String diaryUuid) {
		diaryService.deletePost(diaryUuid);
		return ResponseEntity.ok().build();
	}
	
	/**
	 * 입력된 장소의 여행 기록 정보 검색
	 * 
	 */
	@GetMapping("/search")
	public ResponseEntity<?> search(@RequestParam("location") String location) {
		List<ResponseDiaryItemDto> list = diaryService.getRelatedPost(location);
		
		return ResponseEntity.ok().body(list);
	}
}

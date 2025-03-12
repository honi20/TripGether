package com.wooyeah.diary.service;

import java.util.List;

import org.json.simple.parser.ParseException;

import com.wooyeah.diary.dto.RequestRegistDto;
import com.wooyeah.diary.dto.ResponseDiaryDetailDto;
import com.wooyeah.diary.dto.ResponseDiaryItemDto;
import com.wooyeah.diary.entity.DiaryEntity;

public interface DiaryService {

	// 기록 작성
	void registPost(String userId, RequestRegistDto requestRegistDto) throws ParseException;

	// 유저 기록 조회
	List<ResponseDiaryItemDto> getUserList(String userId);
	
	// 그룹 기록 조회
	List<ResponseDiaryItemDto> getGroupList(String userId, String matchingUuid);

	// 기록 상세 조회
	ResponseDiaryDetailDto getPostDetail(String diaryUuid);

	// 기록 삭제
	void deletePost(String diaryUuid);

	// 장소 연관 기록 보이기
	List<ResponseDiaryItemDto> getRelatedPost(String location);
}

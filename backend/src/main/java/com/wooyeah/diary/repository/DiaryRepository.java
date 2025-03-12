package com.wooyeah.diary.repository;

import java.util.List;

import com.wooyeah.diary.dto.FileInfoDto;
import com.wooyeah.diary.dto.ImgGroupDto;
import com.wooyeah.diary.dto.RequestRegistDto;
import com.wooyeah.diary.dto.ResponseDiaryItemDto;
import com.wooyeah.diary.entity.DiaryEntity;
import com.wooyeah.diary.entity.DiaryImgEntity;

public interface DiaryRepository {

	// 기록 정보 저장 - matching_id 포함
	void savePostInfoWithMatchingId(String userId, int matchingId, RequestRegistDto requestRegistDto);
	
	// 기록 정보 저장 - matching_id 제외
	void savePostInfo(String userId, RequestRegistDto requestRegistDto);

	// 기록 이미지 리스트 저장
	void savePostImages(List<FileInfoDto> images);

	// 기록 이미지 그룹 리스트 저장
	void saveImgGroup(List<ImgGroupDto> imgGroupDtoList);

	// 유저의 기록 게시물 조회
	List<DiaryEntity> getUserDiaryList(String userId);

	// 그룹의 기록 게시물 조회
	List<DiaryEntity> getGroupDiaryList(String userId, int matchingId);

	// 기록의 이미지 리스트 조회
	List<DiaryImgEntity> getDiaryImgList(int diaryId);

	// 기록 기본 정보 조회
	DiaryEntity getDiaryInfo(String diaryUuid);

	// 기록 관련 이미지들 삭제
	void deleteDiaryImages(int diaryId);

	// 기록 정보 삭제
	void deleteDiary(int diaryId);

	// 장소 연관 기록 검색
	List<DiaryEntity> getRelatedDiaryList(String location);

}

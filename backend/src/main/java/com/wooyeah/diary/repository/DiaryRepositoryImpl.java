package com.wooyeah.diary.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wooyeah.diary.dto.FileInfoDto;
import com.wooyeah.diary.dto.ImgGroupDto;
import com.wooyeah.diary.dto.RequestRegistDto;
import com.wooyeah.diary.dto.ResponseDiaryItemDto;
import com.wooyeah.diary.entity.DiaryEntity;
import com.wooyeah.diary.entity.DiaryImgEntity;
import com.wooyeah.diary.mapper.DiaryMapper;

@Repository
public class DiaryRepositoryImpl implements DiaryRepository {

	private final DiaryMapper diaryMapper;
	
	public DiaryRepositoryImpl(DiaryMapper diaryMapper) {
		this.diaryMapper = diaryMapper;
	}
	
	@Override
	public void savePostInfoWithMatchingId(String userId, int matchingId, RequestRegistDto requestRegistDto) {
		Map<String, Object> info = new HashMap<>();
		info.put("userId", userId);
		info.put("matchingId", matchingId);
		info.put("dto", requestRegistDto);
		
		diaryMapper.insertDiaryAndMatchingId(info);
	}
	
	@Override
	public void savePostInfo(String userId, RequestRegistDto requestRegistDto) {
		Map<String, Object> info = new HashMap<>();
		info.put("userId", userId);
		info.put("dto", requestRegistDto);
		
		diaryMapper.insertDiary(info);
	}

	@Override
	public void savePostImages(List<FileInfoDto> images) {
		diaryMapper.insertDiaryImg(images);
	}

	@Override
	public void saveImgGroup(List<ImgGroupDto> imgGroupDtoList) {
		diaryMapper.insertImgGroup(imgGroupDtoList);
	}

	@Override
	public List<DiaryEntity> getUserDiaryList(String userId) {
		return diaryMapper.selectDiaryByWriterId(userId);
	}

	@Override
	public List<DiaryEntity> getGroupDiaryList(String userId, int matchingId) {
		return diaryMapper.selectDiaryByMatchingId(userId, matchingId);
	}
	
	@Override
	public List<DiaryImgEntity> getDiaryImgList(int diaryId) {
		return diaryMapper.selectDiaryImgById(diaryId);
	}

	@Override
	public DiaryEntity getDiaryInfo(String diaryUuid) {
		return diaryMapper.selectDiaryByUuid(diaryUuid);
	}

	@Override
	public void deleteDiaryImages(int diaryId) {
		diaryMapper.deleteDiaryImgByDiaryId(diaryId);
	}

	@Override
	public void deleteDiary(int diaryId) {
		diaryMapper.deleteDiaryById(diaryId);
	}

	@Override
	public List<DiaryEntity> getRelatedDiaryList(String location) {
		return diaryMapper.selectDiaryByLocation(location);
	}

}
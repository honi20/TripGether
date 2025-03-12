package com.wooyeah.diary.service;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wooyeah.diary.dto.FileInfoDto;
import com.wooyeah.diary.dto.ImgGroupDto;
import com.wooyeah.diary.dto.RequestRegistDto;
import com.wooyeah.diary.dto.ResponseDiaryDetailDto;
import com.wooyeah.diary.dto.ResponseDiaryItemDto;
import com.wooyeah.diary.entity.DiaryEntity;
import com.wooyeah.diary.entity.DiaryImgEntity;
import com.wooyeah.diary.repository.DiaryRepository;
import com.wooyeah.matching.entity.MatchingEntity;
import com.wooyeah.matching.repository.MatchingRepository;
import com.wooyeah.user.dto.UserDto;
import com.wooyeah.user.entity.ProfileEntity;
import com.wooyeah.user.entity.UserEntity;
import com.wooyeah.user.repository.UserRepository;


@Service
public class DiaryServiceImpl implements DiaryService {

	@Value("${file.path.upload-diaryImg}")
	private String uploadDiaryImgPath;
	
	@Value("${file.path.upload-profiles}")
	private String uploadProfilePath;

	private final DiaryRepository diaryRepository;
	private final MatchingRepository matchingRepository;
	private final UserRepository userRepository;
	
	public DiaryServiceImpl(DiaryRepository diaryRepository, MatchingRepository matchingRepository, UserRepository userRepository) {
		this.diaryRepository = diaryRepository;
		this.matchingRepository = matchingRepository;
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public void registPost(String userId, RequestRegistDto requestRegistDto) throws ParseException {

		// matching uuid를 통해 matching id를 구한다.
		MatchingEntity matching = matchingRepository.getMatchingInfo(requestRegistDto.getMatchingUuid());
		
		// 기록에 동행 매칭이 연결되어 있다면, 해당 매칭 id 저장
		if (matching != null) {
			diaryRepository.savePostInfoWithMatchingId(userId, matching.getId(), requestRegistDto);
		}
		// 동행 매칭이 연결되어 있지 않다면(개인적인 기록이라면), 기록 정보만 저장 (matching_id default null)
		else {
			diaryRepository.savePostInfo(userId, requestRegistDto);
		}
		
		
		// 이미지 정보를 저장한다.
		List<FileInfoDto> imgFiles = requestRegistDto.getImages();
		if (imgFiles != null && !imgFiles.isEmpty()) {
			// 이미지 파일 저장
			diaryRepository.savePostImages(requestRegistDto.getImages());
			
			// 이미지 파일 그룹 저장
			List<ImgGroupDto> imgGroupDtoList = new ArrayList<>();
			for (FileInfoDto file : imgFiles) {
				ImgGroupDto imgGroupDto = new ImgGroupDto();
				imgGroupDto.setDiaryId(requestRegistDto.getId());
				imgGroupDto.setDiaryImgId(file.getId());
				
				imgGroupDtoList.add(imgGroupDto);
			}
			diaryRepository.saveImgGroup(imgGroupDtoList);
		}
	}

	@Override
	public List<ResponseDiaryItemDto> getUserList(String userId) {
		// 유저 diary entity 리스트
		List<DiaryEntity> diaryList = diaryRepository.getUserDiaryList(userId);
		
		// entity 리스트로부터 응답 dto 리스트 구성하기
		return getResponseDiaryList(diaryList);
	}
	
	@Override
	public List<ResponseDiaryItemDto> getGroupList(String userId, String matchingUuid) {
		// matching uuid로부터 matching entity 구하기
		MatchingEntity matching = matchingRepository.getMatchingInfo(matchingUuid);
		
		// 그룹 diary entity 리스트
		List<DiaryEntity> diaryList = diaryRepository.getGroupDiaryList(userId, matching.getId());
				
		// entity 리스트로부터 응답 dto 리스트 구성하기
		return getResponseDiaryList(diaryList);
	}

	@Override
	public List<ResponseDiaryItemDto> getRelatedPost(String location) {
		// 그룹 diary entity 리스트
		List<DiaryEntity> diaryList = diaryRepository.getRelatedDiaryList(location);
		
		// entity 리스트로부터 응답 dto 리스트 구성하기
		return getResponseDiaryList(diaryList);
	}
	
	// entity 리스트로부터 응답 dto 리스트 구성하기
	private List<ResponseDiaryItemDto> getResponseDiaryList(List<DiaryEntity> diaryList) {
		// 응답 dto 리스트
		List<ResponseDiaryItemDto> result = new ArrayList<>();
		
		
		for (DiaryEntity diary : diaryList) {
			
			// 응답 dto를 만들어서 diary의 uuid 저장
			ResponseDiaryItemDto item = new ResponseDiaryItemDto();
			item.setDiaryUuid(diary.getUuid());

			// writerId로부터 유저의 이름과 프로필 가져오기
			UserEntity user = userRepository.getUserInfo(diary.getWriter_id());
			item.setWriterId(user.getId());
			item.setWriterName(user.getName());
			ProfileEntity userImg = userRepository.getUserProfile(diary.getWriter_id());
			if (userImg != null && userImg.getSave_folder() != null)
				item.setWriterProfile(uploadProfilePath + "/" + userImg.getSave_folder() + "/" + userImg.getSave_file());
			
			// diary의 image entity 리스트
			List<DiaryImgEntity> imgList = diaryRepository.getDiaryImgList(diary.getId());

			if (imgList != null) {				

				// diary_id로부터 해당 기록에 저장된 이미지 파일 경로 리스트 가져오기
				List<String> imgPathList = new ArrayList<>();
				for (DiaryImgEntity img : imgList) {
					String imgPath = uploadDiaryImgPath + "/" + img.getSave_folder() + "/" + img.getSave_file();
					imgPathList.add(imgPath);
				}
				// 응답 dto에 이미지 리스트 저장
				item.setImages(imgPathList);
			}
			
			result.add(item);
		}
		
		return result;
	}

	@Override
	public ResponseDiaryDetailDto getPostDetail(String diaryUuid) {
		
		// 응답 dto
		ResponseDiaryDetailDto result = new ResponseDiaryDetailDto();
		
		// uuid로부터 diary entity 가져옴
		DiaryEntity diary = diaryRepository.getDiaryInfo(diaryUuid);
		getResponseInfoByEntity(result, diary);
		
		// diary_id로부터 해당 기록의 이미지 entity 리스트 조회
		List<DiaryImgEntity> imgList = diaryRepository.getDiaryImgList(diary.getId());
		
		if (imgList != null) {				
			// diary_id로부터 해당 기록에 저장된 이미지 파일 경로 리스트 가져오기
			List<String> imgPathList = new ArrayList<>();
			
			for (DiaryImgEntity img : imgList) {
				String imgPath = uploadDiaryImgPath + "/" + img.getSave_folder() + "/" + img.getSave_file();
				imgPathList.add(imgPath);
			}
			result.setImages(imgPathList);
		}
		
		return result;
	}

	private void getResponseInfoByEntity(ResponseDiaryDetailDto result, DiaryEntity diary) {
		result.setDiaryUuid(diary.getUuid());
		result.setWriterId(diary.getWriter_id());
		result.setContent(diary.getContent());
		result.setLocation(diary.getLocation());
		result.setPublicStatus(diary.getPublic_status());
		result.setTravelDate(diary.getTravel_date());
		result.setCreated_at(diary.getCreated_at());
		
		// writerId로부터 유저의 이름과 프로필 가져오기
		UserEntity user = userRepository.getUserInfo(diary.getWriter_id());
		result.setWriterName(user.getName());
		ProfileEntity userImg = userRepository.getUserProfile(diary.getWriter_id());
		if (userImg.getSave_folder() != null)
			result.setWriterProfile(uploadProfilePath + "/" + userImg.getSave_folder() + "/" + userImg.getSave_file());
	}

	@Override
	@Transactional
	public void deletePost(String diaryUuid) {
		// diaryUuid로부터 diary entity 가져온다.
		DiaryEntity diary = diaryRepository.getDiaryInfo(diaryUuid);
		
		// diary_id로부터 해당 기록의 diary_img_id들을 찾아 삭제한다.
		diaryRepository.deleteDiaryImages(diary.getId());
		
		// diary_id의 diary 데이터를 삭제한다.
		diaryRepository.deleteDiary(diary.getId());
		
	}

}

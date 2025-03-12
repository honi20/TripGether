package com.wooyeah.matching.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wooyeah.matching.dto.*;
import com.wooyeah.matching.dto.request.RequestModifyDto;
import com.wooyeah.matching.dto.request.RequestRegistDto;
import com.wooyeah.matching.dto.request.RequestSearchDto;
import com.wooyeah.matching.dto.response.ResponseListItemDto;
import com.wooyeah.matching.dto.response.ResponseMatchingDetailDto;
import com.wooyeah.matching.dto.response.ResponseSearchDto;
import com.wooyeah.matching.dto.response.ResponseThemeDto;
import com.wooyeah.matching.entity.MatchingEntity;
import com.wooyeah.matching.entity.ThemeEntity;
import com.wooyeah.matching.repository.MatchingRepository;
import com.wooyeah.notice.dto.RequestCreateDeleteDto;
import com.wooyeah.notice.repository.NoticeRepository;
import com.wooyeah.plan.dto.ResponseDetailPlanDto;
import com.wooyeah.plan.entity.PlanEntity;
import com.wooyeah.plan.entity.PlanImageEntity;
import com.wooyeah.plan.repository.PlanRepository;
import com.wooyeah.user.dto.UserDto;
import com.wooyeah.user.entity.UserEntity;
import com.wooyeah.user.repository.UserRepository;

@Service
public class MatchingServiceImpl implements MatchingService{
	
	@Value("${file.path.upload-planThumbnail}")
	private String uploadPlanImagePath;
	
	private final MatchingRepository matchingRepository;
	private final UserRepository userRepository;
	private final PlanRepository planRepository;
	private final NoticeRepository noticeRepository;
	
	public MatchingServiceImpl(UserRepository userRepository, MatchingRepository matchingRepository, PlanRepository planRepository, NoticeRepository noticeRepository) {
		this.userRepository = userRepository;
		this.matchingRepository = matchingRepository;
		this.planRepository = planRepository;
		this.noticeRepository = noticeRepository;
	}

	@Override
	@Transactional
	public void registMatchingPost(String writerId, RequestRegistDto requestRegistDto) {
		// plan의 uuid로부터 plan_id를 가져옴
		ResponseDetailPlanDto plan = planRepository.getPlanDetail(requestRegistDto.getPlanUuid());
		int planId = plan.getId();
				
		// 테마에 해당하는 정보를 theme 테이블에 저장
		matchingRepository.saveThemes(requestRegistDto.getThemes());
		
		// 동행매칭 정보를 matching 테이블에 저장
		matchingRepository.saveMatchingPost(writerId, planId, requestRegistDto);
		
		// 테이블에 저장된 matching_id와 작성자 id를 group 테이블에 저장
		matchingRepository.saveTripGroup(writerId, requestRegistDto.getId());
		
	}

	@Override
	public List<ResponseListItemDto> getPopularList(int maxCnt) {
		return matchingRepository.getPopularMatching(maxCnt);
	}
	

	@Override
	public List<ResponseListItemDto> getMyList(String userId) {
		List<ResponseListItemDto> recruitList = matchingRepository.getRecruitMatching(userId);
		List<ResponseListItemDto> joinList = matchingRepository.getJoinMatching(userId);
		
		List<ResponseListItemDto> list = new ArrayList<>();
		Collections.addAll(list, recruitList.toArray(new ResponseListItemDto[0]));
		Collections.addAll(list, joinList.toArray(new ResponseListItemDto[0]));
		
		return list;
	}
	
	@Override
	public List<ResponseListItemDto> getRecruitList(String userId) {
		return matchingRepository.getRecruitMatching(userId);
	}

	@Override
	public List<ResponseListItemDto> getJoinList(String userId) {
		return matchingRepository.getJoinMatching(userId);
	}

	@Override
	public ResponseMatchingDetailDto getDetail(String matchingUuid) {
		
		// 동행매칭 기본 정보 가져오기
		MatchingEntity matching = matchingRepository.getMatchingInfo(matchingUuid);
		
		// 매칭의 plan_id로부터 plan entity 가져오기
		PlanEntity plan = planRepository.getPlanInfo(matching.getPlan_id());
		
		// 매칭의 plan_id로부터 totalBudget 가져오기
		int totalBudget = planRepository.getTotalBudget(matching.getPlan_id());
		
		// 매칭의 writer_id로부터 user entity 가져오기
		UserEntity user = userRepository.getUserInfo(matching.getWriter_id());
				
		// 매칭의 theme_id로부터 theme entity 가져오기
		ThemeEntity themes = matchingRepository.getThemesInfo(matching.getTheme_id());
		
		ResponseMatchingDetailDto result = new ResponseMatchingDetailDto();
		setMatchingDetailInfo(result, matching, plan, user, themes);
		result.setTotalBudget(totalBudget);
		
		return result;
	}

	private void setMatchingDetailInfo(ResponseMatchingDetailDto result, MatchingEntity matching, PlanEntity plan, UserEntity user, ThemeEntity themes) {
		result.setMatchingUuid(matching.getUuid());
		result.setTitle(matching.getTitle());
		result.setWriterId(matching.getWriter_id());
		result.setWriterName(user.getName());
		result.setWriterGender(user.getGender());
		result.setWriterBirth(user.getBirthday().getYear());
		result.setRegion(matching.getRegion());
		result.setStartDate(plan.getStart_date());
		result.setEndDate(plan.getEnd_date());
		result.setRecruitPersonCnt(matching.getRecruit_person_cnt());
		result.setCurrentPersonCnt(matching.getCurrent_person_cnt());
		result.setDescription(matching.getDescription());
		result.setPlanUuid(plan.getUuid());
		
		ResponseThemeDto themeDto = new ResponseThemeDto();
		themeDto.setForEnergetic(themes.isFor_energetic());
		themeDto.setForLeisurely(themes.isFor_leisurely());
		themeDto.setForNatural(themes.isFor_natural());
		themeDto.setForUrban(themes.isFor_urban());
		themeDto.setForHealing(themes.isFor_healing());
		themeDto.setForShopping(themes.isFor_shopping());
		themeDto.setForMorning(themes.isFor_morning());
		themeDto.setForNightOwl(themes.isFor_night_owl());
		result.setThemes(themeDto);
	}

	@Override
	public void deleteMatching(String userId, String matchingUuid) {
		MatchingEntity matching = matchingRepository.getMatchingInfo(matchingUuid);
		
		// 해당 매칭 그룹에 속한 그룹원 아이디 정보 받기
		List<String> groupInfo = matchingRepository.getGroupInfo(userId, matching.getId());
//		System.out.println(groupInfo.toString());
		
		// 해당 그룹원들에게 삭제 알림 보내기
		if (groupInfo != null && !groupInfo.isEmpty()) {
			List<RequestCreateDeleteDto> notices = new ArrayList<>();
			for (String receiverId : groupInfo) {
				RequestCreateDeleteDto notice = new RequestCreateDeleteDto();
				notice.setType("삭제 알림");
				notice.setTitle(matching.getTitle() + " 여행 매칭 게시글이 삭제되었습니다.");
				notice.setSenderId(userId);
				notice.setReceiverId(receiverId);
				notice.setMatchingId(matching.getId());
				notices.add(notice);
			}
			noticeRepository.saveDeleteNotice(notices);	
		}
		
		// 매칭 게시글 삭제
		matchingRepository.deleteMatchingPost(matchingUuid);
	}

	@Override
	public List<ResponseSearchDto> searchFilter(RequestSearchDto requestSearchDto) {
		
		// 정렬 타입을 기준과 순서로 분리
		String sortType = requestSearchDto.getSortType();
		if (sortType != null && sortType.equals("최신순")) {
			requestSearchDto.setSortCriteria("start_date");
			requestSearchDto.setSortOrder("asc");
		}
		else if (sortType != null && sortType.equals("인기순")) {
			requestSearchDto.setSortCriteria("viewCnt");
			requestSearchDto.setSortOrder("desc");
		}
		
		List<ResponseSearchDto> result = matchingRepository.getMatchingBySearchFilter(requestSearchDto);
		
		// 각 result의 plan_id를 통해 plan_img 가져오기
		for (ResponseSearchDto item : result) {
			PlanImageEntity img = planRepository.getPlanImage(item.getPlan_id());
//			System.out.println(img);
			
			if (img != null)
				item.setPlan_img(uploadPlanImagePath + "/" + img.getSave_folder() + "/" + img.getSave_file());
		}
		
		return result;
	}

	
	// 동행매칭 모집글 수정
	private void getThemeDto(RequestSearchDto requestSearchDto, ThemeInfoDto themeDto) {
		List<String> theme = requestSearchDto.getTheme();
		for (String themeName : theme) {
			if (themeName.equals("forEnergetic"))
				themeDto.setForEnergetic(true);
			if (themeName.equals("forLeisurely"))
				themeDto.setForLeisurely(true);
			if (themeName.equals("forNatural"))
				themeDto.setForNatural(true);
			if (themeName.equals("forUrban"))
				themeDto.setForUrban(true);
			if (themeName.equals("forHealing"))
				themeDto.setForHealing(true);
			if (themeName.equals("forShopping"))
				themeDto.setForHealing(true);
			if (themeName.equals("forMorning"))
				themeDto.setForMorning(true);
			if (themeName.equals("forNightOwl"))
				themeDto.setForNightOwl(true);
		}
	}

	@Override
	public void increaseViewCnt(String matchingUuid) {
		matchingRepository.updateViewCnt(matchingUuid);
		
	}

	@Override
	public void modifyMatching(String matchingUuid, RequestModifyDto requestModifyDto) {
		// matchingUuid에 해당하는 matching 정보 가져옴
		MatchingEntity matching = matchingRepository.getMatchingInfo(matchingUuid);
		
		// 해당 매칭 정보의 theme_id를 얻어서 theme 정보 업데이트
		matchingRepository.updateThemes(matching.getTheme_id(), requestModifyDto.getThemes());
		
		// 매칭 게시글 기본 정보(상세설명) 업데이트
		matchingRepository.updateMatchingInfo(matching.getId(), requestModifyDto.getDescription());
	}

	@Override
	public void addToGroup(RequestGroupManageDto requestGroupManageDto) {
		// matchingUuid에 해당하는 matching 정보 가져옴
		MatchingEntity matching = matchingRepository.getMatchingInfo(requestGroupManageDto.getMatchingUuid());
		matchingRepository.insertToGroup(matching.getId(), requestGroupManageDto.getUserId());
		matchingRepository.increaseMemberCnt(matching.getId());
	}

	@Override
	public void deletefromGroup(RequestGroupManageDto requestGroupManageDto) {
		// matchingUuid에 해당하는 matching 정보 가져옴
		MatchingEntity matching = matchingRepository.getMatchingInfo(requestGroupManageDto.getMatchingUuid());
		matchingRepository.deleteFromGroup(matching.getId(), requestGroupManageDto.getUserId());
		matchingRepository.decreaseMemberCnt(matching.getId());
		
	}

	@Override
	public void deletefromGroup(String matchingUuid, String userId) {
		// matchingUuid에 해당하는 matching 정보 가져옴
				MatchingEntity matching = matchingRepository.getMatchingInfo(matchingUuid);
				matchingRepository.deleteFromGroup(matching.getId(), userId);
				matchingRepository.decreaseMemberCnt(matching.getId());
		
	}

	@Override
	public List<ResponseGroupMemberListDto> getGroupMemberList(String matchingUuid) {
		// matchingUuid에 해당하는 matching 정보 가져옴
		MatchingEntity matching = matchingRepository.getMatchingInfo(matchingUuid);
		List<ResponseGroupMemberListDto> memberList = matchingRepository.getGroupMemberList(matching.getId());
		return memberList;
	}
}

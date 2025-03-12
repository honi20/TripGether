package com.wooyeah.matching.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wooyeah.matching.dto.*;
import com.wooyeah.matching.dto.request.RequestRegistDto;
import com.wooyeah.matching.dto.request.RequestSearchDto;
import com.wooyeah.matching.dto.response.ResponseListItemDto;
import com.wooyeah.matching.dto.response.ResponseSearchDto;
import com.wooyeah.matching.entity.MatchingEntity;
import com.wooyeah.matching.entity.ThemeEntity;
import com.wooyeah.matching.mapper.MatchingMapper;

@Repository
public class MatchingRepositoryImpl implements MatchingRepository{
	private final MatchingMapper matchingMapper;
	
	public MatchingRepositoryImpl(MatchingMapper matchingMapper) {
		this.matchingMapper = matchingMapper;
	}

	@Override
	public void saveThemes(ThemeInfoDto themes) {
		matchingMapper.insertTheme(themes);
	}

	@Override
	public void saveMatchingPost(String writerId, int planId, RequestRegistDto requestRegistDto) {
		Map<String, Object> info = new HashMap<>();
		info.put("writerId", writerId);
		info.put("planId", planId);
		info.put("themeId", requestRegistDto.getThemes().getId());
		info.put("dto", requestRegistDto);
		
		matchingMapper.insertMatching(info);
	}

	@Override
	public void saveTripGroup(String userId, int matchingId) {
		Map<String, Object> info = new HashMap<>();
		info.put("userId", userId);
		info.put("matchingId", matchingId);
		
		matchingMapper.insertTripGroup(info);
	}
	
	@Override
	public MatchingEntity getMatchingInfo(String matchingUuid) {
		return matchingMapper.selectByUuid(matchingUuid);
	}

	@Override
	public List<ResponseListItemDto> getPopularMatching(int maxCnt) {
		// (모집 인원 - 현재 인원) 값을 오름차순 정렬한 후 앞의 3개의 데이터 return
		return matchingMapper.selectOrderByPersonCnt(maxCnt);
	}
	
	@Override
	public List<ResponseListItemDto> getRecruitMatching(String userId) {	
		return matchingMapper.selectRecruitMatchingByUserId(userId);
	}

	@Override
	public List<ResponseListItemDto> getJoinMatching(String userId) {
		return matchingMapper.selectJoinMatchingByUserId(userId);
	}

	@Override
	public ThemeEntity getThemesInfo(int themeId) {
		return matchingMapper.selectThemeById(themeId);
	}

	@Override
	public List<String> getGroupInfo(String userId, int matchingId) {
		Map<String, Object> info = new HashMap<>();
		info.put("userId", userId);
		info.put("matchingId", matchingId);
		
		return matchingMapper.getTripGroupByUserIdAndMatchingId(info);
	}

	@Override
	public void deleteMatchingPost(String matchingUuid) {
		matchingMapper.deleteMatchingByUuid(matchingUuid);
	}

	@Override
	public List<ResponseSearchDto> getMatchingBySearchFilter(RequestSearchDto requestSearchDto) {
		
		// 테마가 같은 필터 id 리스트를 가져온다.
		List<Integer> themeIdList = matchingMapper.selectThemeIdBySearchFilter(requestSearchDto.getTheme());
		
		requestSearchDto.setThemeId(themeIdList);
		
		// 해당 테마의 검색 조건에 맞는 matching 정보를 가져온다.
		return matchingMapper.selectMatchingBySearchFilter(requestSearchDto);
	}

	@Override
	public void updateViewCnt(String matchingUuid) {
		matchingMapper.updateViewCntByUuid(matchingUuid);
		
	}

	@Override
	public void updateThemes(int themeId, ThemeInfoDto themes) {
		Map<String, Object> info = new HashMap<>();
		info.put("themeId", themeId);
		info.put("themes", themes);
		System.out.println(themeId);
		System.out.println(themes.toString());
		matchingMapper.updateThemesById(info);
	}

	@Override
	public void updateMatchingInfo(int matchingId, String description) {
		Map<String, Object> info = new HashMap<>();
		info.put("matchingId", matchingId);
		info.put("description", description);
		matchingMapper.updateMatchingInfoById(info);
	}

	@Override
	public void insertToGroup(int matchingId, String userId) {
		Map<String, Object> info = new HashMap<>();
		info.put("userId", userId);
		info.put("matchingId", matchingId);
		matchingMapper.insertUserIdToGroup(info);
	}

	@Override
	public void deleteFromGroup(int matchingId, String userId) {
		Map<String, Object> info = new HashMap<>();
		info.put("userId", userId);
		info.put("matchingId", matchingId);
		matchingMapper.deleteUserIdFromGroup(info);
		
	}

	@Override
	public void increaseMemberCnt(int matchingId) {
		matchingMapper.increaseMatchingCurrentCnt(matchingId);
		
	}

	@Override
	public void decreaseMemberCnt(int matchingId) {
		matchingMapper.decreaseMatchingCurrentCnt(matchingId);
		
	}

	@Override
	public List<ResponseGroupMemberListDto> getGroupMemberList(int matchingId) {
		List<ResponseGroupMemberListDto> memberList = matchingMapper.selectUserByMatchingid(matchingId);
		return memberList;
	}

	@Override
	public List<String> getMatchingUuid(int planId) {
		return matchingMapper.selectMatchingUuidByPlanId(planId);
	}
}

package com.wooyeah.matching.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wooyeah.matching.dto.*;
import com.wooyeah.matching.dto.request.RequestRegistDto;
import com.wooyeah.matching.dto.request.RequestSearchDto;
import com.wooyeah.matching.dto.response.ResponseListItemDto;
import com.wooyeah.matching.dto.response.ResponseSearchDto;
import com.wooyeah.matching.dto.response.ResponseThemeDto;
import com.wooyeah.matching.entity.MatchingEntity;
import com.wooyeah.matching.entity.ThemeEntity;

@Repository
public interface MatchingRepository {

	// 등록 - 테마 저장
	void saveThemes(ThemeInfoDto themes);

	// 등록 - 매칭 게시글 저장
	void saveMatchingPost(String writerId, int planId, RequestRegistDto requestRegistDto);

	// 등록 - 그룹 생성
	void saveTripGroup(String userId, int matchingId);
	
	// matching uuid에 해당하는 matching entity 제공
	MatchingEntity getMatchingInfo(String matchingUuid);

	// 리스트 조회 - 인원 마감 임박
	List<ResponseListItemDto> getPopularMatching(int maxCnt);
	
	// 리스트 조회 - 유저 모집 전체
	List<ResponseListItemDto> getRecruitMatching(String userId);

	// 리스트 조회 - 유저 가입 전체
	List<ResponseListItemDto> getJoinMatching(String userId);

	// 상세 조회 - 게시글 테마 정보
	ThemeEntity getThemesInfo(int themeId);

	// 삭제 - 매칭 그룹에 속한 그룹원 정보
	List<String> getGroupInfo(String userId, int matchingId);

	// 삭제 - 매칭 게시글 삭제
	void deleteMatchingPost(String matchingUuid);

	// 필터링 검색
	List<ResponseSearchDto> getMatchingBySearchFilter(RequestSearchDto requestSearchDto);

	// 조회수 증가
	void updateViewCnt(String matchingUuid);

	// 테마 정보 업데이트
	void updateThemes(int themeId, ThemeInfoDto themes);

	// 매칭 게시글 상세설명 업데이트
	void updateMatchingInfo(int matchingId, String description);

	// 그룹에 user insert
	void insertToGroup(int matchingId, String userId);

	// 그룹에서 user Delete
	void deleteFromGroup(int matchingId, String userId);

	// 그룹에서 멤버수 +1
	void increaseMemberCnt(int matchingId);

	// 그룹에서 멤버수 -1
	void decreaseMemberCnt(int matchingId);

	List<ResponseGroupMemberListDto> getGroupMemberList(int matchingId);

	List<String> getMatchingUuid(int planId);


}

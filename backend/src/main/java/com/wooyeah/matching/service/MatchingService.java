package com.wooyeah.matching.service;

import java.util.List;

import com.wooyeah.matching.dto.*;
import com.wooyeah.matching.dto.request.RequestModifyDto;
import com.wooyeah.matching.dto.request.RequestRegistDto;
import com.wooyeah.matching.dto.request.RequestSearchDto;
import com.wooyeah.matching.dto.response.ResponseListItemDto;
import com.wooyeah.matching.dto.response.ResponseMatchingDetailDto;
import com.wooyeah.matching.dto.response.ResponseSearchDto;

public interface MatchingService {
	
	// 매칭 게시글 등록
	void registMatchingPost(String writerId, RequestRegistDto requestRegistDto);

	// 인원 마감 임박 게시글 조회
	List<ResponseListItemDto> getPopularList(int maxCnt);
	
	// 유저가 포함된 모든 게시글 리스트 조회
	List<ResponseListItemDto> getMyList(String userId);
		
	// 유저가 모집한 게시글 전체 리스트 조회
	List<ResponseListItemDto> getRecruitList(String userId);

	// 유저가 가입한 게시글 전체 리스트 조회
	List<ResponseListItemDto> getJoinList(String userId);

	// 동행매칭 게시글 상세보기
	ResponseMatchingDetailDto getDetail(String matchingUuid);

	// 동행매칭 게시글 삭제
	void deleteMatching(String userId, String matchingUuid);

	// 동행매칭 필더링 검색
	List<ResponseSearchDto> searchFilter(RequestSearchDto requestSearchDto);

	// 게시글 클릭 시 
	void increaseViewCnt(String matchingUuid);

	// 게시글 수정
	void modifyMatching(String matchingUuid, RequestModifyDto requestModifyDto);

	// 그룹에 등록
	void addToGroup(RequestGroupManageDto requestGroupManageDto);

	// 그룹에서 삭제
	void deletefromGroup(RequestGroupManageDto requestGroupManageDto);

	void deletefromGroup(String matchingUuid, String userId);

	// 그룹 멤버 리스트 반환
	List<ResponseGroupMemberListDto> getGroupMemberList(String matchingUuid);

}

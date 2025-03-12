package com.wooyeah.notice.repository;

import java.util.List;

import com.wooyeah.notice.dto.*;

public interface NoticeRepository {

	List<ResponseListDto> selectReceivedList(String userId);

	ResponseDetailNoticeDto selectNoticeDetail(String uuid);
	ResponseDetailUserDto selectUserDetail(String sender_id);
	ResponseDetailMatchingDto selectMatchingDetail(int matching_id);

	void updateReadStatus(String uuid);
	
	void updateProcessedStatus(String uuid);

	void deleteNotice(String uuid);

	// 신청 알림 저장
	void saveApplyNotice(int matchingId, String userId, String receiverId, RequestCreateApplyDto requestCreateApplyDto);

	// 응답 알림 저장
	void saveReturnNotice(int matchingId, String userId, RequestCreateReturnDto requestCreateReturnDto);

	// 삭제 알림 저장
	void saveDeleteNotice(List<RequestCreateDeleteDto> notices);
}

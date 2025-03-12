package com.wooyeah.notice.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wooyeah.notice.dto.*;
import com.wooyeah.notice.mapper.NoticeMapper;

@Repository
public class NoticeRepositoryImpl implements NoticeRepository{
	
	private final NoticeMapper noticeMapper;
	// 생성자 주입
	public NoticeRepositoryImpl(NoticeMapper noticeMapper) {
		this.noticeMapper = noticeMapper;
	}

	@Override
	public List<ResponseListDto> selectReceivedList(String userId) {
		List<ResponseListDto> noticeList = noticeMapper.selectRecievedByUserId(userId);
		return noticeList;
	}
	
	@Override
	public ResponseDetailNoticeDto selectNoticeDetail(String uuid) {
		ResponseDetailNoticeDto noticeInfo = noticeMapper.selectNoticeDetailByUuid(uuid);
		return noticeInfo;
	}
	
	@Override
	public ResponseDetailUserDto selectUserDetail(String sender_id) {
		ResponseDetailUserDto userInfo = noticeMapper.selectUserDetailByUuid(sender_id);
		return userInfo;
	}	
	
	@Override
	public ResponseDetailMatchingDto selectMatchingDetail(int matching_id) {
		ResponseDetailMatchingDto matchingInfo = noticeMapper.selectMatchingDetailByUuid(matching_id);
		return matchingInfo;
	}

	
	@Override
	public void updateReadStatus(String uuid) {
		noticeMapper.updateReadStatusByUuid(uuid);
		
	}
	
	@Override
	public void updateProcessedStatus(String uuid) {
		noticeMapper.updateProcessedStatusByUuid(uuid);
		
	}
	
	@Override
	public void deleteNotice(String uuid) {
		noticeMapper.deleteNoticeByUuid(uuid);
		
	}

	@Override
	public void saveApplyNotice(int matchingId, String userId, String receiverId, RequestCreateApplyDto requestCreateApplyDto) {
		Map<String, Object> info = new HashMap<>();
		info.put("matchingId", matchingId);
		info.put("senderId", userId);
		info.put("receiverId", receiverId);
		info.put("dto", requestCreateApplyDto);
		
		noticeMapper.insertApplyNotice(info);
		
	}

	@Override
	public void saveReturnNotice(int matchingId, String userId, RequestCreateReturnDto requestCreateReturnDto) {
		Map<String, Object> info = new HashMap<>();
		info.put("matchingId", matchingId);
		info.put("senderId", userId);
		info.put("dto", requestCreateReturnDto);
		
		noticeMapper.insertReturnNotice(info);
	}

	@Override
	public void saveDeleteNotice(List<RequestCreateDeleteDto> notices) {
		noticeMapper.insertDeleteNotice(notices);
	}
}

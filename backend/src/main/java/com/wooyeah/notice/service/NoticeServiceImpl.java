package com.wooyeah.notice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wooyeah.matching.entity.MatchingEntity;
import com.wooyeah.matching.repository.MatchingRepository;
import com.wooyeah.notice.dto.*;
import com.wooyeah.notice.repository.NoticeRepository;

@Service
public class NoticeServiceImpl implements NoticeService{

	private NoticeRepository noticeRepository;
	private MatchingRepository matchingRepository;
	
	// 생성자 주입
	public NoticeServiceImpl(NoticeRepository noticeRepository, MatchingRepository matchingRepository) {
		this.noticeRepository = noticeRepository;
		this.matchingRepository = matchingRepository;
	}

	@Override
	public List<ResponseListDto> getTotalList(String userId) {
		List<ResponseListDto> noticeList = noticeRepository.selectReceivedList(userId);
		return noticeList;
	}
	
	@Override
	public ResponseDetailDto getDetail(String uuid) {
	
		ResponseDetailNoticeDto noticeDetail = noticeRepository.selectNoticeDetail(uuid);
		ResponseDetailUserDto userDetail = noticeRepository.selectUserDetail(noticeDetail.getSender_id());
		ResponseDetailMatchingDto matchingDetail = noticeRepository.selectMatchingDetail(noticeDetail.getMatching_id());

		ResponseDetailDto detailInfo = new ResponseDetailDto(noticeDetail, userDetail, matchingDetail);
		return detailInfo;
	}
	
	@Override
	public void readNotice(String uuid) {
		noticeRepository.updateReadStatus(uuid);
		
	}
	
	@Override
	public void processNotice(String uuid) {
		noticeRepository.updateProcessedStatus(uuid);
		
	}

	@Override
	public void removeNotice(String uuid) {
		noticeRepository.deleteNotice(uuid);
		
	}

	@Override
	public void createApply(String userId, String matchingUuid, RequestCreateApplyDto requestCreateApplyDto) {
		// matchingUuid를 통해 해당 matching 작성자를 구한다.
		MatchingEntity matching = matchingRepository.getMatchingInfo(matchingUuid);
//		System.out.println(matching.toString());
		
		int matchingId = matching.getId();
		String receiverId = matching.getWriter_id();
		
		noticeRepository.saveApplyNotice(matchingId, userId, receiverId, requestCreateApplyDto);
	}

	@Override
	public void createReturn(String userId, String matchingUuid, RequestCreateReturnDto requestCreateReturnDto) {
		MatchingEntity matching = matchingRepository.getMatchingInfo(matchingUuid);
		int matchingId = matching.getId();
		
		noticeRepository.saveReturnNotice(matchingId, userId, requestCreateReturnDto);
	}




}

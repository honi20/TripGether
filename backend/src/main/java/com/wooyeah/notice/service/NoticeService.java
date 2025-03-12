package com.wooyeah.notice.service;

import java.util.List;

import com.wooyeah.notice.dto.*;

public interface NoticeService {

	List<ResponseListDto> getTotalList(String userId);

	ResponseDetailDto getDetail(String uuid);
	
	void readNotice(String uuid);

	void processNotice(String uuid);
	
	void removeNotice(String uuid);

	// 신청 알림 저장
	void createApply(String userId, String matchingUuid, RequestCreateApplyDto requestCreateApplyDto);

	// 응답 알림 저장
	void createReturn(String userId, String matchingUuid, RequestCreateReturnDto requestCreateReturnDto);




}

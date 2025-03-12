package com.wooyeah.plan.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wooyeah.auth.util.JwtUtil;
import com.wooyeah.matching.entity.MatchingEntity;
import com.wooyeah.matching.repository.MatchingRepository;
import com.wooyeah.notice.dto.RequestCreateDeleteDto;
import com.wooyeah.notice.repository.NoticeRepository;
import com.wooyeah.plan.dto.*;
import com.wooyeah.plan.entity.PlanEntity;
import com.wooyeah.plan.entity.PlanImageEntity;
import com.wooyeah.plan.repository.PlanRepository;
import com.wooyeah.user.entity.ProfileEntity;

@Service
public class PlanServiceImpl implements PlanService {
	
	private final PlanRepository planRepository;
	private final JwtUtil jwtUtil;
	private final MatchingRepository matchingRepository;
	private final NoticeRepository noticeRepository;
	
	public PlanServiceImpl(PlanRepository planRepository, JwtUtil jwtUtil, MatchingRepository matchingRepository, NoticeRepository noticeRepository) {
		this.planRepository = planRepository;
		this.jwtUtil = jwtUtil;
		this.matchingRepository = matchingRepository;
		this.noticeRepository = noticeRepository;
	}
	
	@Value("${file.path.upload-planThumbnail}")
	private String uploadPlanImagePath;

	@Override
	public List<PlanListItemDto> list(String userId) {
		List<PlanEntity> plans = planRepository.getPlanList(userId);
		List<PlanListItemDto> list = new ArrayList<>();
		
		for (PlanEntity plan : plans) {
			PlanListItemDto dto = new PlanListItemDto();
			dto.setUuid(plan.getUuid());
			dto.setTitle(plan.getTitle());
			dto.setStartDate(plan.getStart_date());
			dto.setEndDate(plan.getEnd_date());
			int planId = plan.getId();
			PlanImageEntity planImage = planRepository.getPlanImage(planId);
			if (planImage != null && planImage.getSave_folder() != null) {
				String imagePath = uploadPlanImagePath + "/" + planImage.getSave_folder() + "/" + planImage.getSave_file();
				dto.setPlanImage(imagePath);
			}
			
			// 현재 날짜가 여행 시작 날짜보다 이전인 경우
			if (LocalDate.now().compareTo(plan.getStart_date()) < 0)
				dto.setOutOfDate(false);
			else
				dto.setOutOfDate(true);
			
			list.add(dto);
		}
		
		return list;
	}

	@Override
	@Transactional
	public void removePlan(String userId, String planUuid) {
		
		// planUuid로부터 plan Entity를 가져온다.
		ResponseDetailPlanDto planDetail = planRepository.getPlanDetail(planUuid);
		int planId = planDetail.getId();

		// planId를 갖는 matching_uuid 정보를 가지고온다.
		List<String> matchingUuidList = matchingRepository.getMatchingUuid(planId);
		
		// 해당 matcing_uuid를 갖는 matching 데이터를 삭제한다. (그룹원에게 알림 감)
		for (String matchingUuid : matchingUuidList) {
			MatchingEntity matching = matchingRepository.getMatchingInfo(matchingUuid);
			
			// 해당 매칭 그룹에 속한 그룹원 아이디 정보 받기
			List<String> groupInfo = matchingRepository.getGroupInfo(userId, matching.getId());
//			System.out.println(groupInfo.toString());
			
			// 해당 그룹원들에게 삭제 알림 보내기
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
			
			// 매칭 게시글 삭제
			matchingRepository.deleteMatchingPost(matchingUuid);
		}
		
		
		// planUuid를 갖는 데이터를 삭제한다.
		planRepository.deletePlan(planUuid);		
	}

	@Override
	public void createPlan(RequestPlanRegistDto requestPlanRegistDto) {
		// plan 테이블에 저장
		planRepository.savePlanInfo(requestPlanRegistDto);
		int planId = planRepository.getLastId(); // 가장 마지막에 삽입된 id를 가져온다.
		
		// schedules 테이블에 저장
		List<Schedule> schedules = requestPlanRegistDto.getSchedules();
		planRepository.saveScheduleList(planId, schedules);
		
		// plan_img 테이블에 저장
		FileInfoDto planImage = requestPlanRegistDto.getPlanProfile();
		planRepository.savePlanImage(planId, requestPlanRegistDto);
		
	}

	@Override
	public ResponseDetailDto getDetail(String uuid) {
		// plan 테이블에서 가져오기
		ResponseDetailPlanDto planDetail = planRepository.getPlanDetail(uuid);
		int planId = planDetail.getId();
		int totalBudget = 0;
		
		// schedules 가져오기
		List<ResponseDetailScheduleDto> schedules = planRepository.getSchedules(planId);
		for (ResponseDetailScheduleDto schedule: schedules) { // 각 날의 일정에 대해 반복
			int scheduleId = schedule.getId();
			int scheduleBudget = 0;
			List<Place> places = planRepository.getPlaces(scheduleId);
			for (Place p: places) {
				scheduleBudget += p.getBudget();
			}
			schedule.setPlaces(places);
			totalBudget += scheduleBudget;
		}
		
		// planImage 가져오기
		PlanImageEntity planImage = planRepository.getPlanImage(planId);
		String imagePath = "";
		if (planImage != null && planImage.getSave_folder() != null) {
			imagePath = uploadPlanImagePath + "/" + planImage.getSave_folder() + "/" + planImage.getSave_file();
		}
		
		ResponseDetailDto responseDetailDto = new ResponseDetailDto(planDetail, schedules, totalBudget, imagePath);
		
		return responseDetailDto;
	}


}

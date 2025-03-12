package com.wooyeah.plan.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wooyeah.plan.dto.*;
import com.wooyeah.plan.entity.PlanEntity;
import com.wooyeah.plan.entity.PlanImageEntity;
import com.wooyeah.plan.mapper.PlanMapper;

@Repository
public class PlanRepositoryImpl implements PlanRepository {

	private PlanMapper planMapper;
	
	public PlanRepositoryImpl(PlanMapper planMapper) {
		this.planMapper = planMapper;
	}
	
	@Override
	public List<PlanEntity> getPlanList(String userId) {
		return planMapper.selectAllByUserId(userId);
	}

	@Override
	public void deletePlan(String uuid) {
		planMapper.deletePlanByUuid(uuid);
		
	}

	@Override
	public void savePlanInfo(RequestPlanRegistDto requestPlanRegistDto) {
		planMapper.insertPlan(requestPlanRegistDto);
		
	}

	@Override
	public void saveScheduleList(int planId, List<Schedule> schedules) {
		for (Schedule s: schedules) {
			planMapper.insertSchedule(planId, s);
			List<Place> places = s.getPlaces();
			int scheduleId = planMapper.selectLastInsertId();
			for (Place p: places) {
				planMapper.insertPlace(scheduleId, p);
			}
		}
	}

	@Override
	public void savePlanImage(int planId, RequestPlanRegistDto requestPlanRegistDto) {
		planMapper.insertPlanImageFileByPlanId(planId, requestPlanRegistDto);
	}

	@Override
	public int getLastId() {
		return planMapper.selectLastInsertId();
	}

	@Override
	public ResponseDetailPlanDto getPlanDetail(String uuid) {
		return planMapper.selectPlanByUuid(uuid);
	}

	@Override
	public PlanImageEntity getPlanImage(int planId) {
		return planMapper.selectPlanImageByPlanId(planId);
	}

	@Override
	public List<ResponseDetailScheduleDto> getSchedules(int planId) {
		return planMapper.selectSchedulesByPlanId(planId);
	}

	@Override
	public List<Place> getPlaces(int scheduleId) {
		return planMapper.selectPlacesByScheduleId(scheduleId);
	}

	@Override
	public PlanEntity getPlanInfo(int planId) {
		return planMapper.selectById(planId);
	}

	@Override
	public int getTotalBudget(int planId) {
		return planMapper.selectBudgetSumById(planId);
	}



}

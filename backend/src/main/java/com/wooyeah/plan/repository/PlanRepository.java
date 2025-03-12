package com.wooyeah.plan.repository;

import java.util.List;

import com.wooyeah.plan.dto.FileInfoDto;
import com.wooyeah.plan.dto.Place;
import com.wooyeah.plan.dto.*;
import com.wooyeah.plan.dto.ResponseDetailPlanDto;
import com.wooyeah.plan.dto.ResponseDetailScheduleDto;
import com.wooyeah.plan.dto.Schedule;
import com.wooyeah.plan.entity.PlanEntity;
import com.wooyeah.plan.entity.PlanImageEntity;

public interface PlanRepository {

	List<PlanEntity> getPlanList(String userId);

	void deletePlan(String uuid);

	void savePlanInfo(RequestPlanRegistDto requestPlanRegistDto);

	void saveScheduleList(int planId, List<Schedule> schedules);

	void savePlanImage(int planId, RequestPlanRegistDto requestPlanRegistDto);

	int getLastId();

	ResponseDetailPlanDto getPlanDetail(String uuid);

	PlanImageEntity getPlanImage(int planId);

	List<ResponseDetailScheduleDto> getSchedules(int planId);

	List<Place> getPlaces(int scheduleId);

	PlanEntity getPlanInfo(int planId);

	int getTotalBudget(int planId);

}

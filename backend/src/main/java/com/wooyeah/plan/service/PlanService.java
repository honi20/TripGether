package com.wooyeah.plan.service;

import java.util.List;

import com.wooyeah.plan.dto.PlanListItemDto;
import com.wooyeah.plan.dto.RequestPlanRegistDto;
import com.wooyeah.plan.dto.ResponseDetailDto;

public interface PlanService {

	List<PlanListItemDto> list(String userId);

	void removePlan(String userId, String planUuid);

	void createPlan(RequestPlanRegistDto requestPlanRegistDto);

	ResponseDetailDto getDetail(String uuid);
}

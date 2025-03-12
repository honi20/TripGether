package com.wooyeah.plan.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.wooyeah.plan.dto.*;
import com.wooyeah.plan.entity.PlanEntity;
import com.wooyeah.plan.entity.PlanImageEntity;

@Mapper
public interface PlanMapper {
	
	@Select("select * from plan where writer = #{userId} ORDER BY CASE WHEN plan.start_date >= CURRENT_DATE THEN 0 ELSE 1 END, plan.start_date")
	List<PlanEntity> selectAllByUserId(String userId);
	
	@Delete("DELETE FROM plan WHERE uuid=#{uuid}")
	void deletePlanByUuid(String uuid);
	
	@Insert("INSERT INTO `plan` (title, writer, start_date, end_date) "
			+ "VALUES (#{title}, #{writer}, #{startDate}, #{endDate})")
	void insertPlan(RequestPlanRegistDto requestPlanRegistDto);
	
	@Insert("INSERT INTO `schedule` (plan_id, day, idx) " + 
			"VALUES (#{planId}, #{s.day}, #{s.idx})")
	void insertSchedule(@Param("planId") int planId, @Param("s") Schedule s);
	
	@Insert("INSERT INTO `place` (schedule_id, place_order, name, latitude, longitude, description, budget, time) " + 
			"VALUES (#{scheduleId}, #{p.place_order}, #{p.name}, #{p.latitude}, #{p.longitude}, #{p.description}, #{p.budget}, #{p.time})")
	void insertPlace(@Param("scheduleId") int scheduleId, @Param("p") Place p);
	
	@Insert("INSERT INTO `plan_img` (plan_id, save_folder, original_file, save_file) " +
            "VALUES (#{planId}, #{requestPlanRegistDto.planProfile.saveFolder}, " +
            "#{requestPlanRegistDto.planProfile.originalFile}, #{requestPlanRegistDto.planProfile.saveFile})")
    void insertPlanImageFileByPlanId(@Param("planId") int planId, 
                                     @Param("requestPlanRegistDto") RequestPlanRegistDto requestPlanRegistDto);
	
	@Select("SELECT last_insert_id()")
	int selectLastInsertId();
	
	@Select("SELECT uuid, id, title, writer, start_date, end_date "
			+ "FROM plan WHERE uuid=#{uuid}")
	ResponseDetailPlanDto selectPlanByUuid(String uuid);
	
	@Select("SELECT * FROM plan_img WHERE plan_id=#{planId}")
	PlanImageEntity selectPlanImageByPlanId(int planId);
	
	@Select("SELECT id, day, idx FROM schedule WHERE plan_id=#{planId}")
	List<ResponseDetailScheduleDto> selectSchedulesByPlanId(int planId);

	@Select("SELECT place_order, DATE_FORMAT(time, '%H:%i') AS time, name, description, budget, latitude, longitude "
			+"FROM place WHERE schedule_id=#{scheduleId}")
	List<Place> selectPlacesByScheduleId(int scheduleId);

	@Select("SELECT * FROM plan WHERE id = #{planId}")
	PlanEntity selectById(int planId);

	@Select("SELECT SUM(budget) FROM schedule LEFT JOIN place ON schedule.id = place.schedule_id WHERE schedule.plan_id = #{planId};")
	int selectBudgetSumById(int planId);
}

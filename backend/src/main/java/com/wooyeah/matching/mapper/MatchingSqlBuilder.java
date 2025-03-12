package com.wooyeah.matching.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.jdbc.SQL;

import com.wooyeah.matching.dto.SearchDto;
import com.wooyeah.matching.dto.request.RequestSearchDto;

public class MatchingSqlBuilder {

	// 인원 임박 리스트 정보 (남은 인원이 적은 순 -> 출발일 빠른 순)
	public String buildSelectOrderByPersonCnt(int maxCnt) {
		return new SQL() {
			{
				SELECT("matching.uuid AS matchingUuid, " + 
						"matching.title AS matchingTitle, " + 
						"matching.region AS matchingRegion, "  + 
						"matching.recruit_person_cnt AS recruitPersonCnt, " + 
						"matching.current_person_cnt AS currentPersonCnt, "  + 
						"plan.start_date AS planStartDate, " + 
						"plan.end_date AS planEndDate");
				FROM("matching");
				LEFT_OUTER_JOIN("plan ON matching.plan_id = plan.id");
				WHERE("ABS(current_person_cnt - recruit_person_cnt) > 0");
				WHERE("plan.start_date > NOW()");
				ORDER_BY("ABS(current_person_cnt - recruit_person_cnt), plan.start_date");
				LIMIT("#{maxCnt}");
			}
		}.toString();
	}

	// 내가 모집한 게시글 리스트 정보 (출발일이 현재 이후인 순 -> 출발일 빠른 순 정렬)
	public String buildSelectRecruitMatchingByUserId(String userId) {
        return new SQL() {{
            SELECT("matching.uuid AS matchingUuid, " +
                    "matching.title AS matchingTitle, " +
                    "matching.region AS matchingRegion, " +
                    "matching.recruit_person_cnt AS recruitPersonCnt, " +
                    "matching.current_person_cnt AS currentPersonCnt, " +
                    "plan.start_date AS planStartDate, " +
                    "plan.end_date AS planEndDate");
            FROM("matching");
            LEFT_OUTER_JOIN("plan ON matching.plan_id = plan.id");
            WHERE("matching.writer_id = #{userId}");
            GROUP_BY("matching.id");
            ORDER_BY("CASE WHEN plan.start_date >= CURRENT_DATE THEN 0 ELSE 1 END, plan.start_date");
        }}.toString();
    }
	
	// 내가 가입한 게시글 리스트 정보 (출발일이 현재 이후인 순 -> 출발일 빠른 순 정렬)
	public String buildSelectJoinMatchingByUserId(String userId) {
        return new SQL() {{
            SELECT("matching.uuid AS matchingUuid, " +
                    "matching.title AS matchingTitle, " +
                    "matching.region AS matchingRegion, " +
                    "matching.recruit_person_cnt AS recruitPersonCnt, " +
                    "matching.current_person_cnt AS currentPersonCnt, " +
                    "plan.start_date AS planStartDate, " +
                    "plan.end_date AS planEndDate");
            FROM("trip_group");
            LEFT_OUTER_JOIN("matching ON trip_group.matching_id = matching.id");
            LEFT_OUTER_JOIN("plan ON matching.plan_id = plan.id");
            WHERE("trip_group.user_id = #{userId}");
            WHERE("#{userId} != matching.writer_id");
            ORDER_BY("CASE WHEN plan.start_date >= CURRENT_DATE THEN 0 ELSE 1 END, plan.start_date");
        }}.toString();
    }
	
	public String buildSelectThemesBySearchFilter(List<String> filters) {
        return new SQL() {{
            SELECT("*");
            FROM("theme");

            // 리스트에 포함된 각 필터에 대해 WHERE 절을 추가
            if (filters != null) {
                filters.forEach(filter -> {
                    WHERE(filter + " = 1");
                });
            }
        }}.toString();
    }
	
	public static String buildSelectMatchingBySearchFilter(RequestSearchDto requestSearchDto) {
		return new SQL() {
			{
				SELECT("matching.uuid, matching.title, region, recruit_person_cnt, current_person_cnt, matching.description, view_cnt, matching.created_at, writer_id, matching.plan_id, start_date, end_date, sum(budget) as budget");
				FROM("matching");
				LEFT_OUTER_JOIN("plan ON matching.plan_id = plan.id");
				LEFT_OUTER_JOIN("schedule on plan.id = schedule.plan_id");
				LEFT_OUTER_JOIN("place on schedule.id = place.schedule_id");
				
				if (requestSearchDto.getKeyword() != null) {
					WHERE("matching.title LIKE CONCAT('%', #{keyword}, '%') OR region LIKE CONCAT('%', #{keyword}, '%')");
				}
				if (requestSearchDto.getThemeId() != null && !requestSearchDto.getThemeId().isEmpty()) {
					WHERE("theme_id IN ("
							+ requestSearchDto.getThemeId().stream().map(String::valueOf).collect(Collectors.joining(","))
							+ ")");
				}
				if (requestSearchDto.getMaxPersonCnt() != 0) {
					WHERE("recruit_person_cnt <= #{maxPersonCnt}");
				}
				if (requestSearchDto.getMaxPeriod() != 0) {
					WHERE("DATEDIFF(plan.end_date, plan.start_date)+1 <= #{maxPeriod}");
				}
				GROUP_BY("matching.uuid, matching.title, region, recruit_person_cnt, current_person_cnt, matching.description, view_cnt, matching.created_at, writer_id, matching.plan_id, start_date, end_date");
				if (requestSearchDto.getMaxBudget() != 0) {
					HAVING("sum(budget) <= (#{maxBudget} * 10000)");
				}
				if (requestSearchDto.getSortCriteria() != null && requestSearchDto.getSortOrder() != null) {
					ORDER_BY(requestSearchDto.getSortCriteria() + " " + requestSearchDto.getSortOrder());
				}
			}
		}.toString();
	}
}

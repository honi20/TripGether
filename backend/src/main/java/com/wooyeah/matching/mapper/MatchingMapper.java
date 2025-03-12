package com.wooyeah.matching.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.wooyeah.matching.dto.*;
import com.wooyeah.matching.dto.request.RequestSearchDto;
import com.wooyeah.matching.dto.response.ResponseListItemDto;
import com.wooyeah.matching.dto.response.ResponseSearchDto;
import com.wooyeah.matching.entity.MatchingEntity;
import com.wooyeah.matching.entity.ThemeEntity;

@Mapper
public interface MatchingMapper {
	@Insert("INSERT INTO theme(for_energetic,for_leisurely,for_natural,for_urban,for_healing,for_shopping,for_morning,for_night_owl) VALUES (#{forEnergetic}, #{forLeisurely}, #{forNatural}, #{forUrban}, #{forHealing}, #{forShopping}, #{forMorning}, #{forNightOwl})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insertTheme(ThemeInfoDto themes);

	@Insert("INSERT INTO matching(title, region, recruit_person_cnt, description, writer_id, plan_id, theme_id) VALUES (#{dto.title}, #{dto.region}, #{dto.recruitPersonCnt}, #{dto.description}, #{writerId}, #{planId}, #{themeId})")
	@Options(useGeneratedKeys = true, keyProperty = "dto.id")
	void insertMatching(Map<String, Object> info);

	@Insert("INSERT INTO trip_group VALUES(#{userId}, #{matchingId})")
	void insertTripGroup(Map<String, Object> info);
	
	@Select("SELECT * FROM matching WHERE uuid = #{matchingUuid}")
	MatchingEntity selectByUuid(String matchingUuid);

	@SelectProvider(type = MatchingSqlBuilder.class, method = "buildSelectOrderByPersonCnt")
	List<ResponseListItemDto> selectOrderByPersonCnt(int maxCnt);

	@SelectProvider(type = MatchingSqlBuilder.class, method = "buildSelectRecruitMatchingByUserId")
	List<ResponseListItemDto> selectRecruitMatchingByUserId(String userId);

	
	@SelectProvider(type = MatchingSqlBuilder.class, method = "buildSelectJoinMatchingByUserId")
	List<ResponseListItemDto> selectJoinMatchingByUserId(String userId);

	@Select("SELECT * FROM theme WHERE id = #{themeId}")
	ThemeEntity selectThemeById(int themeId);

	@Select("SELECT user_id FROM trip_group WHERE matching_id = #{matchingId} AND user_id != #{userId};")
	List<String> getTripGroupByUserIdAndMatchingId(Map<String, Object> info);

	@Delete("DELETE FROM matching WHERE uuid = #{matchingUuid}")
	void deleteMatchingByUuid(String matchingUuid);

	@SelectProvider(type = MatchingSqlBuilder.class, method = "buildSelectThemesBySearchFilter")
	List<Integer> selectThemeIdBySearchFilter(List<String> filters);

	@SelectProvider(type = MatchingSqlBuilder.class, method = "buildSelectMatchingBySearchFilter")
	List<ResponseSearchDto> selectMatchingBySearchFilter(RequestSearchDto requestSearchDto);

	@Update("UPDATE matching SET view_cnt = view_cnt+1 WHERE uuid = #{matchingUuid}")
	void updateViewCntByUuid(String matchingUuid);

	@Update("UPDATE theme SET for_energetic = #{themes.forEnergetic}, for_leisurely = #{themes.forLeisurely}, for_natural = #{themes.forNatural}, for_urban = #{themes.forUrban}, for_healing = #{themes.forHealing}, for_shopping = #{themes.forShopping}, for_morning = #{themes.forMorning}, for_night_owl = #{themes.forNightOwl} WHERE id = #{themeId}")
	void updateThemesById(Map<String, Object> info);

	@Update("UPDATE matching SET description = #{description} WHERE id = #{matchingId}")
	void updateMatchingInfoById(Map<String, Object> info);

	@Insert("INSERT INTO trip_group(matching_id, user_id) VALUES (#{matchingId}, #{userId})")
	void insertUserIdToGroup(Map<String, Object> info);

	@Delete("DELETE FROM trip_group WHERE matching_id = #{matchingId} AND user_id = #{userId}")
	void deleteUserIdFromGroup(Map<String, Object> info);

	@Update("UPDATE matching SET current_person_cnt = current_person_cnt+1 WHERE id = #{matchingId}")
	void increaseMatchingCurrentCnt(int matchingId);

	@Update("UPDATE matching SET current_person_cnt = current_person_cnt-1 WHERE id = #{matchingId}")
	void decreaseMatchingCurrentCnt(int matchingId);

	@Select("SELECT T.user_id userId, U.name userName "
			+ "FROM trip_group T JOIN user U ON T.user_id = U.id "
			+ "WHERE T.matching_id = #{matchingId}")
	List<ResponseGroupMemberListDto> selectUserByMatchingid(int matchingId);

	@Select("SELECT uuid FROM matching WHERE plan_id = #{planId}")
	List<String> selectMatchingUuidByPlanId(int planId);

}

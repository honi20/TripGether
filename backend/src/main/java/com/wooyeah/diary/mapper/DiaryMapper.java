package com.wooyeah.diary.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wooyeah.diary.dto.FileInfoDto;
import com.wooyeah.diary.dto.ImgGroupDto;
import com.wooyeah.diary.entity.DiaryEntity;
import com.wooyeah.diary.entity.DiaryImgEntity;

@Mapper
public interface DiaryMapper {

	@Insert("INSERT INTO diary(writer_id, matching_id, content, location, public_status, travel_date) VALUE (#{userId}, #{matchingId}, #{dto.content}, #{dto.location}, #{dto.publicStatus}, #{dto.travelDate})")
	@Options(useGeneratedKeys = true, keyProperty = "dto.id")
	int insertDiaryAndMatchingId(Map<String, Object> info);
	
	@Insert("INSERT INTO diary(writer_id, content, location, public_status, travel_date) VALUE (#{userId}, #{dto.content}, #{dto.location}, #{dto.publicStatus}, #{dto.travelDate})")
	@Options(useGeneratedKeys = true, keyProperty = "dto.id")
	int insertDiary(Map<String, Object> info);

	@Insert({
        "<script>",
        "INSERT INTO diary_img (save_folder, original_file, save_file) VALUES ",
        "<foreach collection='list' item='item' index='index' separator=','>",
        "(#{item.saveFolder}, #{item.originalFile}, #{item.saveFile})",
        "</foreach>",
        "</script>"
    })
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insertDiaryImg(List<FileInfoDto> images);

	@Insert({
        "<script>",
        "INSERT INTO img_group (diary_id, diary_img_id) VALUES ",
        "<foreach collection='list' item='item' index='index' separator=','>",
        "(#{item.diaryId}, #{item.diaryImgId})",
        "</foreach>",
        "</script>"
    })
	void insertImgGroup(List<ImgGroupDto> imgGroupDtoList);

	@Select("SELECT * FROM diary WHERE writer_id = #{userId} ORDER BY created_at DESC")
	List<DiaryEntity> selectDiaryByWriterId(String userId);

	// 기록 작성자가 로그인 유저라면, 공개 여부와 상관없이 보여줌
	// 기록 작성자가 그룹의 다른 유저라면, 공개 여부에 따라 보여줌 (전체공개/그룹공개 일 때만)
	@Select("SELECT * FROM diary WHERE (matching_id IS NOT NULL AND matching_id = #{matchingId}) AND (public_status IN ('openAll', 'openGroup') OR (public_status = 'private' AND writer_id = #{userId})) ORDER BY created_at DESC;")
	List<DiaryEntity> selectDiaryByMatchingId(String userId, int matchingId);

	@Select("SELECT id, save_folder, original_file, save_file FROM diary_img RIGHT JOIN img_group ON diary_img.id = img_group.diary_img_id WHERE img_group.diary_id = #{diaryId}")
	List<DiaryImgEntity> selectDiaryImgById(int diaryId);

	@Select("SELECT * FROM diary WHERE uuid = #{diaryUuid}")
	DiaryEntity selectDiaryByUuid(String diaryUuid);

	@Delete("DELETE FROM diary_img WHERE id IN (SELECT diary_img_id FROM img_group WHERE diary_id = #{diaryId})")
	void deleteDiaryImgByDiaryId(int diaryId);

	@Delete("DELETE FROM diary WHERE id = #{diaryId}")
	int deleteDiaryById(int diaryId);

	@Select("SELECT * FROM diary WHERE location = #{location} AND public_status = 'openAll' ORDER BY created_at DESC")
	List<DiaryEntity> selectDiaryByLocation(String location);

}

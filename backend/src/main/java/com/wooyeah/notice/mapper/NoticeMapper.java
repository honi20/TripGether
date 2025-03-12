package com.wooyeah.notice.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;

import com.wooyeah.notice.dto.*;

@Mapper
public interface NoticeMapper {
	
	@Select("SELECT uuid, type, title, created_at, read_status, processed_status FROM notice WHERE receiver_id=#{userId} ORDER BY created_at DESC")
	List<ResponseListDto> selectRecievedByUserId(String userId);

	@Select("SELECT uuid, type, sender_id, msg, matching_id, processed_status FROM notice WHERE uuid=#{uuid}")
	ResponseDetailNoticeDto selectNoticeDetailByUuid(String uuid);
	@Select("SELECT name, gender, date_format(birthday, \"%Y\") AS birth_year FROM user WHERE id=#{sender_id}")
	ResponseDetailUserDto selectUserDetailByUuid(String sender_id);
	@Select("SELECT uuid, title FROM matching WHERE id=#{matching_id}")
	ResponseDetailMatchingDto selectMatchingDetailByUuid(int matching_id);

	@Update("UPDATE notice SET read_status=1 WHERE uuid=#{uuid}")
	void updateReadStatusByUuid(String uuid);
	
	@Update("UPDATE notice SET processed_status=1 WHERE uuid=#{uuid}")
	void updateProcessedStatusByUuid(String uuid);

	@Delete("DELETE FROM notice WHERE uuid=#{uuid}")
	void deleteNoticeByUuid(String uuid);

	@Insert("INSERT INTO notice(type, title, msg, sender_id, receiver_id, matching_id) VALUES(#{dto.type}, #{dto.title}, #{dto.msg}, #{senderId}, #{receiverId}, #{matchingId})")
	void insertApplyNotice(Map<String, Object> info);
	
	@Insert("INSERT INTO notice(type, title, sender_id, receiver_id, matching_id) VALUES(#{dto.type}, #{dto.title}, #{senderId}, #{dto.receiverId}, #{matchingId})")
	void insertReturnNotice(Map<String, Object> info);

	@Insert({
        "<script>",
        "INSERT INTO notice (type, title, sender_id, receiver_id, matching_id) VALUES ",
        "<foreach collection='list' item='item' index='index' separator=','>",
        "(#{item.type}, #{item.title}, #{item.senderId}, #{item.receiverId}, #{item.matchingId})",
        "</foreach>",
        "</script>"
    })
	void insertDeleteNotice(List<RequestCreateDeleteDto> notices);
}

package com.wooyeah.user.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wooyeah.user.dto.RequestSignUpDto;
import com.wooyeah.user.dto.RequestUpdateDto;
import com.wooyeah.user.dto.UserDto;
import com.wooyeah.user.entity.ProfileEntity;
import com.wooyeah.user.entity.UserEntity;

@Mapper
public interface UserMapper {

	@Select("select COUNT(*) from user where id = #{inputId}")
	int selectCountById(String inputId);
	
	@Insert("insert into user(id, password, name, gender, birthday) values(#{userId}, #{userPassword}, #{userName}, #{userGender}, #{userBirthday})")
	void insertUser(RequestSignUpDto requestSignUpDto);
	
	@Insert("insert into profile(user_id, save_folder, original_file, save_file) values(#{userId}, #{userProfile.saveFolder}, #{userProfile.originalFile}, #{userProfile.saveFile})")
	void insertProfileFileByUserId(RequestSignUpDto requestSignUpDto);

	@Select("select * from user where uuid = #{uuid}")
	UserEntity selectByUuid(String uuid);

	@Select("select * from profile where user_id = #{userId}")
	ProfileEntity selectProfileByUserId(String userId);

	@Update("update user set password = #{dto.userPassword} where id = #{userId}")
	void updateUserByUserId(Map<String, Object> info);

	@Delete("delete from profile where user_id = #{userId}")
	void deleteProfileFileByUserId(String userId);

	@Update("update profile set save_folder = #{dto.saveFolder}, original_file = #{dto.originalFile}, save_file = #{dto.saveFile} where user_id = #{userId}")
	void updateProfileFileByUserId(Map<String, Object> info);

	@Select("select * from user where id = #{userId}")
	UserEntity selectByUserId(String userId);
}

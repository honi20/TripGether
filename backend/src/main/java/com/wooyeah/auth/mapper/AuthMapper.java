package com.wooyeah.auth.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wooyeah.auth.dto.RequestLoginDto;
import com.wooyeah.auth.dto.TokenDto;
import com.wooyeah.user.entity.UserEntity;


@Mapper
public interface AuthMapper {

	@Select("select * from user where id = #{userId} and password = #{userPassword}")
	UserEntity selectByRequestLoginDto(RequestLoginDto requestLoginDto);
	
	@Insert("insert into token_status values(#{uuid}, #{hashedToken}, #{dateExpiration}, 1)")
	int insertTokenDto(TokenDto tokenDto);
	
	@Update("update token_status set valid=0 where uuid=#{userUuid} and valid=1")
	void updateValidTokenToInvalidByUserUuid(String userUuid);

	@Select("select * from user where uuid=#{userUuid}")
	UserEntity selectByUserUuid(String userUuid);

	@Update("update token_status set valid=0 where uuid=#{userUuid} and valid=1 and hashed_token!=#{hashedRefreshToken}")
	void updateValidTokenToInvalidByUserUuidAndRefreshToken(String userUuid, String hashedRefreshToken);

	@Select("select COUNT(*) from token_status where hashed_token=#{hashedToken} and valid=1")
	boolean selectValidStatusByToken(String hashedToken);
}

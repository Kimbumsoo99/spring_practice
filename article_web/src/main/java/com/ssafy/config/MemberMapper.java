package com.ssafy.config;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.ssafy.member.model.dto.MemberDto;

public interface MemberMapper {
	final String joinMember = "insert into members (user_name, user_id, user_password, email_id, email_domain, join_date) values (#{userName}, #{userId}, #{userPwd}, #{emailId}, #{emailDomain}, now())";
	final String listMember = "select * from members";
	final String loginMember = "select user_id, user_name from members where user_id = #{userid} and user_password = #{userpwd}";
	final String idCheck = "select count(user_id) from members where user_id = #{userid}";
	
	@Insert(joinMember)
	void join(MemberDto memberDto);
	
	@Select(loginMember)
	MemberDto loginMember(@Param("userId") String userId,@Param("userPwd") String userPwd);
	
	@Select(idCheck)
	int idCheck(String userId);
	
	
	@Select(listMember)
	   @Results(value = {
	      @Result(property = "userId", column = "user_id"),
	      @Result(property = "userName", column = "user_name"),
	      @Result(property = "userPwd", column = "user_password"),
	      @Result(property = "emailId", column = "email_id"),       
	      @Result(property = "emailDomain", column = "email_domain"),
	      @Result(property = "joinDate", column = "join_date")
	   })
	List<MemberDto> list();
}

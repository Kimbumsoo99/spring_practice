package com.ssafy.member.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.member.model.dto.MemberDto;

@Mapper
public interface MemberMapper {
	MemberDto login(Map<String, String> map);
}

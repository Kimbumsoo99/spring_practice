package com.ssafy.member.model.service;

import java.util.Map;

import com.ssafy.member.model.dto.MemberDto;

public interface MemberService {
	MemberDto login(Map<String, String> map) throws Exception;
}

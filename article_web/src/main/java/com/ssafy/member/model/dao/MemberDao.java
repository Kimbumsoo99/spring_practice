package com.ssafy.member.model.dao;

import java.sql.SQLException;

import com.ssafy.member.model.dto.MemberDto;

public interface MemberDao {
	int idCheck(String userId) throws SQLException;

	int joinMember(MemberDto memberDto) throws SQLException;

	MemberDto loginMember(String userId, String userPwd) throws SQLException;

}

package com.ssafy.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.config.SqlMapConfig;
import com.ssafy.member.model.dto.MemberDto;
import com.ssafy.util.DBUtil;

@Repository
public class MemberDaoImpl implements MemberDao {

	private final String NAMESAPCE = "com.ssafy.member.model.dao.MemberDao.";

	@Override
	public int idCheck(String userId) throws SQLException {
		try (SqlSession session = SqlMapConfig.getSqlSession()) {
			return session.selectOne(NAMESAPCE + "idCheck", userId);
		}
	}

	@Override
	public int joinMember(MemberDto memberDto) throws SQLException {
		try (SqlSession sqlSession = SqlMapConfig.getSqlSession()) {
			int cnt = sqlSession.insert(NAMESAPCE + "joinMember", memberDto);
			sqlSession.commit();
			return cnt;
		}
	}

	@Override
	public MemberDto loginMember(String userId, String userPwd) throws SQLException {
		try (SqlSession sqlSession = SqlMapConfig.getSqlSession()) {
			Map<String, String> map = new HashMap<>();
			map.put("userId", userId);
			map.put("userPwd", userPwd);
			return sqlSession.selectOne(NAMESAPCE + "loginMember", map);
		}
	}

}

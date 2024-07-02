package com.ssafy.member.model.mapper;

import com.ssafy.member.model.domain.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    List<Member> findAll();

    Member findByMemberId(String memberId);

    void save(Member member);
}

package com.ssafy.member.model.service;

import com.ssafy.member.model.domain.Member;
import com.ssafy.member.model.mapper.MemberMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService{

    private final MemberMapper memberMapper;

    public MemberServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public List<Member> getAllMember() {
        return memberMapper.findAll();
    }
}

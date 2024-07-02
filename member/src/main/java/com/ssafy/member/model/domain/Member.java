package com.ssafy.member.model.domain;

import lombok.Data;

@Data
public class Member {
    Long id;
    String memberName;
    String memberId;
    String memberPw;
    String email;
    String role;
}

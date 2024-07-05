package com.ssafy.memberPjt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtTokenDTO {
    private String access;
    private String refresh;
}

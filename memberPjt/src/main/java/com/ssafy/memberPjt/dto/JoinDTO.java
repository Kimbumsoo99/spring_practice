package com.ssafy.memberPjt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class JoinDTO {
    private String username;
    private String password;
    private String name;
    private String email;
}
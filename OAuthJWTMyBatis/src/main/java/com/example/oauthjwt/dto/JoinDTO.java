package com.example.oauthjwt.dto;

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
    private String email;
    private String name;
}

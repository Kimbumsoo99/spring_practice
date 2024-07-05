package com.ssafy.memberPjt.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ApiError {
    private String code;
    private String details;
}

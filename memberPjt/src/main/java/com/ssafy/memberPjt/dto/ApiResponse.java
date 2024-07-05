package com.ssafy.memberPjt.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ApiResponse<T> {
    private String status;
    private T data;
    private String message;
    private ApiError error;
}

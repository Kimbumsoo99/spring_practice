package com.project.reacttest.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private String token;
    private String username;
    private String password;
    private String id;
}

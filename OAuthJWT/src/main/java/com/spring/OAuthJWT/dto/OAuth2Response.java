package com.spring.OAuthJWT.dto;

public interface OAuth2Response {

    // 제공자 (naver, google)
    String getProvider();

    // 제공자 발급 아이디(번호)
    String getProviderId();

    // 이메일
    String getEmail();
    
    // 사용자 실명
    String getName();
}

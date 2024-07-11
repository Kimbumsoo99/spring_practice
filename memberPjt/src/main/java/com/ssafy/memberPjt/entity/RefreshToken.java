package com.ssafy.memberPjt.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@Builder
@RedisHash(value = "refresh_token")
public class RefreshToken {
    @Id
    private Long id;
    private String username;
    private String refresh;
    @TimeToLive
    private long expiration;
}

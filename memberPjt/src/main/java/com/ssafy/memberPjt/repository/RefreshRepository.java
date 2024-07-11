package com.ssafy.memberPjt.repository;

import com.ssafy.memberPjt.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshRepository extends CrudRepository<RefreshToken, Long> {
    Boolean existsByRefresh(String refresh);
    void deleteByRefresh(String refresh);
}

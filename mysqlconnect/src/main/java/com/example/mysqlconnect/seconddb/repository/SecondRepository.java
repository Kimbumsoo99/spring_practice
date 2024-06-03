package com.example.mysqlconnect.seconddb.repository;

import com.example.mysqlconnect.seconddb.entity.SecondEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecondRepository extends JpaRepository<SecondEntity, Integer> {
}

package com.example.mysqlmongo.mysqlrepo;

import com.example.mysqlmongo.entity.FirstdbEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FirstdbRepository extends JpaRepository<FirstdbEntity, Integer> {
}

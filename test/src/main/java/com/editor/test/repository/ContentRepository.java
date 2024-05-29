package com.editor.test.repository;

import com.editor.test.entity.ContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<ContentEntity, Integer> {
    // Entity와 Entity의 id에 해당하는 타입
    // interface로 선언하면, @Repository를 하지 않아도된다. -> 해도 상관 무

    ContentEntity findById(int id);

}

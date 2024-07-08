package com.project.reacttest.persistence;

import com.project.reacttest.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository<TodoEntity, String> {
    List<TodoEntity> findByUserId(String userId);
//    @Query("select * from TodoEntity t where t.useId = ?1")
//    List<TodoEntity> findByUserIdQuery(String userId);
}

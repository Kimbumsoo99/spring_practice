package com.project.reacttest.service;

import com.project.reacttest.model.TodoEntity;
import com.project.reacttest.persistence.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    public String testService() {
        TodoEntity entity = TodoEntity.builder()
                .title("My first todo item")
                .build();

        todoRepository.save(entity);

        TodoEntity savedEntity = todoRepository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }

    public List<TodoEntity> create(final TodoEntity todoEntity) {
        validate(todoEntity);

        todoRepository.save(todoEntity);

        return todoRepository.findByUserId(todoEntity.getUserId());
    }

    public List<TodoEntity> retrieve(final String userId) {
        return todoRepository.findByUserId(userId);
    }

    public List<TodoEntity> update(final TodoEntity entity) {
        validate(entity);

        final Optional<TodoEntity> original = todoRepository.findById(entity.getId());

        original.ifPresent(todo -> {
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());

            todoRepository.save(todo);
        });

        return retrieve(entity.getUserId());

    }

    public List<TodoEntity> delete(final TodoEntity entity) {
        validate(entity);

        try {
            todoRepository.delete(entity);
        } catch (Exception e) {
            throw new RuntimeException("error deleting entity " + entity.getId());
        }

        return retrieve(entity.getUserId());
    }

    private static void validate(TodoEntity todoEntity) {
        if (todoEntity == null) {
            throw new RuntimeException("Entity cannot be null");
        }

        if (todoEntity.getUserId() == null) {
            throw new RuntimeException("Unknown user");
        }
    }
}

package com.example.springbootmongodb.repository;

import com.example.springbootmongodb.model.TodoList;
import com.example.springbootmongodb.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> findAll();
    User findByEmail(String email);
    User deleteByEmail(String email);
    List<TodoList> findUserByEmail(String email);
}

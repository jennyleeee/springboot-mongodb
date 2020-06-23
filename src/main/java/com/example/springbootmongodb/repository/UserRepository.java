package com.example.springbootmongodb.repository;

import com.example.springbootmongodb.model.TodoList;
import com.example.springbootmongodb.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Now we can use MongoRepository’s methods:
 * save(), findOne(), findById(), findAll(),
 * count(), delete(), deleteById()…
 * without implementing these methods.
 */

public interface UserRepository extends MongoRepository<User, String> {
    List<User> findAll();

    User findByEmail(String email);

    User deleteByEmail(String email);

    List<TodoList> findUserByEmail(String email);
}
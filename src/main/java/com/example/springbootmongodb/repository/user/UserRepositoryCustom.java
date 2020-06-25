package com.example.springbootmongodb.repository.user;

import com.example.springbootmongodb.model.User;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> findAll();
//    User findByEmail(String email);
    DeleteResult deleteByEmail(String email);
    User findUserByEmail(String email);
    User save(User user);
    List<User> findAllUserTodoList();
    DeleteResult removeAll();
    UpdateResult updateUser(String email, User user);
}

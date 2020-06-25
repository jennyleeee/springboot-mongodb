package com.example.springbootmongodb.repository.todolist;

import com.example.springbootmongodb.model.TodoList;
import com.example.springbootmongodb.model.User;
import com.example.springbootmongodb.repository.user.UserRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoListRepository extends MongoRepository<TodoList, String>, TodoListRepositoryCustom {

//    TodoList findTodoListById
//    TodoList deleteTodoListById
//    TodoList removeTodoListById
}
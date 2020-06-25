package com.example.springbootmongodb.repository.todolist;

import com.example.springbootmongodb.model.TodoList;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import java.util.List;
import java.util.Optional;

public interface TodoListRepositoryCustom {

    // create
    TodoList save(TodoList todoList);

    // 1:N (user:todolist)
    List<TodoList> findTodoListByEmail(String email);

    // update => 사용자의 todolist 가져와서 업데이트, 삭제도 마찬가지
    TodoList findTodoListById(String id);

    //delete
    DeleteResult removeTodoListById(String id);

    //delete all
    DeleteResult removeAll();

    List<TodoList> findAll();

    UpdateResult updateTodoList(String id, TodoList todoList);


}


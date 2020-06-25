package com.example.springbootmongodb.controller;

import com.example.springbootmongodb.model.TodoList;
import com.example.springbootmongodb.model.User;
import com.example.springbootmongodb.repository.todolist.TodoListRepository;
import com.example.springbootmongodb.repository.todolist.TodoListRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping(value = "v1.0/test")
public class TodoListController {

    @Autowired
    TodoListRepositoryImpl todoListRepositoryImpl;

    @Autowired
    TodoListRepository todoListRepository;

    // get user Todolists
    @RequestMapping(value = "/user/{email}/todolists", method = {RequestMethod.GET})
    public ResponseEntity<List<TodoList>> getAllTodoList(@PathVariable("email") String email) {
        try {
            List<TodoList> todoLists = todoListRepositoryImpl.findTodoListByEmail(email);
            if (todoLists.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(todoLists, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // create to-do
    @RequestMapping(value = "/todo", method = {RequestMethod.POST})
    public ResponseEntity<TodoList> createTodo(@RequestBody TodoList todoList) {
        try {
            TodoList _todolist = todoListRepositoryImpl.save(new TodoList(todoList.getEmail(), todoList.getContents(), todoList.getCreated(), todoList.getIsdone(), todoList.getUpdated()));
            return new ResponseEntity<>(_todolist, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    //read all users todolist
    @RequestMapping(value = "/todolist", method = {RequestMethod.GET})
    public ResponseEntity<List<TodoList>> getAllTodoList() {
        try {
//            List<User> users = new ArrayList<>();
//            userRepository.findAll().forEach(users::add);
            List<TodoList> todoLists = todoListRepositoryImpl.findAll();
            if (todoLists.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(todoLists, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // read only one to-to Find by _id
    @RequestMapping(value = "/todo/{id}", method = RequestMethod.GET)
    public ResponseEntity<TodoList> getTargetTodo (@PathVariable("id") String id){
        Optional<TodoList> todoList = Optional.ofNullable(todoListRepositoryImpl.findTodoListById(id));
//        try {
//            TodoList todoList = todoListRepositoryImpl.findByObjectId(id);
//            if(todoList.getId()==null){
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            } return new ResponseEntity<>(todoList, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        if(todoList.isPresent()){
            return new ResponseEntity(todoList.get(), HttpStatus.OK);
        } else {
            System.out.println("DDDddddanskbnklb");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


//        // read a user todolist
//        @RequestMapping(value = "/todolist", method = {RequestMethod.GET})
//        public ResponseEntity<List<TodoList>> getUserTodoList(@PathVariable ("email") String email) {
//            try {
////            List<User> users = new ArrayList<>();
////            userRepository.findAll().forEach(users::add);
//                List<TodoList> todoLists = todoListRepositoryImpl.findAll();
//                if (todoLists.isEmpty()) {
//                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//                }
//                return new ResponseEntity<>(todoLists, HttpStatus.OK);
//            } catch (Exception e) {
//                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//            }

    // update a todolist
    @RequestMapping(value = "/todo/{id}", method = {RequestMethod.PUT}, produces = "application/json")
    public ResponseEntity<TodoList> updateUserTodoList(@PathVariable("id") String id, @RequestBody TodoList todoList) {
//        Optional<User> targetUser = Optional.ofNullable(userRepository.findByEmail(email));
        System.out.println("id :" + id);
        Optional<TodoList> targetTodolist = Optional.ofNullable(todoListRepositoryImpl.findTodoListById(id));
        if (targetTodolist.isPresent()) {
//            TodoList _todolist = targetTodolist.get();
//            _todolist.setId(todoList.getId());
//            _todolist.setEmail(todoList.getEmail());
//            _todolist.setContents(todoList.getContents());
//            _todolist.setCreated(todoList.getCreated());
//            _todolist.setIsdone(todoList.getIsdone());
//            _todolist.setUpdated(todoList.getUpdated());
            return new ResponseEntity(todoListRepositoryImpl.updateTodoList(id,todoList), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //delete a todolist
    @RequestMapping(value = "/todo/{id}", method = {RequestMethod.DELETE})
    public ResponseEntity<HttpStatus> deleteTodo(@PathVariable("id") String id) {
        Optional<TodoList> targetId = Optional.ofNullable(todoListRepositoryImpl.findTodoListById(id));
        if (targetId.isPresent()) {
            try {
                todoListRepositoryImpl.removeTodoListById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
    }

    //delete all todolist
    @RequestMapping(value = "/todolist", method = {RequestMethod.DELETE})
    public ResponseEntity<TodoList> deleteAllTodoList () {
        try {
            todoListRepositoryImpl.removeAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

}


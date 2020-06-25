package com.example.springbootmongodb.controller;

import com.example.springbootmongodb.model.TodoList;
import com.example.springbootmongodb.model.User;
import com.example.springbootmongodb.model.UserTodolist;
import com.example.springbootmongodb.repository.user.UserRepository;
import com.example.springbootmongodb.repository.user.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
// CORS(Cross-origin resource sharing)이란, 웹 페이지의 제한된 자원을 외부 도메인에서 접근을 허용해주는 메커니즘
@RestController
@RequestMapping(value = "v1.0/test") // 요청 url 을 어떤 메소드가 처리할 것인지 결정
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRepositoryImpl userRepositoryImpl;


    // GET users
    @RequestMapping(value = "/users", method = {RequestMethod.GET})
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userRepositoryImpl.findAll();
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // GET user
    @RequestMapping(value = "/user/{email}", method = {RequestMethod.GET})
    public ResponseEntity<User> getUser(@PathVariable("email") String email) {
        Optional<User> user = Optional.ofNullable(userRepositoryImpl.findUserByEmail(email));

        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST => register user
    @RequestMapping(value = "/user", method = {RequestMethod.POST})
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User _user = userRepositoryImpl.save(new User(user.getEmail(), user.getPassword(), user.getName(), user.getBirthday(), user.getGender()));
//            User _user = userRepository.save(new User(user.getEmail(),user.getPassword(),user.getName(),user.getBirthday(),user.getGender()));
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    // PUT => change user profile
    @RequestMapping(value = "/user/{email}", method = {RequestMethod.PUT}, produces = "application/json")
    public ResponseEntity<User> updateUserInfo(@PathVariable("email") String email, @RequestBody User user) {
//        Optional<User> targetUser = Optional.ofNullable(userRepository.findByEmail(email));
        Optional<User> targetUser = Optional.ofNullable(userRepositoryImpl.findUserByEmail(email));
        if (targetUser.isPresent()) {
//            User _user = targetUser.get();
//            _user.setEmail(user.getEmail());
//            _user.setName(user.getName());
//            _user.setGender(user.getGender());
//            _user.setPassword(user.getPassword());
//            _user.setBirthday(user.getBirthday());
//            _user.setTodoLists(user.getTodoLists());
            return new ResponseEntity(userRepositoryImpl.updateUser(email,user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // DELETE => delete user
    @RequestMapping(value = "/user/{email}", method = {RequestMethod.DELETE})
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("email") String email) {
        Optional<User> targetUser = Optional.ofNullable(userRepositoryImpl.findUserByEmail(email));
        if (targetUser.isPresent()) {
            try {
                userRepositoryImpl.deleteByEmail(email);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
    }

    @RequestMapping(value = "/users", method = {RequestMethod.DELETE})
    public ResponseEntity<HttpStatus> deleteAllUsers() {
        try {
            userRepositoryImpl.removeAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    // read user todolist
    @RequestMapping(value = "/user/{email}/todolist", method = {RequestMethod.GET})
    public ResponseEntity<User> getUserTodolist (@PathVariable("email") String email) {
        try {
            User user = userRepositoryImpl.findUserByEmail(email);
//            System.out.println(user.toString());
            if(user ==null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e);
        return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

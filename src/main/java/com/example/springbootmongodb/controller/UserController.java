package com.example.springbootmongodb.controller;

import com.example.springbootmongodb.repository.UserRepository;
import com.example.springbootmongodb.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080") // CORS(Cross-origin resource sharing)이란, 웹 페이지의 제한된 자원을 외부 도메인에서 접근을 허용해주는 메커니즘
@RestController
@RequestMapping(value = "v1.0/test") // 요청 url 을 어떤 메소드가 처리할 것인지 결정
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/users", method = {RequestMethod.GET})
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = new ArrayList<>();
            userRepository.findAll().forEach(users::add);
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @RequestMapping(value = "/user", method = {RequestMethod.POST})
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User _user = userRepository.save(new User(user.getEmail(),user.getPassword(),user.getName(),user.getBirthday(),user.getGender()));
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(value = "/user/{email}", method = {RequestMethod.PUT}, produces = "application/json")
    public ResponseEntity<User> updateUserPassword(@PathVariable("email") String email, @RequestBody User user){
        Optional<User> targetUser = Optional.ofNullable(userRepository.findByEmail(email));
        if(targetUser.isPresent()){
            User _user = targetUser.get();
            _user.setEmail(user.getEmail());
            _user.setName(user.getName());
            _user.setGender(user.getGender());
            _user.setPassword(user.getPassword());
            _user.setBirthday(user.getBirthday());
            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/user/{email}", method = {RequestMethod.DELETE})
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("email") String email) {
        Optional<User> targetUser = Optional.ofNullable(userRepository.findByEmail(email));
        if (targetUser.isPresent()) {
            try {
                userRepository.deleteByEmail(email);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
    }

    @RequestMapping(value = "users", method = {RequestMethod.DELETE})
    public ResponseEntity<HttpStatus> deleteAllUsers() {
        try {
            userRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

}

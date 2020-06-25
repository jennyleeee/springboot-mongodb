package com.example.springbootmongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "user")
public class User {

    @Id
    private String id;
    private String email;
    private String password;
    private String name;
    private String birthday;
    private String gender;
//    private List<TodoList> todoLists;

    private User() {
    }

//    User.id
//    User.email


    //final 상수로 만드는 습관 재할당을 막음 val var
    public User(String email, String password, String name, String birthday, String gender) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    // 유효성 검사 !!!
    public void setEmail(String email) {
        this.email = email;
//        if (!email.contains("@"))
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

//    public List<TodoList> getTodoLists() {
//        return todoLists;
//    }
//
//    public void setTodoLists(List<TodoList> todoLists) {
//        this.todoLists = todoLists;
//    }
}

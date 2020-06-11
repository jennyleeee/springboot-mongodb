package com.example.springbootmongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User {

    @Id
    private String id;
    private String email;
    private String password;
    private String userName;
    private String birthday;
    private int gender; // female = 1, male =2
}

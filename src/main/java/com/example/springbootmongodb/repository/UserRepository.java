package com.example.springbootmongodb.repository;

import com.example.springbootmongodb.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Now we can use MongoRepository’s methods:
 * save(), findOne(), findById(), findAll(),
 * count(), delete(), deleteById()…
 * without implementing these methods.
 */

@Repository
public interface UserRepository extends MongoRepository<User, String>, UserRepositoryCustom{

    void ddd();
}
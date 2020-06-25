package com.example.springbootmongodb.repository.user;

import com.example.springbootmongodb.model.User;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    private MongoTemplate mongoTemplate;
    private Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Autowired
    public UserRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<User> findAll() {
        return mongoTemplate.findAll(User.class,"user");
    }

    //    @Override
//    public List<User> findAll() {
//
//        LookupOperation lookupOperation = LookupOperation.newLookup()
//                .from("todolist").localField("email").foreignField("email").as("todoLists");
//
////        AggregationOperation match = Aggregation.match(Criteria.where("todoLists").size(1));
////        Aggregation aggregation = Aggregation.newAggregation(lookupOperation,match);
//        Aggregation aggregation = Aggregation.newAggregation(lookupOperation);
//        List<User> userResult = mongoTemplate.aggregate(aggregation,"user",User.class).getMappedResults();
//
//        return userResult;
//    }

    @Override
    public User findUserByEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        return mongoTemplate.findOne(query, User.class, "user");
//        return mongoTemplate.findById(email, User.class,"user");
    }

    @Override
    public DeleteResult deleteByEmail(String email) {
//        System.out.println("deleteByEmail");
//        System.out.println("email : " + email);
        Query query = new Query(Criteria.where("email").is(email));
        return mongoTemplate.remove(query, "user");
        //mongoTemplate.remove(findByEmail(email),"user");
//        return null;
    }

    // documents
    // raw query 가 더 생산성..
//    @Override
//    public User findUserByEmail(String email) {
//
//        LookupOperation lookupOperation = LookupOperation.newLookup()
//                .from("todolist").localField("email").foreignField("email").as("user_todolist");
//
//        Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(Criteria.where("email").is(email)),
//                lookupOperation);
//        List<User> userResult = mongoTemplate.aggregate(aggregation,"user",User.class).getMappedResults();
//
//        LOGGER.info("obj size : "+userResult.size());
//         return null;
//    }



    @Override
    public User save(User user) {
        return mongoTemplate.save(user);
    }

    @Override
    public List<User> findAllUserTodoList() {
       LookupOperation lookupOperation = LookupOperation.newLookup()
        .from("todolist").localField("email").foreignField("email").as("todoLists");

//        AggregationOperation match = Aggregation.match(Criteria.where("todoLists").size(1));
//        Aggregation aggregation = Aggregation.newAggregation(lookupOperation,match);
        Aggregation aggregation = Aggregation.newAggregation(lookupOperation);
        List<User> userResult = mongoTemplate.aggregate(aggregation,"user",User.class).getMappedResults();

        return userResult;
    }

    @Override
    public DeleteResult removeAll() {
        return mongoTemplate.remove(new Query(),"user");
    }

    @Override
    public UpdateResult updateUser(String email, User user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        Update update = new Update();
        update.set("password",user.getPassword());
        return mongoTemplate.updateMulti(query, update, User.class);
    }
}

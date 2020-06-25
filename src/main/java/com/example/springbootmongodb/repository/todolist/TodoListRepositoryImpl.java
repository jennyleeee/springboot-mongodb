package com.example.springbootmongodb.repository.todolist;

import com.example.springbootmongodb.model.TodoList;
import com.example.springbootmongodb.model.User;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class TodoListRepositoryImpl implements TodoListRepositoryCustom {

    private MongoTemplate mongoTemplate;

    @Autowired
    public TodoListRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public TodoList save(TodoList todoList) {
        return mongoTemplate.save(todoList);
    }

    @Override
    public TodoList findTodoListById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        TodoList todoList = mongoTemplate.findOne(query,TodoList.class);
        return todoList;
    }

    @Override
    public List<TodoList> findTodoListByEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        List<TodoList> todoLists = mongoTemplate.find(query,TodoList.class);
        return todoLists;
    }

    @Override
    public DeleteResult removeTodoListById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
//        TodoList todoList = mongoTemplate.remove(query,TodoList.class);
//        Criteria criteria = new Criteria("_id");
//        criteria.is(objectId);
//        Query query = new Query(criteria);
////        mongoTemplate.remove(query, "todolist");
        return mongoTemplate.remove(query, "todolist");
    }

    @Override
    public DeleteResult removeAll() {
       return mongoTemplate.remove(new Query(), "todolist");
    }

    @Override
    public List<TodoList> findAll() {
        return mongoTemplate.findAll(TodoList.class,"todolist");
    }

    @Override
    public UpdateResult updateTodoList(String id, TodoList todoList) {
//        Criteria criteria = new Criteria("_id");
//        criteria.is(id);
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("contents", todoList.getContents());
        update.set("created", todoList.getCreated());
        update.set("isdone", todoList.getIsdone());
        update.set("updated", todoList.getUpdated());
        return mongoTemplate.updateMulti(query, update, TodoList.class);
    }
}

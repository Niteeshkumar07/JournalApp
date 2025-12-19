package com.example.alpha.Repository;

import com.example.alpha.Entity.JournalEntry;
import com.example.alpha.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String userName);

}

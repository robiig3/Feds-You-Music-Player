package com.example.fedsyoubackend.repository;

import com.example.fedsyoubackend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

//legatura cu baza de date
public interface UserRepository extends MongoRepository<User, String> {

    boolean existsUserByUsernameEquals(String username);

    User findUserByUsername(String username);

}

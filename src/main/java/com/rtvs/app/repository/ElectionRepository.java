package com.rtvs.app.repository;

import com.rtvs.app.model.Election;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ElectionRepository extends MongoRepository<Election,String> {
    Optional<Election> findElectionBy(String Id);
}

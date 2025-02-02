package com.sofkau.bank.audit.repositories;

import com.sofkau.bank.audit.documents.Audit;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface AuditRepository extends ReactiveMongoRepository<Audit, UUID> {
    @Tailable
    @Query("{ '$or': [ { 'sourceAccount': ?0 }, { 'destinationAccount': ?0 } ] }")
    Flux<Audit> findAllByAccount(String account);
}

package com.sofkau.bank.repositories;

import com.sofkau.bank.documents.DepositAudit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;

public interface DepositAuditRepository extends ReactiveMongoRepository<DepositAudit, UUID> {
}

package com.sofkau.bank.audit.repositories;

import com.sofkau.bank.audit.documents.DepositAudit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;

public interface DepositAuditRepository extends ReactiveMongoRepository<DepositAudit, UUID> {
}

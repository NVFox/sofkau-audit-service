package com.sofkau.bank.audit.repositories;

import com.sofkau.bank.audit.documents.WithdrawalAudit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;

public interface WithdrawalAuditRepository extends ReactiveMongoRepository<WithdrawalAudit, UUID> {
}

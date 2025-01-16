package com.sofkau.bank.repositories;

import com.sofkau.bank.documents.WithdrawalAudit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;

public interface WithdrawalAuditRepository extends ReactiveMongoRepository<WithdrawalAudit, UUID> {
}

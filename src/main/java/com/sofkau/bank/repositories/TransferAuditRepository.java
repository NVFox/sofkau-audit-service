package com.sofkau.bank.repositories;

import com.sofkau.bank.documents.TransferAudit;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TransferAuditRepository extends ReactiveMongoRepository<TransferAudit, UUID> {
}

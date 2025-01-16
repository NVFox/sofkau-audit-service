package com.sofkau.bank.audit.repositories;

import com.sofkau.bank.audit.documents.TransferAudit;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TransferAuditRepository extends ReactiveMongoRepository<TransferAudit, UUID> {
}

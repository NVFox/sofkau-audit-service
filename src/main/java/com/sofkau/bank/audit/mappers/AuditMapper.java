package com.sofkau.bank.audit.mappers;

import com.sofkau.bank.audit.documents.Audit;
import com.sofkau.bank.audit.http.responses.GetAuditResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditMapper {
    GetAuditResponse auditToGetAuditResponse(Audit audit);
}

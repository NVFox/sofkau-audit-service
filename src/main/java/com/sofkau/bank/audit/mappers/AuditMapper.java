package com.sofkau.bank.audit.mappers;

import com.sofkau.bank.audit.documents.Audit;
import com.sofkau.bank.audit.http.requests.CreateAuditRequest;
import com.sofkau.bank.audit.http.responses.GetAuditResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuditMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", ignore = true)
    Audit createAuditRequestToAudit(CreateAuditRequest createAuditRequest);

    GetAuditResponse auditToGetAuditResponse(Audit audit);
}

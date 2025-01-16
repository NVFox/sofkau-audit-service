package com.sofkau.bank.audit.documents;

import com.sofkau.bank.audit.types.OperationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "audits")
public class Audit {
    @Id
    private UUID id;

    private OperationType operationType;

    private UUID sourceAccount;

    private UUID destinationAccount;

    private BigDecimal beforeBalanceOnSource;

    private BigDecimal afterBalanceOnSource;

    private BigDecimal beforeBalanceOnDestination;

    private BigDecimal afterBalanceOnDestination;

    private BigDecimal amount;

    @CreatedDate
    private LocalDateTime date;
}

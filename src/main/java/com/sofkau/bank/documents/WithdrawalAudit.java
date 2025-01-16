package com.sofkau.bank.documents;

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
@Document(collection = "withdrawal-audits")
public class WithdrawalAudit {
    @Id
    private UUID id;

    private UUID sourceAccount;

    private BigDecimal beforeBalance;

    private BigDecimal afterBalance;

    private BigDecimal amount;

    @CreatedDate
    private LocalDateTime date;
}

package francisco.barbosa.tinyledger.adapter.in.rest.dto;

import francisco.barbosa.tinyledger.app.model.Operation;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ResponseTransaction(UUID transactionId, Operation operation, BigDecimal amount, Instant timestamp) {
}

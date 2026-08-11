package francisco.barbosa.tinyLedger.app.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record Transaction(UUID transactionId, Operation operation, BigDecimal ammount, Instant timestamp) {
}

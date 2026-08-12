package francisco.barbosa.tinyledger.adapter.in.rest.dto;

import java.math.BigDecimal;

public record ResponseBalance(String accountId, BigDecimal balance) {
}

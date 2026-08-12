package francisco.barbosa.tinyledger.adapter.in.rest.dto;

import java.util.List;

public record ResponseTransactionHistory(List<ResponseTransaction> transactionList) {
}

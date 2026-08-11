package francisco.barbosa.tinyLedger.app;

import francisco.barbosa.tinyLedger.app.model.Operation;
import francisco.barbosa.tinyLedger.app.model.TransactionHistory;

import java.math.BigDecimal;

public interface TransactionHistoryRepository {
    TransactionHistory getTransactionHistory(String accountId);
    void updateTransactionHistory(String accountId, Operation operation, BigDecimal ammount);
}

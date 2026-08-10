package francisco.barbosa.tinyLedger.app;

import francisco.barbosa.tinyLedger.app.model.TransactionHistory;

import java.math.BigDecimal;

public interface AccountRepository {
    void add(String accountId, BigDecimal ammount);

    void remove(String accountId, BigDecimal ammount);

    BigDecimal getAccountBalance(String accountId);

    TransactionHistory getTransactionHistory(String accountId);
}

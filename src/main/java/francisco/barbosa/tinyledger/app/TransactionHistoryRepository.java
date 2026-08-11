package francisco.barbosa.tinyledger.app;

import francisco.barbosa.tinyledger.app.model.Operation;
import francisco.barbosa.tinyledger.app.model.TransactionHistory;
import java.math.BigDecimal;

public interface TransactionHistoryRepository {
	TransactionHistory getTransactionHistory(String accountId);

	void updateTransactionHistory(String accountId, Operation operation, BigDecimal ammount);
}

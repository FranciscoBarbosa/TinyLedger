package francisco.barbosa.tinyLedger.adapter.out;

import francisco.barbosa.tinyLedger.app.TransactionHistoryRepository;
import francisco.barbosa.tinyLedger.app.model.Operation;
import francisco.barbosa.tinyLedger.app.model.Transaction;
import francisco.barbosa.tinyLedger.app.model.TransactionHistory;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionHistoryInMemoryRepository implements TransactionHistoryRepository {
	private final Map<String, TransactionHistory> transactionHistoryStore;

	public TransactionHistoryInMemoryRepository() {
		this.transactionHistoryStore = new HashMap<>();
	}

	@Override
	public TransactionHistory getTransactionHistory(String accountId) {
		return transactionHistoryStore.get(accountId);
	}

	@Override
	public void updateTransactionHistory(String accountId, Operation operation, BigDecimal ammount) {
		UUID transactionId = UUID.randomUUID();
		Transaction transaction = new Transaction(transactionId, operation, ammount, Instant.now());

		transactionHistoryStore.computeIfAbsent(accountId, key -> {
			TransactionHistory transactionHistory = new TransactionHistory(key);
			transactionHistory.addTransaction(transaction);
			return transactionHistory;
		});
	}
}

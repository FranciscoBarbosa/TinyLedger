package francisco.barbosa.tinyLedger.app.model;

import java.util.LinkedHashSet;

public class TransactionHistory {
	private String accountId;
	private LinkedHashSet<Transaction> transactionList;

	public TransactionHistory(String accountId) {
		this.accountId = accountId;
		transactionList = new LinkedHashSet<>();
	}

	public void addTransaction(Transaction transaction) {
		transactionList.add(transaction);
	}
}

package francisco.barbosa.tinyledger.app.model;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistory {
	private String accountId;
	private List<Transaction> transactionList;

	public TransactionHistory(String accountId) {
		this.accountId = accountId;
		transactionList = new ArrayList<>();
	}

	public void addTransaction(Transaction transaction) {
		transactionList.add(transaction);
	}
}

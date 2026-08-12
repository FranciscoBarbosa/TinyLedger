package francisco.barbosa.tinyledger.app.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistory {
	@Getter
	private List<Transaction> transactionList;

	public TransactionHistory() {
		transactionList = new ArrayList<>();
	}

	public void addTransaction(Transaction transaction) {
		transactionList.add(transaction);
	}
}

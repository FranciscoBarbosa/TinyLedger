package francisco.barbosa.tinyledger.app.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import francisco.barbosa.tinyledger.app.exception.OperationNotAllowedException;
import lombok.Getter;

@Getter
public class Account {
	private final String accountId;
	private final TransactionHistory transactionHistory;

	private BigDecimal balance;

	public Account() {
		this.accountId = UUID.randomUUID().toString();
		balance = BigDecimal.ZERO;
		transactionHistory = new TransactionHistory();
	}

	public Account(String accountId, BigDecimal balance) {
		this.accountId = accountId;
		this.balance = balance;
		transactionHistory = new TransactionHistory();
	}

	public Transaction withdraw(BigDecimal amount) {
		if (balance.compareTo(amount) < 0) {
			throw new OperationNotAllowedException(
					"Withdraw operation is not allowed, as the account does not have enough balance.");
		}

		this.balance = balance.subtract(amount);
		return createNewTransaction(Operation.WITHDRAW, amount);
	}

	public Transaction deposit(BigDecimal amount) {
		this.balance = balance.add(amount);
		return createNewTransaction(Operation.DEPOSIT, amount);
	}

	private Transaction createNewTransaction(Operation operation, BigDecimal amount) {
		Transaction currentTransaction = new Transaction(UUID.randomUUID(), operation, amount, Instant.now());
		transactionHistory.addTransaction(currentTransaction);

		return currentTransaction;
	}
}

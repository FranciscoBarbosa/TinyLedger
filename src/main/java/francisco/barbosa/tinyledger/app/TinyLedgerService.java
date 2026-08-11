package francisco.barbosa.tinyledger.app;

import francisco.barbosa.tinyledger.app.exception.AccountNotFoundException;
import francisco.barbosa.tinyledger.app.exception.OperationNotAllowedException;
import francisco.barbosa.tinyledger.app.model.Operation;
import francisco.barbosa.tinyledger.app.model.TransactionHistory;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TinyLedgerService {
	private final AccountRepository accountRepository;
	private final TransactionHistoryRepository transactionHistoryRepository;

	public void deposit(String accountId, BigDecimal ammount) {
		validateAccountId(accountId);
		accountRepository.add(accountId, ammount);
		transactionHistoryRepository.updateTransactionHistory(accountId, Operation.DEPOSIT, ammount);
	}

	public void withdraw(String accountId, BigDecimal ammount) {
		validateAccountId(accountId);
		BigDecimal accountBalance = accountRepository.getAccountBalance(accountId);
		if (accountBalance.compareTo(ammount) < 0) {
			throw new OperationNotAllowedException(
					"Withdraw operation is not allowed, as the account does not have enough balance.");
		}
		accountRepository.remove(accountId, ammount);
		transactionHistoryRepository.updateTransactionHistory(accountId, Operation.WITHDRAW, ammount);
	}

	public BigDecimal viewBalance(String accountId) {
		validateAccountId(accountId);
		return accountRepository.getAccountBalance(accountId);
	}

	public TransactionHistory getTransactionHistory(String accountId) {
		validateAccountId(accountId);
		return transactionHistoryRepository.getTransactionHistory(accountId);
	}

	private void validateAccountId(String accountId) {
		if (!accountRepository.accountExists(accountId)) {
			throw new AccountNotFoundException("Account");
		}
	}
}

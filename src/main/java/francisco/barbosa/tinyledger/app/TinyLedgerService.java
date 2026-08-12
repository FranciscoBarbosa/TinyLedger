package francisco.barbosa.tinyledger.app;

import francisco.barbosa.tinyledger.app.exception.AccountNotFoundException;
import francisco.barbosa.tinyledger.app.model.Account;
import francisco.barbosa.tinyledger.app.model.Transaction;
import francisco.barbosa.tinyledger.app.model.TransactionHistory;
import java.math.BigDecimal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TinyLedgerService {
	private final AccountRepository accountRepository;

	public Transaction deposit(String accountId, BigDecimal amount) {
		Account account = getAccount(accountId);
		Transaction transaction = account.deposit(amount);
		accountRepository.updateAccount(accountId, account);
		return transaction;
	}

	public Transaction withdraw(String accountId, BigDecimal amount) {
		Account account = getAccount(accountId);
		Transaction transaction = account.withdraw(amount);
		accountRepository.updateAccount(accountId, account);
		return transaction;
	}

	public BigDecimal viewBalance(String accountId) {
		return getAccount(accountId).getBalance();
	}

	public TransactionHistory getTransactionHistory(String accountId) {
		Account account = getAccount(accountId);
		return account.getTransactionHistory();
	}

	private Account getAccount(String accountId) {
		return accountRepository.getAccount(accountId)
				.orElseThrow(() -> new AccountNotFoundException("Account doesn't exist"));
	}
}

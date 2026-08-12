package francisco.barbosa.tinyledger.app;

import francisco.barbosa.tinyledger.app.model.Account;

import java.util.Optional;

public interface AccountRepository {
	Optional<Account> getAccount(String accountId);
	void updateAccount(String accountId, Account account);
}

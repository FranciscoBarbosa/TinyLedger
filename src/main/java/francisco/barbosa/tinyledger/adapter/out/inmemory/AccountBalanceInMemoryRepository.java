package francisco.barbosa.tinyledger.adapter.out.inmemory;

import francisco.barbosa.tinyledger.app.*;
import francisco.barbosa.tinyledger.app.model.Account;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class AccountBalanceInMemoryRepository implements AccountRepository {
	public static final String UUID_ACCOUNT_FRANCISCO = "fd1bb2a6-b1bb-4608-bdfb-cdda9a8a657f";
	public static final String UUID_ACCOUNT_MARIA = "94b6dc8f-7134-4df0-9911-2322ad7231e9";

	private final Map<String, Account> accountStore;

	public AccountBalanceInMemoryRepository() {
		this.accountStore = new HashMap<>();
		loadAndResetAccountsData();
	}

	@Override
	public Optional<Account> getAccount(String accountId) {
		return Optional.ofNullable(accountStore.get(accountId));
	}

	@Override
	public void updateAccount(String accountId, Account account) {
		accountStore.put(accountId, account);
	}

	public void loadAndResetAccountsData() {
		Account accountFrancisco = new Account(UUID_ACCOUNT_FRANCISCO, new BigDecimal("100"));
		Account accountMaria = new Account(UUID_ACCOUNT_MARIA, new BigDecimal("200"));

		accountStore.put(UUID_ACCOUNT_FRANCISCO, accountFrancisco);
		accountStore.put(UUID_ACCOUNT_MARIA, accountMaria);
	}
}

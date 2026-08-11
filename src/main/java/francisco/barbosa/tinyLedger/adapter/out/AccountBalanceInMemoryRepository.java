package francisco.barbosa.tinyLedger.adapter.out;

import francisco.barbosa.tinyLedger.app.*;
import francisco.barbosa.tinyLedger.app.model.Account;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class AccountBalanceInMemoryRepository implements AccountRepository {
	public static final String UUID_ACCOUNT_FRANCISCO = "fd1bb2a6-b1bb-4608-bdfb-cdda9a8a657f";
	public static final String UUID_ACCOUNT_MARIA = "94b6dc8f-7134-4df0-9911-2322ad7231e9";

	private final Map<String, Account> accountStore;

	public AccountBalanceInMemoryRepository() {
		this.accountStore = new HashMap<>();
		loadAccountsData();
	}

	@Override
	public boolean accountExists(String accountId) {
		return accountStore.containsKey(accountId);
	}

	@Override
	public void add(String accountId, BigDecimal ammount) {
		accountStore.computeIfPresent(accountId, (id, account) -> {
			BigDecimal previousBalance = account.getBalance();
			account.setBalance(previousBalance.add(ammount));

			return account;
		});
	}

	@Override
	public void remove(String accountId, BigDecimal ammount) {
		accountStore.computeIfPresent(accountId, (id, account) -> {
			BigDecimal previousBalance = account.getBalance();
			account.setBalance(previousBalance.subtract(ammount));

			return account;
		});
	}

	@Override
	public BigDecimal getAccountBalance(String accountId) {
		return accountStore.get(accountId).getBalance();
	}

	private void loadAccountsData() {
		Account accountFrancisco = new Account(UUID_ACCOUNT_FRANCISCO, new BigDecimal("100"));
		Account accountMaria = new Account(UUID_ACCOUNT_MARIA, new BigDecimal("200"));

		accountStore.put(UUID_ACCOUNT_FRANCISCO, accountFrancisco);
		accountStore.put(UUID_ACCOUNT_MARIA, accountMaria);
	}
}

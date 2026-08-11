package francisco.barbosa.tinyLedger.app;

import java.math.BigDecimal;

public interface AccountRepository {
	boolean accountExists(String accountId);

	void add(String accountId, BigDecimal ammount);

	void remove(String accountId, BigDecimal ammount);

	BigDecimal getAccountBalance(String accountId);
}

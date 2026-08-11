package francisco.barbosa.tinyledger.app.model;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Account {
	private final String accountId;
	@Setter
	private BigDecimal balance;

	public Account() {
		this.accountId = UUID.randomUUID().toString();
		balance = BigDecimal.ZERO;
	}

	public Account(String accountId, BigDecimal balance) {
		this.accountId = accountId;
		this.balance = balance;
	}
}

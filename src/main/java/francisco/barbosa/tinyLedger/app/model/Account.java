package francisco.barbosa.tinyLedger.app.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class Account {
    private final String accountId;
    @Setter
    private BigDecimal balance;

    public Account(){
        this.accountId = UUID.randomUUID().toString();
        balance = BigDecimal.ZERO;
    }

    public Account(String accountId, BigDecimal balance) {
        this.accountId = accountId;
        this.balance = balance;
    }
}

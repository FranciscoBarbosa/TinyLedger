package francisco.barbosa.tinyLedger.adapter.out;

import francisco.barbosa.tinyLedger.app.*;
import francisco.barbosa.tinyLedger.app.exception.AccountNotFoundException;
import francisco.barbosa.tinyLedger.app.model.Account;
import francisco.barbosa.tinyLedger.app.model.Operation;
import francisco.barbosa.tinyLedger.app.model.Transaction;
import francisco.barbosa.tinyLedger.app.model.TransactionHistory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class AccountRepositoryInMemory implements AccountRepository {
    private final Map<String, Account> accountStore;
    private final Map<String, TransactionHistory> transactionHistoryStore;

    private static final String UUID_ACCOUNT_FRANCISCO = "fd1bb2a6-b1bb-4608-bdfb-cdda9a8a657f";
    private static final String UUID_ACCOUNT_MARIA = "94b6dc8f-7134-4df0-9911-2322ad7231e9";

    public AccountRepositoryInMemory() {
        accountStore = new HashMap<>();
        transactionHistoryStore = new HashMap<>();
        loadAccountsData();
    }

    @Override
    public void add(String accountId, BigDecimal ammount){
        validateAccountId(accountId);
        accountStore.computeIfPresent(accountId, (id, account) -> {
            BigDecimal previousBalance = account.getBalance();
            account.setBalance(previousBalance.add(ammount));

            return account;
        });

        updateTransactionHistory(accountId, Operation.DEPOSIT, ammount);
    }

    @Override
    public void remove(String accountId, BigDecimal ammount){
        validateAccountId(accountId);
        accountStore.computeIfPresent(accountId, (id, account) -> {
            BigDecimal previousBalance = account.getBalance();
            account.setBalance(previousBalance.subtract(ammount));

            return account;
        });

        updateTransactionHistory(accountId, Operation.WITHDRAW, ammount);
    }

    @Override
    public BigDecimal getAccountBalance(String accountId) {
        validateAccountId(accountId);
        return accountStore.get(accountId).getBalance();
    }

    @Override
    public TransactionHistory getTransactionHistory(String accountId){
        return transactionHistoryStore.get(accountId);
    }

    private void validateAccountId(String accountId) {
        if(!accountStore.containsKey(accountId)){
            throw new AccountNotFoundException("Account");
        }
    }

    private void updateTransactionHistory(String accountId, Operation operation, BigDecimal ammount){
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transaction(transactionId, operation, ammount, Instant.now());

        transactionHistoryStore.computeIfAbsent(accountId,key -> {
            TransactionHistory transactionHistory = new TransactionHistory(key);
            transactionHistory.addTransaction(transaction);
            return transactionHistory;
        });
    }

    private void loadAccountsData(){
        Account accountFrancisco = new Account(UUID_ACCOUNT_FRANCISCO, new BigDecimal("100"));
        Account accountMaria = new Account(UUID_ACCOUNT_MARIA, new BigDecimal("200"));

        accountStore.put(UUID_ACCOUNT_FRANCISCO, accountFrancisco);
        accountStore.put(UUID_ACCOUNT_MARIA, accountMaria);
    }
}

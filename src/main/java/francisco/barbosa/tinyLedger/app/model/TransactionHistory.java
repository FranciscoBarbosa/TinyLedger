package francisco.barbosa.tinyLedger.app.model;

import java.util.HashSet;
import java.util.Set;

public class TransactionHistory {
    private String accountId;
    private Set<Transaction> transactionList;

    public TransactionHistory(String accountId) {
        this.accountId = accountId;
        transactionList = new HashSet<>();
    }

    public void addTransaction(Transaction transaction){
        transactionList.add(transaction);
    }
}

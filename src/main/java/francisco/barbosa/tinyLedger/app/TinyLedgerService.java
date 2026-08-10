package francisco.barbosa.tinyLedger.app;

import francisco.barbosa.tinyLedger.app.exception.OperationNotAllowed;
import francisco.barbosa.tinyLedger.app.model.TransactionHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TinyLedgerService {
    private final AccountRepository accountRepository;

    public void deposit(String accountId, BigDecimal ammount){
        accountRepository.add(accountId, ammount);
    }

    public void withdraw(String accountId, BigDecimal ammount){
        BigDecimal accountBalance = accountRepository.getAccountBalance(accountId);
        if(accountBalance.compareTo(ammount) < 0){
            throw new OperationNotAllowed("Withdraw operation is not allowed, as the account does not have enough balance.");
        }
        accountRepository.remove(accountId, ammount);
    }

    public BigDecimal viewBalance(String accountId){
        return accountRepository.getAccountBalance(accountId);
    }

    public TransactionHistory getTransactionHistory(String accountId){
        return accountRepository.getTransactionHistory(accountId);
    }

}

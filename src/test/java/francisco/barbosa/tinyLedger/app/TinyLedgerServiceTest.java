package francisco.barbosa.tinyLedger.app;

import francisco.barbosa.tinyLedger.app.exception.AccountNotFoundException;
import francisco.barbosa.tinyLedger.app.exception.OperationNotAllowedException;
import francisco.barbosa.tinyLedger.app.model.Operation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static francisco.barbosa.tinyLedger.adapter.out.AccountBalanceInMemoryRepository.UUID_ACCOUNT_FRANCISCO;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TinyLedgerServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TransactionHistoryRepository transactionHistoryRepository;

    private TinyLedgerService tinyLedgerService;

    @BeforeEach
    void setup(){
        tinyLedgerService = new TinyLedgerService(accountRepository, transactionHistoryRepository);
    }

    @Test
    void shouldWithdrawAmountWhenEnoughBalance() {
        var withdrawnAmount = new BigDecimal("200.50");
        when(accountRepository.accountExists(eq(UUID_ACCOUNT_FRANCISCO))).thenReturn(true);
        when(accountRepository.getAccountBalance(eq(UUID_ACCOUNT_FRANCISCO))).thenReturn(new BigDecimal("300"));

        tinyLedgerService.withdraw(UUID_ACCOUNT_FRANCISCO, withdrawnAmount);

        verify(accountRepository).remove(UUID_ACCOUNT_FRANCISCO, withdrawnAmount);
        verify(transactionHistoryRepository).updateTransactionHistory(UUID_ACCOUNT_FRANCISCO, Operation.WITHDRAW, withdrawnAmount);
    }

    @Test
    void shouldNotWidhdrawAmountWhenNotEnoughBalance() {
        var withdrawnAmount = new BigDecimal("200.50");
        when(accountRepository.accountExists(eq(UUID_ACCOUNT_FRANCISCO))).thenReturn(true);
        when(accountRepository.getAccountBalance(eq(UUID_ACCOUNT_FRANCISCO))).thenReturn(new BigDecimal("100"));

        Assertions.assertThatThrownBy(
                () -> tinyLedgerService.withdraw(UUID_ACCOUNT_FRANCISCO, withdrawnAmount)
        ).isInstanceOf(OperationNotAllowedException.class);

        verifyNoInteractions(transactionHistoryRepository);
    }

    @Test
    void shouldNotWithdrawAmountWhenAccountDoesntExist() {
        var withdrawnAmount = new BigDecimal("200.50");
        when(accountRepository.accountExists(eq(UUID_ACCOUNT_FRANCISCO))).thenReturn(false);

        Assertions.assertThatThrownBy(
                () -> tinyLedgerService.withdraw(UUID_ACCOUNT_FRANCISCO, withdrawnAmount)
        ).isInstanceOf(AccountNotFoundException.class);

        verifyNoInteractions(transactionHistoryRepository);
    }

    @Test
    void shouldDepositAmountWhenAccountExist() {
        var withdrawnAmount = new BigDecimal("200.50");
        when(accountRepository.accountExists(eq(UUID_ACCOUNT_FRANCISCO))).thenReturn(true);

        tinyLedgerService.deposit(UUID_ACCOUNT_FRANCISCO, withdrawnAmount);

        verify(transactionHistoryRepository).updateTransactionHistory(UUID_ACCOUNT_FRANCISCO, Operation.DEPOSIT, withdrawnAmount);
    }

    @Test
    void shouldNotDepositAmountWhenAccountDoesntExist() {
        var withdrawnAmount = new BigDecimal("200.50");
        when(accountRepository.accountExists(eq(UUID_ACCOUNT_FRANCISCO))).thenReturn(false);

        Assertions.assertThatThrownBy(
                () -> tinyLedgerService.deposit(UUID_ACCOUNT_FRANCISCO, withdrawnAmount)
        ).isInstanceOf(AccountNotFoundException.class);

        verifyNoInteractions(transactionHistoryRepository);
    }

    @Test
    void shouldNotViewTransactionHistoryAmountWhenAccountDoesntExist() {
        when(accountRepository.accountExists(eq(UUID_ACCOUNT_FRANCISCO))).thenReturn(false);

        Assertions.assertThatThrownBy(
                () -> tinyLedgerService.getTransactionHistory(UUID_ACCOUNT_FRANCISCO)
        ).isInstanceOf(AccountNotFoundException.class);

        verifyNoInteractions(transactionHistoryRepository);
    }

    @Test
    void shouldNotViewAmountWhenAccountDoesntExist() {
        when(accountRepository.accountExists(eq(UUID_ACCOUNT_FRANCISCO))).thenReturn(false);

        Assertions.assertThatThrownBy(
                () -> tinyLedgerService.viewBalance(UUID_ACCOUNT_FRANCISCO)
        ).isInstanceOf(AccountNotFoundException.class);
    }
}
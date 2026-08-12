package francisco.barbosa.tinyledger.app;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import francisco.barbosa.tinyledger.app.exception.AccountNotFoundException;
import francisco.barbosa.tinyledger.app.exception.OperationNotAllowedException;
import francisco.barbosa.tinyledger.app.model.Account;
import francisco.barbosa.tinyledger.app.model.Operation;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import francisco.barbosa.tinyledger.app.model.Transaction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TinyLedgerServiceTest {
	@Mock
	private AccountRepository accountRepository;

	private TinyLedgerService tinyLedgerService;

	@BeforeEach
	void setup() {
		tinyLedgerService = new TinyLedgerService(accountRepository);
	}

	@Test
	void shouldWithdrawAmountAndCreateNewTransaction_WhenEnoughBalance() {
		String testAccountUUID = UUID.randomUUID().toString();
		Account testAccount = new Account(testAccountUUID, BigDecimal.TEN);
		var withdrawnAmount = new BigDecimal("1.50");
		when(accountRepository.getAccount(eq(testAccountUUID))).thenReturn(Optional.of(testAccount));

		tinyLedgerService.withdraw(testAccountUUID, withdrawnAmount);

		BigDecimal expectedFinalBalance = new BigDecimal("8.50");
		Transaction currentTransaction = testAccount.getTransactionHistory().getTransactionList().getFirst();
		verify(accountRepository).updateAccount(testAccountUUID, testAccount);
		Assertions.assertThat(testAccount.getBalance()).isEqualTo(expectedFinalBalance);
		Assertions.assertThat(currentTransaction.operation()).isEqualTo(Operation.WITHDRAW);
		Assertions.assertThat(currentTransaction.amount()).isEqualTo(new BigDecimal("1.50"));
	}

	@Test
	void shouldNotWidhdrawAmount_WhenNotEnoughBalance() {
		String testAccountUUID = UUID.randomUUID().toString();
		Account testAccount = new Account(testAccountUUID, BigDecimal.TEN);
		var withdrawnAmount = new BigDecimal("14.50");
		when(accountRepository.getAccount(eq(testAccountUUID))).thenReturn(Optional.of(testAccount));

		Assertions.assertThatThrownBy(() -> tinyLedgerService.withdraw(testAccountUUID, withdrawnAmount))
				.isInstanceOf(OperationNotAllowedException.class);
	}

	@Test
	void shouldNotWithdrawAmount_WhenAccountDoesntExist() {
		when(accountRepository.getAccount(any())).thenReturn(Optional.empty());

		Assertions
				.assertThatThrownBy(
						() -> tinyLedgerService.withdraw(UUID.randomUUID().toString(), new BigDecimal("200.50")))
				.isInstanceOf(AccountNotFoundException.class);
	}

	@Test
	void shouldDepositAmountAndCreateNewTransaction_WhenAccountExist() {
		var expectedFinalBalance = new BigDecimal("24.50");
		String testAccountUUID = UUID.randomUUID().toString();
		Account testAccount = new Account(testAccountUUID, BigDecimal.TEN);
		var depositAmount = new BigDecimal("14.50");
		when(accountRepository.getAccount(eq(testAccountUUID))).thenReturn(Optional.of(testAccount));

		tinyLedgerService.deposit(testAccountUUID, depositAmount);

		Transaction currentTransaction = testAccount.getTransactionHistory().getTransactionList().getFirst();
		verify(accountRepository).updateAccount(testAccountUUID, testAccount);
		Assertions.assertThat(testAccount.getBalance()).isEqualTo(expectedFinalBalance);
		Assertions.assertThat(currentTransaction.operation()).isEqualTo(Operation.DEPOSIT);
		Assertions.assertThat(currentTransaction.amount()).isEqualTo(new BigDecimal("14.50"));
	}

	@Test
	void shouldNotDepositAmountWhenAccountDoesntExist() {
		when(accountRepository.getAccount(any())).thenReturn(Optional.empty());

		Assertions
				.assertThatThrownBy(
						() -> tinyLedgerService.deposit(UUID.randomUUID().toString(), new BigDecimal("1.0")))
				.isInstanceOf(AccountNotFoundException.class);
	}

	@Test
	void shouldNotViewTransactionHistoryAmountWhenAccountDoesntExist() {
		when(accountRepository.getAccount(any())).thenReturn(Optional.empty());

		Assertions.assertThatThrownBy(() -> tinyLedgerService.getTransactionHistory(UUID.randomUUID().toString()))
				.isInstanceOf(AccountNotFoundException.class);
	}

	@Test
	void shouldNotViewAmountWhenAccountDoesntExist() {
		when(accountRepository.getAccount(any())).thenReturn(Optional.empty());

		Assertions.assertThatThrownBy(() -> tinyLedgerService.viewBalance(UUID.randomUUID().toString()))
				.isInstanceOf(AccountNotFoundException.class);
	}
}

package francisco.barbosa.tinyledger.adapter.in.rest;

import francisco.barbosa.tinyledger.adapter.in.rest.dto.ResponseBalance;
import francisco.barbosa.tinyledger.adapter.in.rest.dto.ResponseTransaction;
import francisco.barbosa.tinyledger.adapter.in.rest.dto.ResponseTransactionHistory;
import francisco.barbosa.tinyledger.adapter.out.inmemory.AccountBalanceInMemoryRepository;
import francisco.barbosa.tinyledger.app.model.Operation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TinyLedgerControllerIT {
	private static final String FRANCISCO_LEDGER_URL = "/v1/ledgers/fd1bb2a6-b1bb-4608-bdfb-cdda9a8a657f";

	@Autowired
	private AccountBalanceInMemoryRepository accountBalanceInMemoryRepository;

	@LocalServerPort
	private int port;
	private RestClient restClient;

	@BeforeEach
	void setUp() {
		restClient = RestClient.builder().baseUrl("http://localhost:" + port).build();
		accountBalanceInMemoryRepository.loadAndResetAccountsData();
	}

	@Test
	void shouldDepositInAccount() {
		String depositUri = FRANCISCO_LEDGER_URL + "/deposits";
		var response = restClient.post().uri(depositUri).body("10").retrieve().toEntity(ResponseTransaction.class);

		Assertions.assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
	}

	@Test
	void shouldWithdrawFromAccount() {
		String withdrawUri = FRANCISCO_LEDGER_URL + "/withdraws";
		var response = restClient.post().uri(withdrawUri).body("20").retrieve().toEntity(ResponseTransaction.class);

		Assertions.assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
	}

	@Test
	void shouldViewBalance_AfterDepositInAccount() {
		depositInAccount("10.0");

		var response = restClient.get().uri(FRANCISCO_LEDGER_URL).retrieve().toEntity(ResponseBalance.class);

		Assertions.assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		Assertions.assertThat(response.getBody().balance()).isEqualTo("110.0");
	}

	@Test
	void shouldViewBalance_AfterWithdrawInAccount() {
		withdrawFromAccount("12.0");

		var response = restClient.get().uri(FRANCISCO_LEDGER_URL).retrieve().toEntity(ResponseBalance.class);

		Assertions.assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		Assertions.assertThat(response.getBody().balance()).isEqualTo("88.0");
	}

	@Test
	void shouldViewTransactionsHistory_AfterMultipleTransactions_AndViewFinalBalance() {
		String transactionHistoryUri = FRANCISCO_LEDGER_URL + "/transactions";
		withdrawFromAccount("12.23");
		depositInAccount("54.12");
		withdrawFromAccount("27.30");
		withdrawFromAccount("44.20");

		var transactionHistoryResponseEntity = restClient.get().uri(transactionHistoryUri).retrieve()
				.toEntity(ResponseTransactionHistory.class);

		Assertions.assertThat(transactionHistoryResponseEntity.getStatusCode().is2xxSuccessful()).isTrue();
		Assertions.assertThat(transactionHistoryResponseEntity.getBody().transactionList().size()).isEqualTo(4);

		Assertions.assertThat(transactionHistoryResponseEntity.getBody().transactionList().getFirst().amount())
				.isEqualTo(new BigDecimal("12.23"));
		Assertions.assertThat(transactionHistoryResponseEntity.getBody().transactionList().getFirst().operation())
				.isEqualTo(Operation.WITHDRAW);

		Assertions.assertThat(transactionHistoryResponseEntity.getBody().transactionList().get(1).amount())
				.isEqualTo(new BigDecimal("54.12"));
		Assertions.assertThat(transactionHistoryResponseEntity.getBody().transactionList().get(1).operation())
				.isEqualTo(Operation.DEPOSIT);

		Assertions.assertThat(transactionHistoryResponseEntity.getBody().transactionList().get(2).amount())
				.isEqualTo(new BigDecimal("27.30"));
		Assertions.assertThat(transactionHistoryResponseEntity.getBody().transactionList().get(2).operation())
				.isEqualTo(Operation.WITHDRAW);

		Assertions.assertThat(transactionHistoryResponseEntity.getBody().transactionList().get(3).amount())
				.isEqualTo(new BigDecimal("44.20"));
		Assertions.assertThat(transactionHistoryResponseEntity.getBody().transactionList().get(3).operation())
				.isEqualTo(Operation.WITHDRAW);

		var viewBalanceResponseEntity = restClient.get().uri(FRANCISCO_LEDGER_URL).retrieve()
				.toEntity(ResponseBalance.class);
		Assertions.assertThat(viewBalanceResponseEntity.getStatusCode().is2xxSuccessful()).isTrue();
		Assertions.assertThat(viewBalanceResponseEntity.getBody().balance()).isEqualTo("70.39");
	}

	private void depositInAccount(String amount) {
		String depositUri = FRANCISCO_LEDGER_URL + "/deposits";
		restClient.post().uri(depositUri).body(amount).retrieve().toBodilessEntity();
	}

	private void withdrawFromAccount(String amount) {
		String depositUri = FRANCISCO_LEDGER_URL + "/withdraws";
		restClient.post().uri(depositUri).body(amount).retrieve().toBodilessEntity();
	}
}

package francisco.barbosa.tinyLedger.adapter.in.rest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TinyLedgerControllerIT {
	private static final String FRANCISCO_LEDGER_URL = "/v1/ledgers/fd1bb2a6-b1bb-4608-bdfb-cdda9a8a657f";

	@LocalServerPort
	private int port;
	private RestClient restClient;

	@BeforeEach
	void setUp() {
		restClient = RestClient.builder().baseUrl("http://localhost:" + port).build();
	}

	@Test
	void shouldDepositInAccount() {
		String depositUri = FRANCISCO_LEDGER_URL + "/deposits";
		var response = restClient.post().uri(depositUri).body("10").retrieve().toBodilessEntity();

		Assertions.assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
	}

	@Test
	void shouldWithdrawFromAccount() {
		String withdrawUri = FRANCISCO_LEDGER_URL + "/withdraws";
		var response = restClient.post().uri(withdrawUri).body("20").retrieve().toBodilessEntity();

		Assertions.assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
	}

	@Test
	void shouldViewBalanceInAccount() {
		var response = restClient.get().uri(FRANCISCO_LEDGER_URL).retrieve().toEntity(BigDecimal.class);

		Assertions.assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		Assertions.assertThat(response.getBody()).isEqualTo(BigDecimal.valueOf(100));
	}
}

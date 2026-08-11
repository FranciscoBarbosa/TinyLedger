package francisco.barbosa.tinyledger.adapter.in.rest;

import francisco.barbosa.tinyledger.app.TinyLedgerService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/ledgers")
@RequiredArgsConstructor
public class TinyLedgerController {
	private final TinyLedgerService tinyLedgerService;

	@GetMapping("/{accountId}")
	ResponseEntity<BigDecimal> viewBalance(@PathVariable String accountId) {
		return ResponseEntity.ok(tinyLedgerService.viewBalance(accountId));
	}

	@PostMapping("/{accountId}/deposits")
	void deposit(@PathVariable String accountId, @RequestBody String amount) {
		tinyLedgerService.deposit(accountId, new BigDecimal(amount));
	}

	@PostMapping("/{accountId}/withdraws")
	void withdraw(@PathVariable String accountId, @RequestBody String amount) {
		tinyLedgerService.withdraw(accountId, new BigDecimal(amount));
	}

	@GetMapping("/{accountId}/transactions")
	void viewTransactionHistory(@PathVariable String accountId) {
		tinyLedgerService.getTransactionHistory(accountId);
	}
}

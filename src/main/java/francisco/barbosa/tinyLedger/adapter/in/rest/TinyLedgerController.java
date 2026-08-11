package francisco.barbosa.tinyLedger.adapter.in.rest;

import francisco.barbosa.tinyLedger.app.TinyLedgerService;
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
		try {
			return ResponseEntity.ok(tinyLedgerService.viewBalance(accountId));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/{accountId}/deposits")
	void deposit(@PathVariable String accountId, @RequestBody String amount) {
		try {
			tinyLedgerService.deposit(accountId, new BigDecimal(amount));
		} catch (Exception e) {

		}
	}

	@PostMapping("/{accountId}/withdraws")
	void withdraw(@PathVariable String accountId, @RequestBody String amount) {
		try {
			tinyLedgerService.withdraw(accountId, new BigDecimal(amount));
		} catch (Exception e) {

		}
	}

	@GetMapping("/{accountId}/transactions")
	void viewTransactionHistory(@PathVariable String accountId) {
		try {
			tinyLedgerService.getTransactionHistory(accountId);
		} catch (Exception e) {

		}
	}
}

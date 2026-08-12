package francisco.barbosa.tinyledger.adapter.in.rest;

import francisco.barbosa.tinyledger.adapter.in.rest.dto.ResponseBalance;
import francisco.barbosa.tinyledger.adapter.in.rest.dto.ResponseTransaction;
import francisco.barbosa.tinyledger.adapter.in.rest.dto.ResponseTransactionHistory;
import francisco.barbosa.tinyledger.app.TinyLedgerService;
import java.math.BigDecimal;
import java.util.List;

import francisco.barbosa.tinyledger.app.model.Transaction;
import francisco.barbosa.tinyledger.app.model.TransactionHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.util.Tuple;

@RestController
@RequestMapping("/v1/ledgers")
@RequiredArgsConstructor
public class TinyLedgerController {
	private final TinyLedgerService tinyLedgerService;

	@GetMapping("/{accountId}")
	ResponseEntity<ResponseBalance> viewBalance(@PathVariable String accountId) {
		Tuple<String, String> accountBalanceTupple = tinyLedgerService.viewBalance(accountId);
		var accountBalance = new ResponseBalance(accountBalanceTupple._1(), accountBalanceTupple._2());
		return ResponseEntity.ok(accountBalance);
	}

	@PostMapping("/{accountId}/deposits")
	ResponseEntity<ResponseTransaction> deposit(@PathVariable String accountId, @RequestBody String amount) {
		Transaction transaction = tinyLedgerService.deposit(accountId, new BigDecimal(amount));
		ResponseTransaction responseTransaction = mapTransactionToResponseTransaction(transaction);
		return ResponseEntity.ok(responseTransaction);
	}

	@PostMapping("/{accountId}/withdraws")
	ResponseEntity<ResponseTransaction> withdraw(@PathVariable String accountId, @RequestBody String amount) {
		Transaction transaction = tinyLedgerService.withdraw(accountId, new BigDecimal(amount));
		ResponseTransaction responseTransaction = mapTransactionToResponseTransaction(transaction);
		return ResponseEntity.ok(responseTransaction);
	}

	@GetMapping("/{accountId}/transactions")
	ResponseEntity<ResponseTransactionHistory> viewTransactionHistory(@PathVariable String accountId) {
		TransactionHistory transactionHistory = tinyLedgerService.getTransactionHistory(accountId);

		ResponseTransactionHistory responseTransactionHistory = mapTransactionHistoryToResponseTransactionHistory(
				transactionHistory);
		return ResponseEntity.ok(responseTransactionHistory);
	}

	private ResponseTransactionHistory mapTransactionHistoryToResponseTransactionHistory(
			TransactionHistory transactionHistory) {
		List<ResponseTransaction> responseTransactionHistory = transactionHistory.getTransactionList().stream()
				.map(this::mapTransactionToResponseTransaction).toList();

		return new ResponseTransactionHistory(responseTransactionHistory);
	}

	private ResponseTransaction mapTransactionToResponseTransaction(Transaction transaction) {
		return new ResponseTransaction(transaction.transactionId(), transaction.operation(), transaction.amount(),
				transaction.timestamp());
	}
}

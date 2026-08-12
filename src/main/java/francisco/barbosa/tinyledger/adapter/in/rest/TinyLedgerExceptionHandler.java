package francisco.barbosa.tinyledger.adapter.in.rest;

import francisco.barbosa.tinyledger.adapter.in.rest.dto.ApiError;
import francisco.barbosa.tinyledger.app.exception.AccountNotFoundException;
import francisco.barbosa.tinyledger.app.exception.OperationNotAllowedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TinyLedgerExceptionHandler {

	@ExceptionHandler(AccountNotFoundException.class)
	ResponseEntity<ApiError> handleAccountNotFound(AccountNotFoundException e) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(e.getMessage()));
	}

	@ExceptionHandler(OperationNotAllowedException.class)
	ResponseEntity<ApiError> handleInvalidAmount(OperationNotAllowedException e) {

		return ResponseEntity.badRequest().body(new ApiError(e.getMessage()));
	}
}

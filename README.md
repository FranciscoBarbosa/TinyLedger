# TinyLedger

A simple REST API for managing account balances and perform money transactions

The application supports:

- Deposits
- Withdraws
- Viewing the current balance
- Viewing transaction history

The application uses in-memory storage, as suggested by the exercise.

## Requirements

- Java 21
- Maven 3.9+

## Running the application

Clone the repository and run:

```bash
./mvnw spring-boot:run
```

The API will be available at:

http://localhost:8080

## Running tests

Run all tests with:
```
./mvnw test
```



## API endpoints

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/v1/ledgers/{accountId}` | Get the current account balance |
| `POST` | `/v1/ledgers/{accountId}/deposits` | Deposit money into an account |
| `POST` | `/v1/ledgers/{accountId}/withdraws` | Withdraw money from an account |
| `GET` | `/v1/ledgers/{accountId}/transactions` | Get the account transaction history |

### Examples

#### Get account balance

```bash
curl http://localhost:8080/v1/ledgers/fd1bb2a6-b1bb-4608-bdfb-cdda9a8a657f
```

#### Deposit

```bash
curl -X POST \
http://localhost:8080/v1/ledgers/fd1bb2a6-b1bb-4608-bdfb-cdda9a8a657f/deposits \
-H "Content-Type: application/json" \
-d '{"amount": 50.00}'
```

#### Withdraw

```bash
curl -X POST \
  http://localhost:8080/v1/ledgers/fd1bb2a6-b1bb-4608-bdfb-cdda9a8a657f/withdraws \
  -H "Content-Type: application/json" \
  -d '{"amount": 20.00}'
```

#### Transaction History

```bash
curl http://localhost:8080/v1/ledgers/fd1bb2a6-b1bb-4608-bdfb-cdda9a8a657f/transactions
```

## Test data initialization

The application starts with a small set of predefined accounts so that the API can be exercised immediately without requiring an account creation API.

The loadAndResetAccountsData() method is used by the integration tests to reset the in-memory repository to a known state before each test.

This is intentionally kept as a test-support mechanism and is not exposed through the REST API.
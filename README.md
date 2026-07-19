# Banking System Pro

A REST API backend for a banking application built with Java and Spring Boot. This is my main portfolio project that I've been working on to transition into backend development.

The system handles user authentication, bank account management and financial transactions between accounts.

---

## What it does

- Users can register and log in using JWT authentication
- Authenticated users can create and manage bank accounts (savings, checking, business)
- Transfer money between accounts, make deposits and withdrawals
- Every transaction is recorded with a full audit trail
- All endpoints are protected – you need a valid JWT token to access them

---

## Tech Stack

- **Java 17** + **Spring Boot 3.5**
- **Spring Security** with JWT authentication (jjwt 0.12.6)
- **Spring Data JPA** / **Hibernate**
- **PostgreSQL 16**
- **Docker** for local database setup
- **Maven** for build management
- **GitHub Actions** for CI/CD

---

## Project Structure

I went with a feature-based package structure instead of the classic layered approach. Each feature (user, account, transactions) contains its own controller, service, repository and DTOs. I find it easier to navigate and it scales better as the project grows.

```
com.jindero.banking
├── features
│   ├── account        # Account management + inheritance (Savings/Checking/Business)
│   ├── transactions   # Transaction processing + history
│   └── user           # User management + auth (register/login)
└── shared
    ├── config         # JPA Auditing
    ├── exception      # Custom exceptions
    ├── handler        # Global exception handler
    └── security       # JWT filter, Security config
```

---

## Getting Started

### Prerequisites

- Java 17+
- Docker Desktop

### 1. Clone the repo

```bash
git clone https://github.com/JinderO/Banking-system-pro.git
cd Banking-system-pro
```

### 2. Start PostgreSQL

```bash
docker-compose up -d
```

This starts a PostgreSQL 16 container with the credentials from `application.yml`.

### 3. Run the app

```bash
./mvnw spring-boot:run
```

Or just hit the run button in IntelliJ. The app starts on `http://localhost:8080` and Spring Boot will create all the tables automatically on first run.

---

## API Endpoints

### Auth (public – no token needed)

```
POST /api/auth/register   Register a new user
POST /api/auth/login      Login and get a JWT token
```

**Register:**
```json
{
  "firstName": "Jan",
  "lastName": "Novák",
  "email": "jan@example.com",
  "password": "password123",
  "phoneNumber": "123456789"
}
```

**Login:**
```json
{
  "email": "jan@example.com",
  "password": "password123"
}
```

Both return a JWT token:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

After that, include the token in every request:
```
Authorization: Bearer <token>
```

---

### Users (auth required)

```
GET    /api/users         Get all users
GET    /api/users/{id}    Get user by ID
POST   /api/users         Create user
PUT    /api/users/{id}    Update user
DELETE /api/users/{id}    Delete user
```

---

### Accounts (auth required)

```
GET    /api/accounts         Get all accounts
GET    /api/accounts/{id}    Get account by ID
POST   /api/accounts         Create account
```

**Create account:**
```json
{
  "userId": "uuid-here",
  "accountType": "SAVINGS",
  "accountNumber": "1234567890",
  "initialBalance": 1000.00
}
```

Account types: `SAVINGS`, `CHECKING`, `BUSINESS`

Each type has its own interest rate and monthly fee, implemented using JPA inheritance (SINGLE_TABLE strategy).

---

### Transactions (auth required)

```
POST /api/transactions/transfer     Transfer between accounts
POST /api/transactions/deposit      Deposit to account
POST /api/transactions/withdrawal   Withdraw from account

GET  /api/transactions/history             Get full transaction history for an account
GET  /api/transactions/history/range       Get history between two dates
GET  /api/transactions/history/from        Get history from a date to now
```

**Transfer request:**
```json
{
  "fromAccountId": "uuid-here",
  "toAccountId": "uuid-here",
  "amount": 500.00,
  "variableSymbol": "1234",
  "note": "Monthly rent"
}
```

---

## Error Handling

All errors return a consistent JSON structure via `@RestControllerAdvice`:

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Account with ID abc-123 not found",
  "timestamp": "2026-07-19T10:30:00"
}
```

---

## A few design decisions I made

**UUID over Long for IDs** – harder to enumerate and better for potential distributed setups in the future.

**BigDecimal for money** – floating point precision issues are a real problem with financial calculations. BigDecimal avoids that entirely.

**JWT stateless auth** – the server doesn't need to keep session state, which makes scaling easier.

**DTO pattern** – I keep the API layer separate from the JPA entities. The entities have `@JsonIgnore` on sensitive fields like passwords and internal Spring Security fields.

**Transactions with `@Transactional`** – all money operations are atomic. If anything fails mid-transfer, the whole thing rolls back.

---

## CI/CD

GitHub Actions runs on every push to main – it spins up a PostgreSQL service, builds the project and runs tests.

---

## What I'm working on next

- Unit tests with JUnit 5 and Mockito
- Ownership validation (users should only see their own accounts)
- Possibly adding roles (USER / ADMIN)

---

*Built as a portfolio project while transitioning from low-code to Java backend development.*
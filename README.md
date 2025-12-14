# Midas Core Project

Midas Core is a backend service built as part of the JPMorgan Chase Software Engineering Virtual Experience (Forage).  
It is responsible for receiving, validating, processing, and storing financial transactions at scale.

---

## Features

- Kafka-based transaction ingestion
- Transaction validation and processing
- In-memory H2 database with JPA
- Incentive calculation via external REST API
- REST endpoint to query user balances
- Embedded Kafka and automated test verification

---

## Tech Stack

- Java
- Spring Boot
- Apache Kafka
- Spring Data JPA
- H2 Database
- REST APIs
- Maven

---

## Project Architecture

- **Kafka** → receives transactions
- **Midas Core** → validates and processes data
- **H2 Database** → stores users and transactions
- **Incentive API** → calculates incentives
- **REST API** → exposes user balances

---

## How to Run the Project

### 1. Start Incentive API
```bash
java -jar services/transaction-incentive-api.jar

### 2. Run Midas Core
mvn spring-boot:run

### 3. Execute Tests
Run task tests from your IDE:
TaskOneTests
TaskTwoTests
TaskThreeTests
TaskFourTests
TaskFiveTests

## REST API Example
Get User Balance
GET http://localhost:33400/balance?userId=1


## Response:
{
  "amount": 3089.42
}

## Learning Outcomes

Real-world backend system design
Kafka integration
Database consistency and validation
Microservice communication using REST
Debugging distributed systems
Writing production-style Spring Boot code

Acknowledgements

This project was completed as part of the
JPMorgan Chase Software Engineering Virtual Experience Program on Forage.

Click **Commit new file** 
---
### 4  Add `.gitignore` (if missing)
Create `.gitignore` and paste:
```gitignore
/target
/.idea
*.iml
*.log

This keeps repo clean.




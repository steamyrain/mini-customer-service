# MINI CUSTOMER SERVIcE

A mini spring application for customer, product, transaction with RBAC 

## Installation

Running with gradle and docker compose

```bash
  ./gradlew bootJar
  docker compose build --build-arg JAR="mini-customer-service-0.0.1.jar"
  docker compose up -d 
```

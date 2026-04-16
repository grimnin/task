# Product Management System API

A robust backend system designed to manage a complex product catalog and a database of manufacturers. This project demonstrates a scalable approach to handling products with high attribute density (50-200 attributes per item) while maintaining clean architecture and high testability.

## 🚀 Key Features

* **Dynamic Attribute Architecture**: Implements a Key-Value structure using `@ElementCollection`. This allows products to store 50-200+ unique technical attributes without requiring schema migrations for every new product type.
* **Manufacturer CRUD**: A dedicated module for managing producers with built-in validation for name uniqueness and relational integrity.
* **Advanced Filtering**: Support for complex queries, including partial name matching (Case-Insensitive) and filtering by specific manufacturer IDs.
* **Database Versioning**: Powered by **Liquibase** to ensure consistent schema across all environments and automated seed data loading (Samsung, Apple, Sony).
* **Interactive Documentation**: Integrated **Swagger UI (OpenAPI 3.0)** for instant API testing and exploration.
* **Quality Assurance**: Comprehensive test suite including **Unit Tests** for business logic (Mockito) and **Integration Tests** for API-to-DB flows (MockMvc).

## 🛠 Tech Stack

* **Java 21** (OpenJDK)
* **Spring Boot 3.3.5**
* **Spring Data JPA / Hibernate**
* **H2 Database** (File-based, persistent storage)
* **Liquibase** (Database migrations)
* **Lombok** (Boilerplate reduction)
* **Maven** (Dependency management)

## 📖 Getting Started

### Prerequisites
* Java 21 or higher.
* Maven (The project includes the Maven Wrapper, so a local install is not required).

### Installation & Execution
1. Unzip the project archive.
2. In the root directory, run the following command:
   ```bash
   ./mvnw spring-boot:run
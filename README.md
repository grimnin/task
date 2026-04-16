# Product Management System API

A professional-grade backend system designed to manage a complex product catalog and a database of manufacturers. This project focuses on handling products with high attribute density (50-200 attributes) using a flexible, scalable architecture.

## 🛠 Tech Stack

* **Java 21** (OpenJDK - Temurin)
* **Spring Boot 3.3.5**
* **Spring Data JPA / Hibernate**
* **H2 Database** (File-based storage)
* **Liquibase** (Database migrations and seed data)
* **Lombok** (Boilerplate reduction)
* **Maven** (Dependency management)

### 🧪 Testing Stack
* **JUnit 5**: The primary framework for writing and running tests.
* **Mockito**: Used for mocking dependencies in unit tests to isolate business logic.
* **MockMvc**: Provides support for testing Spring MVC controllers by performing "fake" HTTP requests.
* **AssertJ**: Used for fluent and readable assertions in test cases.

### 💡 Tech Stack Reasoning
* **Spring Boot 3.3.5**: I chose one of the latest stable versions of Spring Boot 3 to minimize the risk of "dependency injection hell" and to leverage improved security, Jakarta EE 10 compatibility, and native support for Java 21 features.
* **Java 21 (LTS)**: Utilized for its modern syntax, improved performance, and long-term support, ensuring the project is built on the current industry standard.

---

## 📖 Access & Configuration

### API Documentation (Swagger UI)
The project is fully documented using OpenAPI 3.0. You can test all endpoints directly through the browser:
👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

### H2 Database Console
To inspect the database state, use the built-in H2 Console:
* **URL**: `http://localhost:8080/h2-console`
* **JDBC URL**: `jdbc:h2:file:./data/productdb`
* **User**: `sa`
* **Password**: (leave empty)

---

## 🗄 Database Structure & Logic

The system uses a relational approach optimized for flexibility:

1. **Producers Table**: Stores manufacturer details (id, name).
2. **Products Table**: Stores core product data (name, description, price) and a foreign key to the Producer.
3. **Product Attributes (The "EAV-like" Approach)**:
   To handle the requirement of **50-200 attributes** without creating a "flat table" with 200 columns, I implemented an `@ElementCollection` map.
   * Attributes are stored in a separate table `product_attributes` linked by `product_id`.
   * This allows each product to have a completely different set of attributes (e.g., "RAM" for phones vs. "Material" for furniture) without changing the database schema.

---

## 🚀 How to use Endpoints

You can use the **Swagger UI** or **Postman** to interact with the API.

### 1. Create a Product
**POST** `/api/products`
* **Payload**: Requires a `producerId` (ensure the producer exists first).
* **Attributes**: Send as a JSON map: `"attributes": {"Color": "Black", "RAM": "12GB"}`.

### 2. Search & Filtering
**GET** `/api/products`
The endpoint supports optional query parameters:
* `name`: Partial, case-insensitive search (e.g., `?name=galaxy`).
* `producerId`: Filters products by a specific manufacturer.
* *Note: If no parameters are provided, it returns all products.*

### 3. Manage Producers
**GET / POST / DELETE** `/api/producers`
Standard CRUD operations to manage the list of available manufacturers.

---

## 🧪 Testing & Reliability

* **Unit Tests**: Focus on service-layer logic, validation rules, and mapper accuracy using Mockito to isolate external dependencies.
* **Integration Tests**: Verify the full end-to-end flow from the REST controller down to the H2 database.
* **Data Isolation**: The `ProductIntegrationTest` uses a manual cleanup strategy in `@BeforeEach` to ensure that Liquibase seed data does not interfere with test results, ensuring a consistent green build in any environment (including Windows Sandbox).

---

## ⚙️ Installation & Running

This project uses **Maven Wrapper**, so you don't need Maven installed globally. **Java 21** is required.

1. Unzip the project archive.
2. Open a terminal (CMD/PowerShell) in the project root folder.
3. Run the application:
   - **Windows (PowerShell)**: `.\mvnw.cmd spring-boot:run`
   - **Windows (CMD)**: `mvnw.cmd spring-boot:run`
   - **Linux/Mac**: `./mvnw spring-boot:run`
4. Wait for the `Started TaskApplication` log.
5. Access the API via Swagger: `http://localhost:8080/swagger-ui/index.html`

*Note: On the first run, the app will automatically create a `./data` folder for the H2 database and run Liquibase migrations.*

---
### Author
**Kamil Dabrowski**
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
* **JUnit 5**: Core framework for writing and running tests.
* **Mockito**: Used for mocking dependencies to isolate business logic during unit testing.
* **MockMvc**: Provides support for testing Spring MVC controllers by performing simulated HTTP requests.
* **AssertJ**: Used for fluent and highly readable assertions.

### 💡 Tech Stack Reasoning
* **Spring Boot 3.3.5**: I chose one of the latest stable versions of Spring Boot 3 to minimize the risk of "dependency injection hell" and to leverage improved security, Jakarta EE 10 compatibility, and native support for Java 21 features.
* **Java 21 (LTS)**: Utilized for its modern syntax and efficiency, ensuring the project is built on the current industry standard for long-term support.

---

## 📖 Access & Configuration

### API Documentation (Swagger UI)
The project is fully documented using OpenAPI 3.0. You can test all endpoints directly through the browser:
👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

### H2 Database Console
To inspect the database state and run SQL queries:
👉 **[http://localhost:8080/h2-console](http://localhost:8080/h2-console)**
* **JDBC URL**: `jdbc:h2:file:./data/productdb`
* **User**: `sa`
* **Password**: (leave empty)

---

## 🗄 Database Structure & Logic

The system uses a relational approach optimized for high-flexibility data:

1. **Producers Table**: Stores manufacturer details (id, name).
2. **Products Table**: Stores core product data (name, description, price) and a foreign key to the Producer.
3. **Product Attributes (The "EAV-like" Approach)**:
   To handle the requirement of **50-200 attributes** without creating a "flat table" with 200 columns, I implemented an `@ElementCollection` map.
   * Attributes are stored in a separate table `product_attributes` linked by `product_id`.
   * This allows each product to have a completely different set of attributes (e.g., "RAM" for phones vs. "Material" for furniture) without changing the database schema.

---

## 🚀 API Endpoints & Usage (cURL Templates)

You can use the **Swagger UI** or import these **cURL** commands into **Postman** (*Import -> Raw text*).

### 1. Create a Product
**POST** `/api/products`
```bash
curl -X POST http://localhost:8080/api/products \
-H "Content-Type: application/json" \
-d '{
  "name": "Smartphone X1",
  "description": "Flagship model",
  "price": 2999.99,
  "producerId": 1,
  "attributes": {
    "Color": "Space Gray",
    "Storage": "256GB",
    "RAM": "12GB"
  }
}'
```

### 2. Update a Product (Full Update)
**PUT** `/api/products/{id}`
```bash
curl -X PUT http://localhost:8080/api/products/1 \
-H "Content-Type: application/json" \
-d '{
  "name": "Smartphone X1 Pro",
  "description": "Updated flagship model",
  "price": 3200.00,
  "producerId": 1,
  "attributes": {
    "Color": "Deep Black",
    "Storage": "512GB"
  }
}'
```

### 3. Search & Filtering
**GET** `/api/products`
Supports `name` (partial, case-insensitive) and `producerId`.
```bash
curl -G http://localhost:8080/api/products \
--data-urlencode "name=smart" \
--data-urlencode "producerId=1"
```

### 4. Delete a Product
**DELETE** `/api/products/{id}`
```bash
curl -X DELETE http://localhost:8080/api/products/1
```

---

## 🧪 Testing & Reliability

* **Unit Tests**: Focus on service-layer logic, validation rules, and mapper accuracy.
* **Integration Tests**: Verify the full end-to-end flow from the REST controller down to the H2 database.
* **Data Isolation**: The `ProductIntegrationTest` uses a manual cleanup strategy in `@BeforeEach` to ensure that Liquibase seed data does not interfere with test results, ensuring a consistent build in any environment (including Windows Sandbox).

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
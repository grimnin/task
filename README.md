# Product Management System API

A professional-grade backend system designed to manage a complex product catalog and a database of manufacturers. This project focuses on handling products with high attribute density (50-200 attributes) using a flexible, scalable architecture.

## 🛠 Tech Stack

* **Java 21** (OpenJDK)
* **Spring Boot 3.3.5**
* **Spring Data JPA / Hibernate**
* **H2 Database** (File-based storage)
* **Liquibase** (Database migrations and seed data)
* **Lombok** (Boilerplate reduction)
* **Maven** (Dependency management)

### 💡 Tech Stack Reasoning
* **Spring Boot 3.3.5**: I chose one of the latest stable versions of Spring Boot 3 to take full advantage of **Java 21** features (including Virtual Threads compatibility and better performance). It also ensures the highest level of security and support for modern libraries like Jakarta Persistence.

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

* **Unit Tests**: Test core service logic and validation using Mockito.
* **Integration Tests**: Verify the full flow from API to H2 Database.
* **Data Isolation**: The `ProductIntegrationTest` uses a manual cleanup strategy in `@BeforeEach` to ensure that Liquibase seed data does not interfere with test results, ensuring 100% pass rate in any environment (including Windows Sandbox).

---

## ⚙️ Installation

1. Unzip the project.
2. Run `./mvnw spring-boot:run` (or `mvnw.cmd` on Windows).
3. The application will automatically create a `./data` folder for the database.

---
### Author
[Kamil Dabrowski]
# Employee Management API

A RESTful Employee Management API built with **Java, Spring Boot, Spring Data JPA, Hibernate, PostgreSQL, and Spring Security with JWT authentication**.

The project is designed as a practical backend project for learning and practicing Java and Spring Boot concepts.

## 🚀 Features

* Employee CRUD operations
* Department CRUD operations
* Employee ↔ Department relationship
* PostgreSQL database
* Spring Data JPA
* Hibernate ORM
* DTO pattern
* Bean Validation
* Global exception handling
* Pagination and sorting
* Employee search
* Spring Security
* JWT authentication
* BCrypt password hashing
* Role-based authorization

  * USER
  * ADMIN
* REST API
* Unit testing with JUnit and Mockito
* Controller testing with MockMvc

## 🛠️ Technologies

| Technology      | Purpose                        |
| --------------- | ------------------------------ |
| Java            | Programming language           |
| Spring Boot     | Backend framework              |
| Spring Web      | REST API                       |
| Spring Data JPA | Database access                |
| Hibernate       | ORM                            |
| PostgreSQL      | Database                       |
| Spring Security | Authentication & authorization |
| JWT             | Token-based authentication     |
| BCrypt          | Password hashing               |
| JUnit           | Unit testing                   |
| Mockito         | Mocking                        |
| MockMvc         | Controller testing             |
| Maven           | Dependency management          |

## 📁 Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── employee_api/
│   │       ├── controller/
│   │       │   ├── AuthController.java
│   │       │   ├── EmployeeController.java
│   │       │   └── DepartmentController.java
│   │       │
│   │       ├── dto/
│   │       │   ├── ApiResponse.java
│   │       │   ├── LoginRequest.java
│   │       │   ├── RegisterRequest.java
│   │       │   ├── AuthResponse.java
│   │       │   ├── EmployeeRequest.java
│   │       │   ├── EmployeeResponse.java
│   │       │   ├── DepartmentRequest.java
│   │       │   └── DepartmentResponse.java
│   │       │
│   │       ├── entity/
│   │       │   ├── Employee.java
│   │       │   ├── Department.java
│   │       │   └── User.java
│   │       │
│   │       ├── repository/
│   │       │   ├── EmployeeRepository.java
│   │       │   ├── DepartmentRepository.java
│   │       │   └── UserRepository.java
│   │       │
│   │       ├── service/
│   │       │   ├── EmployeeService.java
│   │       │   ├── DepartmentService.java
│   │       │   ├── AuthService.java
│   │       │   └── CustomUserDetailsService.java
│   │       │
│   │       ├── security/
│   │       │   ├── SecurityConfig.java
│   │       │   ├── JwtService.java
│   │       │   └── JwtAuthenticationFilter.java
│   │       │
│   │       ├── exception/
│   │       │   ├── GlobalExceptionHandler.java
│   │       │   ├── EmployeeNotFoundException.java
│   │       │   ├── DepartmentNotFoundException.java
│   │       │   ├── InvalidCredentialsException.java
│   │       │   └── DuplicateUsernameException.java
│   │       │
│   │       └── EmployeeApiApplication.java
│   │
│   └── resources/
│       └── application.properties
│
└── test/
    └── java/
        └── employee_api/
            ├── service/
            ├── controller/
            └── security/
```

## 🗄️ Database Relationship

The project contains two main business entities:

```text
Department
    │
    │ 1
    │
    │
    │ *
    ▼
Employee
```

A department can have many employees.

### Department

```java
@OneToMany(mappedBy = "department")
private List<Employee> employees = new ArrayList<>();
```

### Employee

```java
@ManyToOne
@JoinColumn(name = "department_id")
private Department department;
```

The `employees` table contains the foreign key:

```text
department_id
```

## 🔐 Authentication

The API uses JWT-based authentication.

### Register

```http
POST /api/auth/register
```

```json
{
  "username": "ahmed",
  "password": "123456"
}
```

### Login

```http
POST /api/auth/login
```

```json
{
  "username": "ahmed",
  "password": "123456"
}
```

Response:

```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9..."
  }
}
```

Use the token in protected requests:

```http
Authorization: Bearer <JWT_TOKEN>
```

## 👤 Roles

The API supports two roles:

```text
USER
ADMIN
```

### USER

Can:

* Read employees
* Read departments

Cannot:

* Create employees
* Update employees
* Delete employees
* Create departments
* Update departments
* Delete departments

### ADMIN

Can perform all CRUD operations.

## 📌 API Endpoints

### Authentication

| Method | Endpoint             | Access |
| ------ | -------------------- | ------ |
| POST   | `/api/auth/register` | Public |
| POST   | `/api/auth/login`    | Public |

### Employees

| Method | Endpoint              | Access       |
| ------ | --------------------- | ------------ |
| GET    | `/api/employees`      | USER / ADMIN |
| GET    | `/api/employees/{id}` | USER / ADMIN |
| POST   | `/api/employees`      | ADMIN        |
| PUT    | `/api/employees/{id}` | ADMIN        |
| DELETE | `/api/employees/{id}` | ADMIN        |

### Departments

| Method | Endpoint                | Access       |
| ------ | ----------------------- | ------------ |
| GET    | `/api/departments`      | USER / ADMIN |
| GET    | `/api/departments/{id}` | USER / ADMIN |
| POST   | `/api/departments`      | ADMIN        |
| PUT    | `/api/departments/{id}` | ADMIN        |
| DELETE | `/api/departments/{id}` | ADMIN        |

## 📝 Example Department Request

### Create Department

```http
POST /api/departments
Authorization: Bearer <ADMIN_TOKEN>
Content-Type: application/json
```

```json
{
  "name": "IT"
}
```

### Update Department

```http
PUT /api/departments/1
Authorization: Bearer <ADMIN_TOKEN>
Content-Type: application/json
```

```json
{
  "name": "Information Technology"
}
```

### Delete Department

```http
DELETE /api/departments/1
Authorization: Bearer <ADMIN_TOKEN>
```

Response:

```text
204 No Content
```

## 👨‍💻 Example Employee Request

```json
{
  "firstName": "Ahmed",
  "lastName": "Ali",
  "email": "ahmed@example.com",
  "departmentId": 1,
  "salary": 15000
}
```

The `departmentId` is used to associate the employee with an existing department.

## ⚙️ Configuration

Configure PostgreSQL in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/employee_db
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=your-secret-key
jwt.expiration=3600000
```

> Do not commit real database passwords or JWT secrets to Git.

## ▶️ Running the Project

### 1. Clone the repository

```bash
git clone <your-repository-url>
cd employee-api
```

### 2. Configure PostgreSQL

Create a PostgreSQL database:

```sql
CREATE DATABASE employee_db;
```

Update the database credentials in `application.properties`.

### 3. Run the application

Using Maven:

```bash
mvn spring-boot:run
```

Or run:

```text
EmployeeApiApplication.java
```

from your IDE.

The API will normally be available at:

```text
http://localhost:8080
```

## 🧪 Running Tests

Run all tests:

```bash
mvn test
```

The project contains tests for:

* Service layer
* Controller layer
* Validation
* Security
* Authentication

## 📚 What I Practiced

This project was built to practice:

```text
Java
 ↓
OOP
 ↓
Spring
 ↓
Spring Boot
 ↓
REST APIs
 ↓
JPA / Hibernate
 ↓
PostgreSQL
 ↓
DTOs
 ↓
Validation
 ↓
Exception Handling
 ↓
Spring Security
 ↓
JWT
 ↓
Role-Based Authorization
 ↓
JUnit / Mockito
 ↓
MockMvc
```

## 🔮 Future Improvements

* Swagger / OpenAPI documentation
* Testcontainers
* Docker
* Docker Compose
* Refresh tokens
* Better JWT configuration using environment variables
* Advanced employee search/filtering
* Database migrations with Flyway
* CI/CD with GitHub Actions
* Production-ready logging
* API versioning

## 👨‍💻 Author

**Ahmed Aboragab**

Java & Spring Boot practice project focused on building a production-style REST API.

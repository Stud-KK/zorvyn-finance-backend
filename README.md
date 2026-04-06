
#  Finance Backend API

A Spring Boot backend system for managing financial records with secure authentication and role-based access control.

---

# 🚀 Features

- JWT-based authentication
- Role-based access control (RBAC)
- Financial record management (CRUD + filters)
- Dashboard analytics (income, expenses, trends)
- Input validation & global error handling
- Unit & integration testing
- Clean layered architecture

---

# 🛠 Tech Stack

- Java 17
- Spring Boot 3
- Spring Security (JWT)
- Spring Data JPA
- MySQL
- Lombok
- JUnit + Mockito

---

# 🔐 Roles & Permissions

| Role     | Permissions |
|----------|------------|
| ADMIN    | Full access (CRUD + users) |
| ANALYST  | View records + dashboard |
| VIEWER   | Read-only access |

---

# 🔑 Authentication

## 1. Register

**POST** `/auth/register`

```json
{
  "name": "Komal",
  "email": "komal@test.com",
  "password": "1234"
}
````

---

## 2. Login

**POST** `/auth/login`

```json
{
  "email": "komal@test.com",
  "password": "1234"
}
```

**Response:**

```
JWT Token
```

---

## 🔐 Authorization

Add token in header:

```
Authorization: Bearer <your_token>
```

---

# 📊 Record APIs

## 1. Create Record (ADMIN only)

**POST** `/records`

```json
{
  "amount": 500,
  "type": "INCOME",
  "category": "Salary",
  "note": "April salary"
}
```

---

## 2. Get All Records

**GET** `/records`

---

## 3. Filter by Type

**GET** `/records/filter/type?type=INCOME`

---

## 4. Filter by Category

**GET** `/records/filter/category?category=Food`

---

## 5. Filter by Date Range

**GET**

```
/records/filter/date?start=2024-01-01&end=2024-12-31
```

---

# 📈 Dashboard APIs

## 1. Total Income

**GET** `/dashboard/income`

---

## 2. Total Expense

**GET** `/dashboard/expense`

---

## 3. Category-wise Summary

**GET** `/dashboard/category`

---

## 4. Monthly Trends

**GET** `/dashboard/monthly`

---

## 5. Net Balance 

**GET** `/dashboard/balance`

---

# 👤 User Management (ADMIN)

## Get All Users

**GET** `/users`

---

## Update User Role

**PUT** `/users/{id}/role`

```json
{
  "role": "ROLE_ADMIN"
}
```

---

## Activate / Deactivate User

**PUT** `/users/{id}/status`

```json
{
  "status": "ACTIVE"
}
```

---

# ⚙️ Validation & Error Handling

* Uses `@Valid` for request validation
* Global exception handler for:

    * Invalid input
    * Resource not found
    * Unauthorized access

---

# 🧪 Testing

Run tests:

```bash
mvn test
```

Includes:

* Unit tests (service layer)
* Basic integration tests

---

# 🗄️ Database

* MySQL used for persistence
* JPA/Hibernate for ORM

Example table:

### users

* id
* name
* email
* password
* role
* status

### records

* id
* amount
* type
* category
* date
* note
* user_id

---

# 📂 Project Structure

```
src/main/java/com/zorvyn/finance

config      → security config
controller  → REST APIs
service     → business logic
repository  → database layer
entity      → DB models
dto         → request/response
security    → JWT filter & utils
exception   → global handler
```

---

# ▶️ Run Application

```bash
mvn clean install
mvn spring-boot:run
```

---

# 📌 Notes

* JWT secures all endpoints except `/auth/**`
* Role-based access enforced using `@PreAuthorize`
* Clean separation of concerns (Controller → Service → Repository)




# 🔗 REST Assured API Automation Framework

> API test automation framework for backend service validation  
> Built with **REST Assured + Java + TestNG** | OAuth 2.0 | JSON Schema Validation | CI/CD via **GitHub Actions**

---

## 🧱 Framework Architecture
---

## ⚙️ Tech Stack

| Layer | Tool |
|---|---|
| Language | Java |
| API Framework | REST Assured 5.x |
| Test Framework | TestNG |
| Schema Validation | JSON Schema Validator |
| Authentication | OAuth 2.0 / Bearer Token |
| Reporting | Allure Reports |
| Build Tool | Maven |
| CI/CD | GitHub Actions |

---

## 🚀 Getting Started

### Prerequisites
- Java JDK 11+
- Maven 3.8+

### Installation

```bash
# Clone the repository
git clone https://github.com/YOUR_USERNAME/rest-assured-api-automation.git

# Navigate to project
cd rest-assured-api-automation
```

### Run Tests

```bash
# Run all API tests
mvn test

# Run smoke tests only
mvn test -Dgroups=smoke

# Run specific test class
mvn test -Dtest=ProductAPITest

# Run regression suite
mvn test -Dgroups=regression

# Generate Allure report
mvn allure:serve
```

---

## 🔑 Key Framework Features

- ✅ **Request Builder** — reusable request setup with headers, auth, body
- ✅ **OAuth 2.0** — token-based authentication for secured endpoints
- ✅ **JSON Schema Validation** — validate response structure automatically
- ✅ **Response Validator** — reusable status code, body, header checks
- ✅ **Data-driven Testing** — JSON-based request payloads
- ✅ **Allure Reporting** — detailed API request/response in reports
- ✅ **Parallel Execution** — TestNG parallel test runs
- ✅ **GitHub Actions CI/CD** — automated pipeline on every push
- ✅ **Negative Testing** — invalid inputs, missing fields, wrong auth

---

## 🌐 API Test Coverage

| API Module | Endpoints Covered |
|---|---|
| Auth API | Login, logout, token refresh, invalid credentials |
| Product API | Get all, get by ID, create, update, delete |
| Order API | Create order, get order, update status, cancel |
| Schema Validation | Response structure validation for all endpoints |
| Negative Tests | 400, 401, 403, 404, 500 response handling |

---

## 👤 Author

**Parimal Jagtap** — SDET | 4+ Years  
[![LinkedIn](https://img.shields.io/badge/LinkedIn-parimaljagtap-0A66C2?style=flat-square&logo=linkedin)](https://www.linkedin.com/in/parimaljagtap)

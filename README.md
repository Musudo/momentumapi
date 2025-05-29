# 📘 MomentumAPI – README

MomentumAPI is the backend service for the Momentum productivity app. It provides RESTful endpoints for managing tasks, habits, sessions, and other productivity-related resources.

---

## 🚀 Tech Stack

* **Java v21**
* **Spring Boot**
* **Gradle**
* **PostgreSQL**
* **Docker**
* **CircleCI**

---

## 📂 Project Structure

* `controller/` – Handles incoming HTTP requests
* `service/` – Business logic
* `repository/` – Data access layer (Spring Data JPA)
* `model/` – Domain entities
* `dto/` – Data Transfer Objects for request/response payloads
* `config/` – Security and application configuration

---

## 🐳 Running with Docker

1. Clone this repo.
2. Build the project:

   ```bash
   ./gradlew build
   ```
3. Start the app with Docker Compose:

   ```bash
   docker-compose up --build
   ```

> Make sure Docker and Docker Compose are installed.

---

## 🧪 Testing

* Unit tests: JUnit & Mockito
* Integration tests for key services and REST controllers
* To run tests:

  ```bash
  ./gradlew test
  ```

---

## 🔐 Security

* JWT-based authentication
* BCrypt password hashing
* Role-based authorization

---

## 🔄 CI/CD with CircleCI

* Build and test pipeline on each commit
* Automated Docker image build and push

---

## 📮 API Overview

* Base URL: `/api/v1`
* Auth endpoints: `/auth/register`, `/auth/login`
* Dashboard: `/dasboard`
* Activities: `/activities`, `/activities/{id}`
* Contacts: `/contacts`, `/contacts/{id}`
* Swagger docs: `/swagger-ui/index.html`

> Some new stuff planned to for future release.

---

## 🛠️ To Do

* [ ] ...

---

## 👤 Author

* Musa Tashtamirov

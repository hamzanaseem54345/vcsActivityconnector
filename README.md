# VCS Activity Connector

A Spring Boot application that connects to GitHub using a Personal Access Token (PAT) to fetch repository activities such as commit history. Designed with extensibility in mind to support multiple VCS (Version Control System) providers in the future.

---

## 🚀 Features

- 🔐 Authenticate with GitHub using PAT (Personal Access Token)
- 📦 Fetch public repositories for a user or organization
- 📜 View recent 20 commits with message, author, and timestamp
- 🌱 Modular and scalable design to support future connectors (e.g., GitLab, Bitbucket)

---

## 🧱 Tech Stack

- Java 21
- Spring Boot 3.5.3
- Spring Web, Security, Feign, Data JPA
- Springdoc OpenAPI (Swagger UI)
- Liquibase for DB migration
- Maven

---

## 🔧 Setup & Run

### 1. Clone the Repository

```bash
git clone https://github.com/hamzanaseem54345/vcsActivityconnector.git
cd vcsActivityconnector

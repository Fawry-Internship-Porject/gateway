
# Spring Boot Gateway with LDAP Authentication and Role-Based Authorization

This project is a **Spring Cloud Gateway** application built with **Spring Boot**.  
It uses **Spring Security** integrated with **LDAP** for authentication and role-based authorization to secure routes.

## Features

- API Gateway using **Spring Cloud Gateway**
- LDAP authentication using **Spring Security**
- Role-based access control (RBAC)
- Dynamic route forwarding to backend services
- Basic login and security handling


## Tech Stack

- Java 23
- Spring Boot
- Spring Cloud Gateway
- Spring Security LDAP
- Maven

## Getting Started

### Prerequisites

- Java 23 or later
- Maven
- An accessible LDAP server

## Running the Project

1. **Clone the repository**

   ```bash
   git clone https://github.com/your-username/your-repo.git
   cd your-repo
   ```

2. **Configure LDAP and Role Mappings**

   Update your `application.properties` with the correct LDAP settings:

   ```properties
   spring.ldap.urls=ldap://localhost:10389
   spring.ldap.base=dc=example,dc=com
   spring.ldap.username=uid=admin,ou=system
   spring.ldap.password=secret
   ```

3. **Run the application**

   ```bash
   mvn spring-boot:run
   ```

4. **Access the Gateway**

   The Gateway will start at: [http://localhost:8080](http://localhost:8080)

## Example Route Configuration

```properties
spring.cloud.gateway.routes[0].id=admin_route
spring.cloud.gateway.routes[0].uri=http://localhost:8081
spring.cloud.gateway.routes[0].predicates[0]=Path=/admin/**

spring.cloud.gateway.routes[1].id=user_route
spring.cloud.gateway.routes[1].uri=http://localhost:8082
spring.cloud.gateway.routes[1].predicates[0]=Path=/user/**
```

## Project Structure

```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.fawry_internship.gateway
│   │   │       ├── GatewayApplication.java
│   │   │       └── config
│   │   │           ├── LdapConfig.java
│   │   │           └── SecurityConfig.java
│   ├── resources
│   │   └── application.properties
├── pom.xml
└── README.md
```

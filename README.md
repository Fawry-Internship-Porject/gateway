Spring Boot Gateway with LDAP Authentication and Role-Based Authorization
This project is a Spring Cloud Gateway application built with Spring Boot. It uses Spring Security integrated with LDAP for authentication and role-based authorization to secure routes.

Features
API Gateway using Spring Cloud Gateway

LDAP authentication using Spring Security

Role-based access control (RBAC)

Dynamic route forwarding to backend services

Basic login and security handling

Tech Stack
Java 23

Spring Boot

Spring Cloud Gateway

Spring Security LDAP

Maven

Getting Started
Prerequisites
Java 23 or later

Maven

An accessible LDAP server

Running the Project
Clone the repository

bash
Copy
Edit
git clone https://github.com/your-username/your-repo.git
cd your-repo
Configure LDAP and Role Mappings

Update application.properties with your LDAP settings:

properties
Copy
Edit
spring.ldap.urls=ldap://localhost:10389
spring.ldap.base=dc=example,dc=com
spring.ldap.username=uid=admin,ou=system
spring.ldap.password=secret
Run the application

bash
Copy
Edit
mvn spring-boot:run
Access the Gateway

The Gateway will start on http://localhost:8080.

Example Route Configuration
properties
Copy
Edit
spring.cloud.gateway.routes[0].id=admin_route
spring.cloud.gateway.routes[0].uri=http://localhost:8081
spring.cloud.gateway.routes[0].predicates[0]=Path=/admin/**

spring.cloud.gateway.routes[1].id=user_route
spring.cloud.gateway.routes[1].uri=http://localhost:8082
spring.cloud.gateway.routes[1].predicates[0]=Path=/user/**

Project Structure
css
Copy
Edit
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

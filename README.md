# RentEase

RentEase is a comprehensive Rental Management System designed to streamline the management of rental properties for landlords and tenants. It offers a simple and user-friendly interface with essential features for managing rental properties, leases, tenants, and payments.

## Features

- **User Authentication and Authorization**:

- **Property Listings**:

- **Tenant Management**:

- **Lease Management**:

- **Rent Payment Tracking**:

- **Basic Reporting**:

- **Online Rent Payment Integration (Optional)**:

## Tech Stack

| **Technology**      | **Description**                            |
|---------------------|--------------------------------------------|
|  **Java**           | Programming language                       |
| **Spring Boot**     | Backend framework                          |
| **PostgreSQL**      | Relational database                        |
| **Spring Security** | Security framework                         |
| **JWT**             | JSON Web Tokens for authentication         |
| **OOP**             | Object-Oriented Programming principles     |

## Spring Boot Backend Architecture

![Spring Boot Backend Architecture](https://res.cloudinary.com/amritrajmaurya/image/upload/v1722873464/Screenshot_2024-08-05_153752_uepmrm.png)

## ER Diagram

![ER Diagram](https://res.cloudinary.com/amritrajmaurya/image/upload/v1722873465/Screenshot_2024-08-05_153739_zscie3.png)

### To Encrypt/Decrypt Username and Password of application.properties file use below commands

```dtd
To Encrypt:
        mvn -X jasypt:encrypt-value "-Djasypt.encryptor.password=your_secret" "-Djasypt.plugin.value=your_value" 
To Decrypt:
        mvn jasypt:decrypt-value "-Djasypt.encryptor.password=your_secret" "-Djasypt.plugin.value=ENC(Xm9n9QtBj3oAhySxPINgLqBOBN3dszzRJGifVbo4y7DUkyKgdPwmNmUlRCq3V8Sj)"
```

RentEase aims to simplify rental property management, ensuring an efficient and hassle-free experience for both landlords and tenants.
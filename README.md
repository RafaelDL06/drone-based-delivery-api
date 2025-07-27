#Drone-based Delivery API

A Spring Boot RESTful API for managing drones and medication delivery operations.

---

## Features

- Register new drones
- Load medications onto drones
- Trigger delivery of loaded drones
- View loaded medications
- Check drone battery level
- Get available drones for delivery
- Automatic state transitions via scheduled task

---

## Tech Stack

- Java 17+
- Spring Boot 3.x
- Spring Data JPA (Hibernate)
- H2 in-memory database (for development/testing)
- JUnit 5 + Mockito + MockMvc for testing
- Lombok (to reduce boilerplate)

---

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+

---

### Clone Repo
git clone https://github.com/RafaelDL06/drone-based-delivery-api.git
cd drone-based-delivery-api

---

### Build
mvn clean install

---

### Run 
mvn spring-boot:run
or
java -jar target/drone-based-delivery-api.jar

---

### Test
mvn test

---

###Author
Created by rdelemos

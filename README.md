# Coworking-Service

Spring Boot application for managing a coworking space

## Functional features

- User registration and authorization
- View available workplaces and conference rooms
- Booking a workplace or conference room for a specific time and date
- Cancellation of the reservation
- Manage bookings and resources
- View all bookings and filter them

## Technologies

- Spring Boot 3.2.0 
- PostgreSQL 
- SpringDoc for API documentation 
- MapStruct for mapping DTOs to entities 
- Liquibase for database migrations 
- Lombok for boilerplate code reduction 
- AOP for logging and auditing

## How to Build and Run

1. Clone the repository;
2. Navigate to the project directory: `cd <project-directory>`;
3. Start the database using Docker: `docker-compose up -d`;
4. Build the project using Maven: `mvn clean install`;
5. Run the application: `mvn spring-boot:run`;

## API Documentation
API documentation is available via Swagger at:
http://localhost:8080/swagger-ui/index.html

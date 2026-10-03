# RideLink – Ride Management Service

IT3130 Application Development Group Assignment.

## Technology
- Java 17
- Spring Boot
- Spring Data JPA
- H2 (file-based persistence for development)
- REST/JSON
- OpenAPI/Swagger UI
- JUnit 5 + Mockito

## Responsibility
This service owns ride requests and the ride lifecycle:
REQUESTED -> ASSIGNED -> ACCEPTED -> IN_PROGRESS -> COMPLETED

Cancellation is allowed from REQUESTED, ASSIGNED and ACCEPTED.

The service stores stable passenger/driver identifiers. It does not directly access another microservice's database.

## Run
```bash
mvn clean test
mvn spring-boot:run
```

Swagger:
http://localhost:8083/swagger-ui.html

OpenAPI:
http://localhost:8083/v3/api-docs

H2 console:
http://localhost:8083/h2-console

JDBC URL:
jdbc:h2:file:./data/ridelink-rides

## Main endpoints
POST   /api/rides
GET    /api/rides/{id}
GET    /api/rides/passenger/{passengerId}
GET    /api/rides/driver/{driverId}
PUT    /api/rides/{id}/assign
PUT    /api/rides/{id}/accept
PUT    /api/rides/{id}/start
PUT    /api/rides/{id}/complete
PUT    /api/rides/{id}/cancel
PUT    /api/rides/{id}/status/{status}

## Example create request
```json
{
  "passengerId": 101,
  "pickupLocation": "SLIIT Malabe",
  "destination": "Kaduwela"
}
```

## Important integration note
The assignment requires meaningful inter-service communication. This starter service keeps the integration boundary ready, but the actual Driver & Vehicle Service contract must be agreed with your group. Configure its URL in:
`driver.service.base-url`

Before submission, your group should implement and document the actual inter-service call(s), authentication/authorization integration, and any agreed communication method.

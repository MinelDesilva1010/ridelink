# ridelink-driver-service

Owns: **driver and vehicle data, availability rules**. Runs on **port 8081** with its own H2 in-memory database (`driverdb`).

## Run it

```bash
mvn spring-boot:run
```

The service starts on `http://localhost:8081`. The H2 console (for peeking at the data if you want) is at `http://localhost:8081/h2-console` — JDBC URL `jdbc:h2:mem:driverdb`, user `sa`, no password.

## Endpoints (the contract)

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/drivers` | Create a driver |
| GET | `/api/drivers/available` | List available drivers |
| PATCH | `/api/drivers/{id}/availability` | Set `available` true/false |

### Create a driver
```bash
curl -X POST http://localhost:8081/api/drivers \
  -H "Content-Type: application/json" \
  -d '{"name":"Kasun Perera","vehicleNumber":"WP CAB-1234","vehicleType":"CAR"}'
```

### List available drivers
```bash
curl http://localhost:8081/api/drivers/available
```

### Set availability
```bash
curl -X PATCH http://localhost:8081/api/drivers/1/availability \
  -H "Content-Type: application/json" \
  -d '{"available": false}'
```

Create at least 2–3 drivers before testing Ride Service, so `GET /api/drivers/available` returns something for Ride Service to pick.

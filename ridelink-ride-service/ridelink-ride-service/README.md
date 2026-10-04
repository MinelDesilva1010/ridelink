# ridelink-ride-service

Owns: **ride request, assignment reference (`driverId`), and ride status**. Runs on **port 8082** with its own H2 in-memory database (`ridedb`). It never reads or writes Driver Service's tables — it only ever calls Driver Service's HTTP API, via `DriverClient`.

## Run it

**Start Driver Service first** (port 8081), then:

```bash
mvn spring-boot:run
```

The service starts on `http://localhost:8082`.

## How the pieces fit together

- `driver.service.base-url=http://localhost:8081` in `application.properties` — the only place Ride Service knows where Driver Service lives.
- `DriverClient` — GETs `/api/drivers/available` from Driver Service, picks the first driver, PATCHes that driver's availability to `false`, and returns a plain `DriverResponse` DTO. It's the only class that touches HTTP.
- `RideService` — the ride business rule lives here: ask `DriverClient` for a driver, save the ride with that driver's id, return the confirmed ride. If `DriverClient` throws, no `Ride` row is ever saved.
- `GlobalExceptionHandler` — turns a failed call to Driver Service into a clean `503` instead of leaking a stack trace.

## Endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/rides` | Request a ride (reserves a driver) |
| GET | `/api/rides/{id}` | Look up a ride |

### Create a ride (Driver Service must be running, with an available driver)
```bash
curl -X POST http://localhost:8082/api/rides \
  -H "Content-Type: application/json" \
  -d '{"passengerName":"Ayesha","pickup":"SLIIT","destination":"Malabe Junction"}'
```

Expected: `201 Created` with a JSON body like:
```json
{"id":1,"passengerName":"Ayesha","pickup":"SLIIT","destination":"Malabe Junction","driverId":1,"status":"CONFIRMED"}
```

### Look up a ride
```bash
curl http://localhost:8082/api/rides/1
```

## Testing the failure scenario (Part 4)

1. Stop Driver Service (Ctrl+C in its terminal). Leave Ride Service running.
2. Send another ride request:
   ```bash
   curl -i -X POST http://localhost:8082/api/rides \
     -H "Content-Type: application/json" \
     -d '{"passengerName":"Nimal","pickup":"Malabe","destination":"Kottawa"}'
   ```
3. Expected response — `HTTP/1.1 503 Service Unavailable`:
   ```json
   {"code":"DRIVER_SERVICE_UNAVAILABLE","message":"A driver cannot be assigned now. Please retry."}
   ```
   No stack trace, and no `Ride` row is created (check with `GET /api/rides/{id}` for the next id — it should 404).
4. Restart Driver Service and repeat the successful request from above to confirm it recovers.

Also try emptying Driver Service's available drivers (PATCH them all to `available:false`) while Driver Service is still up — you should get a `409 Conflict` with code `NO_DRIVER_AVAILABLE`, which is a different situation from the service being down entirely.

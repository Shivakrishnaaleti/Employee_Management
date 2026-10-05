# Employee Management

A simple REST CRUD application for employees and departments, built with Java 21,
Spring Boot, Spring Data JPA, MySQL, and Redis caching.

## Requirements

- Java 21
- MySQL 8 or a compatible server
- Redis 7 or a compatible server

## Configure and run

Create the database in MySQL and start Redis:

```sql
CREATE DATABASE Employee_Management;
```

```powershell
docker run --name employee-management-redis -p 6379:6379 -d redis:7-alpine
```

The application uses `src/main/resources/application.properties`. For local settings, copy `.env.example` to `.env` and set your MySQL
credentials:

```powershell
Copy-Item .env.example .env
# Edit .env and replace change-me with your MySQL password.
.\mvnw.cmd spring-boot:run
```

Spring Boot loads `.env` when running from the project directory. The `.env`
file is ignored by Git; do not commit credentials. Alternatively, configure
`SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, and
`SPRING_DATASOURCE_PASSWORD` in your operating system or IDE environment.
Without a `.env` file or environment overrides, the default connection uses
`jdbc:mysql://localhost:3306/Employee_Management`, username `root`, and an
empty password. Redis defaults to `localhost:6379`; override it with
`REDIS_HOST` and `REDIS_PORT`. Cached values expire after 10 minutes by default;
`REDIS_CACHE_TTL` can override the duration (for example, `30s` or `1h`).

Employee and department detail and paginated-list reads use Redis caches.
Successful creates, updates, and deletes evict the affected caches; department
updates also evict employee caches because employee responses contain department
details.

The API runs at `http://localhost:8084`. On macOS or Linux, run `./mvnw
spring-boot:run`.

Run the tests with:

```powershell
.\mvnw.cmd verify
```

Tests use an in-memory H2 database and do not require MySQL or Redis.

## CRUD endpoints

| Method | Endpoint | Action |
| --- | --- | --- |
| `POST` | `/employees` | Create an employee |
| `GET` | `/employees` | List employees |
| `GET` | `/employees/{employeeId}` | Get an employee |
| `PUT` | `/employees/{employeeId}` | Update an employee |
| `DELETE` | `/employees/{employeeId}` | Delete an employee |
| `POST` | `/departments` | Create a department |
| `GET` | `/departments` | List departments |
| `GET` | `/departments/{deptId}` | Get a department |
| `PUT` | `/departments/{deptId}` | Update a department |
| `DELETE` | `/departments/{deptId}` | Delete a department |

List endpoints support `page`, `size`, `sortBy`, and `sortDir` query
parameters. For interactive API documentation, start the application and open
`http://localhost:8084/swagger-ui/index.html`.

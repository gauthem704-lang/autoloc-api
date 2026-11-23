# AutoLoc API

Spring Boot project for the AutoLoc JPA and Spring Security workshops. It contains the nine domain entities, the JPA relationships described in the workshop handout, and a small HTTP Basic security exercise.

## Requirements

- JDK 17 or newer
- MySQL running locally or at a reachable host
- IntelliJ IDEA or Maven

## Database setup

The default URL creates the local `autoloc_db` schema if it does not exist. Set the database values in the IntelliJ run configuration or in the shell before starting the app. For PowerShell:

```powershell
$env:DB_URL = 'jdbc:mysql://localhost:3306/autoloc_db?createDatabaseIfNotExist=true'
$env:DB_USERNAME = 'root'
$env:DB_PASSWORD = 'your-local-mysql-password'
```

Then run the application from IntelliJ or use:

```powershell
./mvnw.cmd spring-boot:run
```

Hibernate uses `ddl-auto=update` for this workshop and logs generated SQL. Do not use that schema-update setting as a production migration strategy.

## Security exercise

The sample accounts are for local classroom use only:

| Username | Password | Role |
| --- | --- | --- |
| `student` | `student123` | `USER` |
| `admin` | `admin123` | `ADMIN` |

- `GET /api/public/ping` is public.
- `GET /api/hello` requires USER or ADMIN.
- `GET /api/admin/report` requires ADMIN.

Import `postman/AutoLoc-Security.postman_collection.json` to make the manual checks. See [docs/fetch-strategy.md](docs/fetch-strategy.md) for association and cascade decisions and [docs/security-notes.md](docs/security-notes.md) for the expected responses and security explanations.

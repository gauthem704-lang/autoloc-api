# Spring Security exercise

The API uses HTTP Basic authentication with two in-memory classroom accounts. Passwords are encoded with BCrypt when the application starts. The sample credentials are intentionally limited to this local exercise and must never be reused for real accounts.

| Username | Password | Role |
| --- | --- | --- |
| `student` | `student123` | `USER` |
| `admin` | `admin123` | `ADMIN` |

## Routes and expected responses

| Request | Anonymous | `student` | `admin` |
| --- | ---: | ---: | ---: |
| `GET /api/public/ping` | 200 | 200 | 200 |
| `GET /api/hello` | 401 | 200 | 200 |
| `GET /api/admin/report` | 401 | 403 | 200 |

The public path rule comes first, then the more specific admin rule, and then the general `/api/**` rule. Spring Security applies the first matching rule. A 401 response means the request is unauthenticated; a 403 means the credentials were accepted but the user's role is insufficient.

## Manual checks

Import `postman/AutoLoc-Security.postman_collection.json` into Postman and send each request. The collection includes anonymous, USER, and ADMIN requests. The same checks can be run with curl:

```text
curl -i http://localhost:8080/api/public/ping
curl -i http://localhost:8080/api/hello
curl -i -u student:student123 http://localhost:8080/api/hello
curl -i -u student:student123 http://localhost:8080/api/admin/report
curl -i -u admin:admin123 http://localhost:8080/api/admin/report
```

Capture the 200, 401, and 403 responses in Postman if screenshots are required for submission.

## Security questions

- `/api/admin/**` must precede `/api/**`; otherwise the broader rule would match first and prevent the admin-only rule from being reached.
- 401 means authentication is missing or invalid. 403 means the authenticated user lacks the required role.
- BCrypt stores a one-way password hash with a salt and work factor; a plain-text password is directly readable if the configuration is exposed.
- In-memory accounts are appropriate only for a small local exercise. A real service should load user records and roles from a database or identity provider through a `UserDetailsService`, store only password hashes, and support credential rotation and account lifecycle rules.
- HTTP Basic encodes credentials for the request but does not encrypt them. Use HTTPS outside localhost. If browser clients later send state-changing requests, keep an appropriate CSRF strategy.

## Database configuration

Set `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` in the IntelliJ run configuration or process environment. The checked-in `application.properties` contains no database password. Do not add `.env` files, real credentials, or Postman screenshots containing secrets to Git.

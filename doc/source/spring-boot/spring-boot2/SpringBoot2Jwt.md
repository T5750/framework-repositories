# Spring Boot 2 JWT

## CookieUtil
- `cookie.setSecure(secure)`: secure=true => work on HTTPS only.
- `cookie.setHttpOnly(true)`: invisible to JavaScript.
- `cookie.setMaxAge(maxAge)`: maxAge=0: expire cookie now, maxAge<0: expire cookiie on browser exit.
- `cookie.setDomain(domain)`: visible to `domain` only.
- `cookie.setPath("/")`: visible to all paths.

## References
- [Single Sign On (SSO) Example with JSON Web Token (JWT) and Spring Boot](https://hellokoding.com/spring-boot/security-sso-jwt/#source-code)
- [Single Log Out (SLO) Example with JSON Web Token (JWT), Spring Boot and Redis](https://hellokoding.com/spring-boot/security-slo-jwt/)

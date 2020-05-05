# Keycloak OAuth2 PKCE

## Why Keycloak as authentication server
Keycloak comes with several handy features built-in:
- Two-factor authentication
- Bruteforce detection
- Social login (Facebook, Twitter, Google…)
- LDAP/AD integration

## Setting up a Keycloak server
```
$ curl https://downloads.jboss.org/keycloak/9.0.3/keycloak-9.0.3.zip --output keycloak-9.0.3.zip
$ unzip keycloak-9.0.3.zip
$ cd keycloak-9.0.3/bin/
$ ./standalone
```

## Creating a new realm
Name: `Security`

## Creating a client
- Client ID: `spa-heroes`
- Valid Redirect URIs: `http://localhost:4200/*`

Standard flow is another name for the [Authorization Code Flow](https://tools.ietf.org/html/rfc6749#section-1.3.1) as defined in the [OAuth 2.0 specification](https://tools.ietf.org/html/rfc6749).

**Direct Access Grants Enabled** may remain enabled for now. It will be easy to test our configuration later.

## Creating roles and scopes
- Roles: `user`
- Client Scopes: `heroes`
- Clients -> Client Scopes -> Add heroes

## Creating a user
Users -> Add User

## Setting up the front end and back end applications

## Angular app: tour of heroes
```
$ curl -LO https://angular.io/generated/zips/toh-pt6/toh-pt6.zip
$ unzip toh-pt6.zip -d toh
$ cd toh
$ npm i
```
`hero.service.ts`
```
export class HeroService {
  private heroesUrl = 'http://localhost:18095/api/heroes';
}
```
```
ng serve
```
http://localhost:4200/

## Implementing security

## Implicit flow versus code flow + PKCE
In this example, we will use the authorization code grant flow with Proof Key for Code Exchange ([PKCE](https://tools.ietf.org/html/rfc7636)) to secure the Angular app. It’s a very long name for what could be shortened to “code flow + PKCE” which is [more secure than the implicit flow](https://tools.ietf.org/html/draft-ietf-oauth-security-topics-13#section-3.1.2).

In fact, the implicit flow was never very secure to begin with.

The implicit flow was the easiest to understand, since it required one step less than the standard code flow:

![keycloak_implicit_vs_code](https://s1.wailian.download/2020/04/30/keycloak_implicit_vs_code-min.png)

PKCE is an addition on top of the standard code flow to make it usable for public clients. It is already in use for native and mobile clients. PKCE boils down to this:
1. Give `hash` of random value to authorization server when logging in to ask for `code`
2. Hand over the `random value` to authorization server when exchanging `code` for `access token`
3. Authorization server returns `access token` after verifying that `hash` belongs to `random value`.

![keycloak_pkce](https://s1.wailian.download/2020/04/30/keycloak_pkce-min.png)

If a fraudster were to intercept our authorization grant (the code), he or she would still not have the `code_verifier`, which is stored in our SPA client.

## Json web token (JWT)
In its compact form, JSON Web Tokens consist of three parts separated by dots (.), which are:
- `Header`: the type of the token and the signing algorithm being used
- `Payload`: the payload, which contains the claims and additional data
- `Signature`: to verify if the token was not tampered with

Therefore, a JWT typically looks like the following: `xxxxx.yyyyy.zzzzz`

The header (xxx) and payload (yyy) are base64 encoded. An access token is a good example of a JWT:
```
eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJkdE9JZkY2NGZRYnlwWVRFdGV2eV83NUdIWTdQMmNHU1o2a2ZXWDdFblBJIn0.eyJqdGkiOiIxY2EzZTZkYS1kM2Y2LTRiYTMtYjNjZC1iMDExYmRlM2JmNmIiLCJleHAiOjE1NjYzMjk1NTYsIm5iZiI6MCwiaWF0IjoxNTY2MzI5MjU2LCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvaGVyb2VzIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjBkNjg0OWI4LWUyZmEtNGU3My04NjlhLTE1ZDVhOTE1YzdhMiIsInR5cCI6IkJlYXJlciIsImF6cCI6InNwYS1oZXJvZXMiLCJhdXRoX3RpbWUiOjAsInNlc3Npb25fc3RhdGUiOiI4NWRjYTg0Ny00YmQzLTRiOTUtOTNiYy01MjE5ZjUzYWNiMzciLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbIioiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJ1c2VyIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBoZXJvZXMgcHJvZmlsZSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYW1lIjoiSmVyb2VuIE1leXMiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJqZXJvZW4iLCJnaXZlbl9uYW1lIjoiSmVyb2VuIiwiZmFtaWx5X25hbWUiOiJNZXlzIiwiZW1haWwiOiJtZUBhY21lLmJlIn0.cvn79d-n0aFYqDB3p-htDNeeYOdkvqEsmDhGKp9V3a4i6nJx7dU0_r7zicQe26ZgDsM65ILx_X-buWv-e5_eraFo1OOveCGtBbrrLwrQ0Z7SlVMHJrDooJrLEE_m8Qlz_-iLcEC2-ODroEwyLRej_Du626B48QL2bcq-8ADqGSaLf7Y4ZTVMiP_p6dsCi4GDQLq1WOy-g6--z47FKTJVuAl2yY_JNNuEd5aofw0FTE38EoEinIdcy5NXCXDhtGHr_k5lA2Swu4JvK84YB6usECigCb1_zO_c6LhZQkRTCcCojxC6Qn1trQH9epcFEKTkDCHrNf6BLp4X9rH2URWJcA
```
We can easily decode them using online tools like [jwt.io](https://jwt.io/).

>Be careful with online tools to analyze JWT tokens. You are exposing access tokens to the world!

## Resource server in spring boot
```
implementation 'org.springframework.boot:spring-boot-starter-security'
implementation 'org.springframework.security:spring-security-oauth2-resource-server'
implementation 'org.springframework.security:spring-security-oauth2-jose'
```
- `spring-boot-starter-security`: starter dependency for Spring Security
- `spring-security-oauth2-resource-server`: dependency to use our application as a Resource Server
- `spring-security-oauth2-jose`: support for the Javascript Object Signing and Encryption framework

## Configuration of the resource server
```java
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .authorizeRequests()
                .mvcMatchers("/api/heroes/**").hasAuthority("SCOPE_heroes")
                .anyRequest().denyAll()
                .and()
                .oauth2ResourceServer()
                .jwt();
    }
}
```
```
spring.security.oauth2.resourceserver.jwt.jwk-set-uri=http://localhost:8080/auth/realms/Security/protocol/openid-connect/certs
```

## Testing the setup
```
$ curl -i http://localhost:18095/api/heroes
```
Without a token, the server responds with HTTP 401. This means we are not authorized. As we don’t have a login form available just yet, we can use the Direct Access Grants flow to obtain a token. This can come in very handy for testing different scenarios as well.
```
$export TOKEN=$(curl -H "Content-Type: application/x-www-form-urlencoded" \
  -d "client_id=spa-heroes" \
  -d "username=tester" \
  -d "password=123456" \
  -d "grant_type=password" \
  -X POST http://localhost:8080/auth/realms/Security/protocol/openid-connect/token | jq -r .access_token)
$echo $TOKEN
$ curl -i -X GET -H "Authorization: Bearer $TOKEN" http://localhost:18095/api/heroes
```

>Make sure ‘Direct Access Grants Enabled’ is enabled in the Keycloak Client settings

## Securing the Angular application
```
$ npm i angular-oauth2-oidc --save
```
`app.module.ts`
```
import { HttpClientModule } from '@angular/common/http';
import { OAuthModule } from 'angular-oauth2-oidc';

@NgModule({
  imports: [
    HttpClientModule,
    OAuthModule.forRoot({
      resourceServer: {
          allowedUrls: ['http://localhost:18095/api'],
          sendAccessToken: true
      }
    })
```
`app.component.html`
```
<button class="btn btn-default" (click)="login()">
  Login
</button>
<button class="btn btn-default" (click)="logoff()">
  Logout
</button>
```
`app.component.ts`
```
import { Component } from '@angular/core';
import { OAuthService, NullValidationHandler, AuthConfig } from 'angular-oauth2-oidc';
import { JwksValidationHandler } from 'angular-oauth2-oidc';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Tour of Heroes';
  
  constructor(private oauthService: OAuthService) {
    this.configure();
  }

  authConfig: AuthConfig = {
    issuer: 'http://localhost:8080/auth/realms/Security',
    redirectUri: window.location.origin + "/Security",
    clientId: 'spa-heroes',
    scope: 'openid profile email offline_access heroes',
    responseType: 'code',
    // at_hash is not present in JWT token
    disableAtHashCheck: true,
    showDebugInformation: true
  }
  
  public login() {
    this.oauthService.initLoginFlow();
  }
  
  public logoff() {
    this.oauthService.logOut();
  }
  
  private configure() {
    this.oauthService.configure(this.authConfig);
    this.oauthService.tokenValidationHandler = new NullValidationHandler();
    this.oauthService.loadDiscoveryDocumentAndTryLogin();
  }
}
```

## References
- [SECURING WEB APPLICATIONS WITH KEYCLOAK USING OAUTH 2.0 AUTHORIZATION CODE FLOW AND PKCE](https://ordina-jworks.github.io/security/2019/08/22/Securing-Web-Applications-With-Keycloak.html)
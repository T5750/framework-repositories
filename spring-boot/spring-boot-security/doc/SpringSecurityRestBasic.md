# Secure Spring REST API using Basic Authentication

## What is Basic Authentication?
[Basic Authentication](https://en.wikipedia.org/wiki/Basic_access_authentication) provides a solution for this problem, although not very secure. With Basic Authentication, clients send it’s Base64 encoded credentials **with each request**, using HTTP [Authorization] header . That means each request is independent of other request and server may/does not maintain any state information for the client, which is good for scalability point of view.

Shown below is the sample code for preparing the header.
```
String plainClientCredentials="myusername:mypassword";
String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));
HttpHeaders headers = getHeaders();
headers.add("Authorization", "Basic " + base64ClientCredentials);
```

which may in turn produce something like:
```
Authorization : Basic bXktdHJ1c3RlZC1jbGllbnQ6c2VjcmV0...
```

## Basic Authentication & Spring Security
With two steps, you can enable the Basic Authentication in Spring Security Configuration.
1. `Configure httpBasic` : Configures HTTP Basic authentication. [http-basic in XML]
2. `Configure authentication entry point with BasicAuthenticationEntryPoint` : In case the Authentication fails [invalid/missing credentials], this entry point will get triggered. It is very important, because we don’t want [Spring Security default behavior] of redirecting to a login page on authentication failure [ We don’t have a login page].

## Results
- `BootSecurityApplication`
- `SpringRestClient`

```
http://localhost:8072/user/
# Authorization -> Basic Auth
admin, pass
# Body -> raw
{
"name":"T5750",
"age":99,
"salary":99
}
```

## References
- [Secure Spring REST API using Basic Authentication](http://websystique.com/spring-security/secure-spring-rest-api-using-basic-authentication/)
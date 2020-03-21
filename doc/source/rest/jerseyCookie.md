# Jersey Set Cookie

## Set Cookie Syntax
To set a cookie in REST API response, get the `Response` reference and use it’s `cookie()` method.
```
Response.ok().entity(list).cookie(new NewCookie("cookieResponse", "cookieValueInReturn")).build();
```

## Rest API Example Code
```java
public class EmployeesService {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getAllEployees() {
		Employees list = new Employees();
		list.setEmployeeList(new ArrayList<Employee>());
		list.getEmployeeList().add(new Employee(1, "Lokesh Gupta"));
		list.getEmployeeList().add(new Employee(2, "Alex Kolenchiskey"));
		list.getEmployeeList().add(new Employee(3, "David Kameron"));
		return Response.ok().entity(list).cookie(new NewCookie("cookieResponse", "cookieValueInReturn")).build();
	}
}
```

## Demo
- `JerseyCookieTest`

## References
- [Jersey – How to set Cookie in REST API Response](https://howtodoinjava.com/jersey/jersey-how-to-set-cookie-in-rest-api-response/)
- [Jersey Client – Set Cookie Example](https://howtodoinjava.com/jersey/jersey-client-cookie-example/)
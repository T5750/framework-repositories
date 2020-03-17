package t5750.rest.jersey.service;

import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import t5750.rest.jersey.model.Employee;
import t5750.rest.jersey.model.Employees;
import t5750.rest.jersey.util.Globals;
import io.swagger.annotations.Api;

@Path("/employees")
@Api(value = "employees")
public class EmployeesService {
	@Context
	private UriInfo uriInfo;
	private static Employees employees;

	public static Employees initEmployees() {
		if (employees == null) {
			employees = new Employees();
			employees.setEmployeeList(new ArrayList<Employee>());
			employees.getEmployeeList().add(new Employee(1, "Lokesh Gupta"));
			employees.getEmployeeList().add(
					new Employee(2, "Alex Kolenchiskey"));
			employees.getEmployeeList().add(new Employee(3, "David Kameron"));
		}
		return employees;
	}

	@RolesAllowed(Globals.ADMIN)
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Employees getAllEmployees() {
		employees = initEmployees();
		return employees;
	}

	@RolesAllowed(Globals.ADMIN)
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addEmployee(Employee e) throws URISyntaxException {
		if (e == null) {
			return Response.status(400)
					.entity("Please add employee details !!").build();
		}
		if (e.getName() == null) {
			return Response.status(400)
					.entity("Please provide the employee name !!").build();
		}
		employees = initEmployees();
		employees.getEmployeeList().add(e);
		return Response.ok().entity(employees).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployeeById(@PathParam("id") Integer id) {
		if (id < 0) {
			return Response.noContent().build();
		}
		employees = initEmployees();
		Employee emp = employees.getEmployeeList().get(id - 1);
		GenericEntity<Employee> entity = new GenericEntity<Employee>(emp,
				Employee.class);
		return Response.ok().entity(entity).build();
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateEmployeeById(@PathParam("id") Integer id, Employee e) {
		employees = initEmployees();
		if (id > employees.getEmployeeList().size()) {
			return Response.status(400)
					.entity("Please provide a valid employee id !!").build();
		}
		Employee updatedEmployee = employees.getEmployeeList().get(id - 1);
		if (e.getName() == null) {
			return Response.status(400)
					.entity("Please provide the employee name !!").build();
		}
		updatedEmployee.setId(id);
		updatedEmployee.setName(e.getName());
		return Response.ok().entity(updatedEmployee).build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteEmployeeById(@PathParam("id") Integer id) {
		employees = initEmployees();
		if (id > employees.getEmployeeList().size()) {
			return Response.status(400)
					.entity("Please provide a valid employee id !!").build();
		}
		employees.getEmployeeList().remove(id - 1);
		return Response.status(202).entity("Employee deleted successfully !!")
				.build();
	}
}

package t5750.rest.jersey.provider;

import java.lang.reflect.Method;
import java.util.*;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.apache.commons.codec.binary.Base64;

import t5750.rest.jersey.util.Globals;

/**
 * This filter verify the access permissions for a user based on username and
 * passowrd provided in request
 */
@Provider
public class AuthenticationFilter
		implements javax.ws.rs.container.ContainerRequestFilter {
	@Context
	private ResourceInfo resourceInfo;
	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Basic";
	private static final String ACCESS_DENIED = "You cannot access this resource";
	private static final String ACCESS_FORBIDDEN = "Access blocked for all users !!";

	private Response buildResponse(Object entity) {
		return Response.status(Response.Status.UNAUTHORIZED).entity(entity)
				.build();
	}

	@Override
	public void filter(ContainerRequestContext requestContext) {
		UriInfo uriInfo = requestContext.getUriInfo();
		String path = uriInfo.getPath();
		if (path.contains(Globals.PATH_FILTER)) {
			Method method = resourceInfo.getResourceMethod();
			// Access allowed for all
			if (!method.isAnnotationPresent(PermitAll.class)) {
				// Access denied for all
				if (method.isAnnotationPresent(DenyAll.class)) {
					requestContext.abortWith(buildResponse(ACCESS_FORBIDDEN));
					return;
				}
				// Get request headers
				final MultivaluedMap<String, String> headers = requestContext
						.getHeaders();
				// Fetch authorization header
				final List<String> authorization = headers
						.get(AUTHORIZATION_PROPERTY);
				// If no authorization information present; block access
				if (authorization == null || authorization.isEmpty()) {
					requestContext.abortWith(buildResponse(ACCESS_DENIED));
					return;
				}
				// Get encoded username and password
				final String encodedUserPassword = authorization.get(0)
						.replaceFirst(AUTHENTICATION_SCHEME + " ", "");
				// Decode username and password
				String usernameAndPassword = new String(
						Base64.decodeBase64(encodedUserPassword.getBytes()));
				// Split username and password tokens
				final StringTokenizer tokenizer = new StringTokenizer(
						usernameAndPassword, ":");
				final String username = tokenizer.nextToken();
				final String password = tokenizer.nextToken();
				// Verifying Username and password
				// System.out.println(username);
				// System.out.println(password);
				// Verify user access
				if (method.isAnnotationPresent(RolesAllowed.class)) {
					RolesAllowed rolesAnnotation = method
							.getAnnotation(RolesAllowed.class);
					Set<String> rolesSet = new HashSet<String>(
							Arrays.asList(rolesAnnotation.value()));
					// Is user valid?
					if (!isUserAllowed(username, password, rolesSet)) {
						requestContext.abortWith(buildResponse(ACCESS_DENIED));
						return;
					}
				}
			}
		}
	}

	private boolean isUserAllowed(final String username, final String password,
			final Set<String> rolesSet) {
		boolean isAllowed = false;
		// Step 1. Fetch password from database and match with password in
		// argument
		// If both match then get the defined role for user from database and
		// continue; else return isAllowed [false]
		// Access the database and do this part yourself
		// String userRole = userMgr.getUserRole(username);
		if (username.equals(Globals.T5750)
				&& password.equals(Globals.PASSWORD)) {
			String userRole = "ADMIN";
			// Step 2. Verify user role
			if (rolesSet.contains(userRole)) {
				isAllowed = true;
			}
		}
		return isAllowed;
	}
}
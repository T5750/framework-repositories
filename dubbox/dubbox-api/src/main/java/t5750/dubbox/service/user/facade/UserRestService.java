package t5750.dubbox.service.user.facade;

import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

import t5750.dubbox.service.user.User;

@Path("users")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
@Produces({ ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8 })
@Service
public interface UserRestService {
	@GET
	@Path("{id : \\d+}")
	User getUser(
			@PathParam("id") @Min(value = 1L, message = "User ID must be greater than 1") Long id/*
																									 * ,
																									 * HttpServletRequest
																									 * request
																									 */);

	@POST
	@Path("register")
	RegistrationResult registerUser(User user);
}
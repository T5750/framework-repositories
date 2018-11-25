package t5750.dubbox.service.user.facade;

import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

import t5750.dubbox.service.user.User;

@Path("u")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
@Produces({ ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8 })
public interface AnotherUserRestService {
	@GET
	@Path("{id : \\d+}")
	User getUser(@PathParam("id") @Min(1L) Long id);

	@POST
	@Path("register")
	RegistrationResult registerUser(User user);
}
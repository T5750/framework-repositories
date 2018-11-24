package t5750.dubbox.service.hello;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

import t5750.dubbox.service.user.User;

@Path("hello")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
@Produces({ ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8 })
@Service
public interface HelloService {
	@GET
	@Path("{name:[\\u4e00-\\u9fa5_a-zA-Z0-9]+}")
	User hello(@PathParam("name") String name);
}
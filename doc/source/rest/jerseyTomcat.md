# Jersey Tomcat

## JSR 311 (JAX-RS) 和 Jersey
Jersey 是 JAX-RS 的参考实现，它包含三个主要部分：
- 核心服务器（Core Server）：通过提供 JSR 311 中标准化的注释和 API 标准化，您可以用直观的方式开发 RESTful Web 服务。
- 核心客户端（Core Client）：Jersey 客户端 API 帮助您与 REST 服务轻松通信。
- 集成（Integration）：Jersey 还提供可以轻松集成 Spring、Guice、Apache Abdera 的库。

## 构建 RESTful Web 服务
```java
@Path("/hello")
public class HelloResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello() {
        return "Hello Jersey";
    }
}
```
- 资源类（Resource Class）：注意，资源类是一个简单的 Java 对象 (POJO)，可以实现任何接口。这增加了许多好处，比如可重用性和简单
- 注释（Annotation）：在 `javax.ws.rs.*` 中定义，是 JAX-RS (JSR 311) 规范的一部分
- `@Path`：定义资源基 URI。由上下文根和主机名组成，资源标识符类似于 http://localhost:8080/rest/jersey/hello
- `@GET`：这意味着以下方法可以响应 HTTP GET 方法
- `@Produces`：以纯文本方式定义响应内容 MIME 类型

## 资源
```java
@Path("/contacts")
public class ContactsResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<Contact>();
        contacts.addAll( ContactStore.getStore().values());
        return contacts;
    }

    @Path("{contact}")
    public ContactResource getContact(
            @PathParam("contact") String contact) {
        return new ContactResource(uriInfo, request, contact);
    }
}
```
- `@Context`：使用该注释注入上下文对象，比如 `Request`、`Response`、`UriInfo`、`ServletContext` 等
- `@Path("{contact}")`：这是 `@Path` 注释，与根路径 `/contacts` 结合形成子资源的 URI
- `@PathParam("contact")`：该注释将参数注入方法参数的路径。其他可用的注释有 `@FormParam`、`@QueryParam` 等
- `@Produces`：响应支持多个 MIME 类型

## 与 REST 服务通讯的客户端
### 使用 curl 与 REST 服务通讯
```
curl http://localhost:8080/rest/jersey/contacts
curl –HAccept:application/json http://localhost:8080/rest/jersey/contacts
curl -X PUT -HContent-type:application/xml --data "<contact><id>foo</id><name>bar</name></contact>" http://localhost:8080/rest/jersey/contacts/foo
```

## References
- [使用 Jersey 和 Apache Tomcat 构建 RESTful Web 服务](https://www.ibm.com/developerworks/cn/web/wa-aj-tomcat/)
- [Jersey 2 hello world example – Jersey 2 tutorial](https://howtodoinjava.com/jersey/jersey2-hello-world-example/)
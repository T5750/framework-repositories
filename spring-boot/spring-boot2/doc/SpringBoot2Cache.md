# Spring Boot 2 Cache

## Ehcache 3.x
1. Maven dependencies
    - Older spring boot versions support `ehcache 2.x` available under net.sf.ehcache package.
    - New versions of Spring boot support `ehcache 3.x` available under org.ehcache package.
2. `application.properties`
    - `spring.cache.jcache.config=classpath:ehcache.xml`
3. `ehcache.xml`
    - Find the complete list of attributes in the [ehcache documentation](https://www.ehcache.org/documentation/3.0/107.html#supplement-jsr-107-configurations).
4. `CacheEventListener`: `CustomCacheEventListener`
5. `@EnableCaching`: `CacheConfig`
6. `@Cacheable` Annotation: `EmployeeManager`
7. Spring CacheManager API
8. Spring boot ehcache 3 example – demo
    - `Employee`
    - `EhcacheController`

## References
- [Spring boot 2 and ehcache 3 example](https://howtodoinjava.com/spring-boot2/ehcache3-config-example/)
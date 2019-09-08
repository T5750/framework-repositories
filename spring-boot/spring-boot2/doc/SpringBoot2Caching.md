# Spring Boot 2 Caching

## Types of cache
1. In-memory caching
1. Database caching
1. Web server caching
1. CDN caching

## Cache annotations
1. `@EnableCaching`
1. `@Cacheable`
	```
	@Cacheable(value="books", key="#isbn")
	public Book findStoryBook(ISBN isbn, boolean checkWarehouse, boolean includeUsed)

	@Cacheable(value="books", key="#isbn.rawNumber")
	public Book findStoryBook (ISBN isbn, boolean checkWarehouse, boolean includeUsed)

	@Cacheable(value="books", key="T(classType).hash(#isbn)")
	public Book findStoryBook (ISBN isbn, boolean checkWarehouse, boolean includeUsed)

	@Cacheable(value="book", condition="#name.length < 50")
	public Book findStoryBook (String name)
	```
1. `@CachePut`
1. `@CacheEvict`
1. `@Caching`

## Register a cache engine
- JCache (JSR-107) (EhCache 3, Hazelcast, Infinispan, and others)
- EhCache 2.x
- Hazelcast
- Infinispan
- Couchbase
- Redis
- Caffeine
- Simple cache

## References
- [Spring boot caching tutorial with example](https://howtodoinjava.com/spring-boot2/spring-boot-cache-example/)
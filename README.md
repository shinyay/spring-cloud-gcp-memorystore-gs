# Spring Cloud GCP for Cloud Memorystore

Cloud Memorystore for Redis provides a fully managed in-memory data store service.
Spring Cache can be integrated with Cloud Memoerystore.

## Description
### Dependency
- org.springframework.boot
  - `spring-boot-starter-cache`
  - `spring-boot-starter-data-redis`
- redis.clients
  - `jedis`

### Redis Configuration
Spring App needs `RedisConnectionFactory` and `RedisTemplate` to connect with Redis.
Therefore, I used **Jedis**.

#### JedisConnectionFactory (RedisConnectionFactory)
You need to add Jedis dependency "`redis.clients:jedis`" to use `RedisConnectionFactory` even if you've added `spring-boot-starter-data-redis`

```kotlin
@Bean
fun jedisConnectionFactory(): JedisConnectionFactory = JedisConnectionFactory()
```

##### Redis Client
- [Jedis](https://github.com/redis/jedis)
- [Lettuce](https://github.com/lettuce-io/lettuce-core)

#### RedisTemplate
RedisTemplate uses `JdkSerializationRedisSerializer` by default.
By `JdkSerializationRedisSerializer`, Redis stores "Serialized Data" to Key and Value.

Redis Serializer has some kinds of serializer:

|Serializer|Description|
|----------|-----------|
|JdkSerializationRedisSerializer|Java Serialization Redis serializer.<br>Delegates to the default (Java based) serializer and DefaultDeserializer.|
|GenericToStringSerializer|Generic String to byte[] (and back) serializer.<br>The Strings are convert into bytes and vice-versa using the specified charset (by default UTF-8).|
|StringRedisSerializer|Simple String to byte[] (and back) serializer.<br>Converts Strings into bytes and vice-versa using the specified charset (by default UTF-8).|
|GenericJackson2JsonRedisSerializer|Generic Jackson 2-based RedisSerializer that maps objects to JSON using dynamic typing.|

##### Issue for JdkSerializationRedisSerializer
If you use `JdkSerializationRedisSerializer` to store "Non-Serialized Data", you have some characters in front of data.

```
\xac\xed\x00\x05t\x00\x02
```

Therefore, you should configure **RedisTemplate** for the following:

- `keySerializer`
- `valueSerializer`
- `hashKeySerializer`
- `hashValueSerializer`

#### @EnableRedisRepositories
Annotation to activate Redis repositories. If no base package is configured through either value(), basePackages() or basePackageClasses() it will trigger scanning of the package of annotated class.

```kotlin
@Configuration
@ComponentScan("com.google.shinyay")
@EnableRedisRepositories("com.google.shinyay.repository")
@PropertySource("classpath:application.yml")
class RedisConfig
```

By `@EnableRedisRepositories`, Spring scan your packages for repository classes/interfaces and then use Redis as the storage to persist your objects to

#### @RedisHash / @Id
`@RedisHash` marks Objects as aggregate roots to be stored in a Redis hash.

- `@RedisHash` -> Hash in Redis
- `@Id` -> Key

```kotlin
@RedisHash("book")
data class Book(@Id val isbn: String,
                var title: String) : Serializable
```

### Caching Configuration
#### @EnableCaching annotation
`@EnableCaching` triggers a post-processor that inspects every Spring bean for the presence of caching annotations on public methods.
The post-processor handles the following annotations:
- `@Cacheable`
- `@CachePut`
- `@CacheEvict`

Spring Boot automatically configures a suitable `CacheManager` to serve as a provider for the relevant cache.

##### Supported Cache Providers
If you have not defined a bean of type `CacheManager` or a `CacheResolver`,
Spring Boot tries to detect the following providers:

- Generic
- JCache (JSR-107)
- EhCache 2.x
- Hazelcast
- Infinispan
- Couchbase
- Redis
- Caffeine
- Simple

##### RedisCacheManager
If Redis is available and configured, a RedisCacheManager is auto-configured.

### Cache Annotation
|Annotation|Description|
|----------|-----------|
|@Cacheable|Putting the result into the cache.|
|@CacheEvict|Removing items from cache|
|@CachePut|Updating the cache, without interfering with the method execution|
|@Caching|Aggregate multiple annotations of the same typed|

#### Conditional Caching
##### Condition Parameter
We can define the condition with SpEL expression
```kotlin
@CachePut(value="addresses", condition="#customer.name=='Tom'")
fun getAddress(customer: Customer) {...}
```

##### Unless Parameter
We can also control the caching **based on the output of the method rather than the input** – via the unless parameter

```kotlin
@CachePut(value="addresses", unless="#result.length()<64")
fun getAddress(customer: Customer) {...}
```

## Demo
### Set up Cloud Memorystore for Redis
#### Enable Service
```shell script
$ gcloud services enable redis.googleapis.com
```

#### Create Redis Instance
```shell script
$ gcloud redis instances create my-redis --size=1 --region=us-central1
$ gcloud redis instances describe my-redis --region us-central1
```

##### Host Address
```shell script
$ gcloud redis instances describe my-redis --region us-central1 --format json | jq -r '.host'
```

#### Configure Redis to Connect
```shell script
$ set -x REDIS_HOST (gcloud redis instances describe my-redis --region us-central1 --format json | jq -r '.host')
```

### Operate App
#### Save Data
```shell script
$ curl -X POST -H "Content-Type: application/json" -d '{"isbn":"1111","title":"demo"}' localhost:8080/api/v1/books
```

#### Retrieve Data
```shell script
$ curl -X GET "localhost:8080/api/v1/books?isbn=1111"
```

## Features

- feature:1
- feature:2

## Requirement

## Usage

## Installation

## Licence

Released under the [MIT license](https://gist.githubusercontent.com/shinyay/56e54ee4c0e22db8211e05e70a63247e/raw/34c6fdd50d54aa8e23560c296424aeb61599aa71/LICENSE)

## Author

[shinyay](https://github.com/shinyay)

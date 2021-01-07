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
Therefore, I used *Jedis*.

#### JedisConnectionFactory (RedisConnectionFactory)
You need to add Jedis dependency "`redis.clients:jedis`" to use `RedisConnectionFactory` even if you've added `spring-boot-starter-data-redis`

```kotlin
@Bean
fun jedisConnectionFactory(): JedisConnectionFactory = JedisConnectionFactory()
```

#### RedisTemplate
RedisTemplate uses `JdkSerializationRedisSerializer` by default.

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

### @Cacheable
@Cacheable annotation takes care of putting the result into the cache.

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

# little-url
> 

<a href="https://opensource.org/licenses/MIT"><img src="https://img.shields.io/badge/License-MIT-blue.svg"></a>

## About

LittleUrl is an API for short URL creation.  

The main requirements that guided the design and implementation of LittleUrl:
*	Design and implement an API for short URL creation
*	Implement forwarding of short URLs to the original ones
*	There should be some form of persistent storage
*	The application should be distributed as one or more Docker images
*	It should be readable, maintainable, and extensible where appropriate
*	The implementation should preferably be in Java

#### Next steps

There are still some important requirements that will guide the next steps of this API implementation:
* Add an API for gathering different statistics
* Implement an authentication service
* Implement a cache to improve API performance

## Architecture overview

#### Project structure
```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── neueda
│   │   │           └── littleurl
│   │   │               ├── domain
│   │   │               │   └── Url.java
│   │   │               ├── dto
│   │   │               │   ├── UrlDTO.java
│   │   │               │   └── UrlUpdateDTO.java
│   │   │               ├── helpers
│   │   │               │   └── UrlShortnerHelper.java
│   │   │               ├── repositories
│   │   │               │   └── UrlRepository.java
│   │   │               ├── resources
│   │   │               │   └── UrlResources.java
│   │   │               ├── services
│   │   │               │   ├── exceptions
│   │   │               │   │   └── UrlNotFoundException.java
│   │   │               │   └── UrlService.java
│   │   │               └── util
│   │   │                   └── Constants.java
│   │   └── resources
│   │       └── application.properties
│   └── test
│       └── java
│           └── com
│               └── neueda
│                   └── littleurl
│                       ├── helpers
│                       │   └── UrlShortnerHelperTest.java
│                       ├── resources
│                       │   └── UrlResourcesTest.java
│                       ├── services
│                       │   └── UrlServiceTest.java
│                       └── LittleUrlApplicationTests.java
├── docker-compose.yml
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```
#### Overview picture

![littleurl](https://user-images.githubusercontent.com/43149895/57698153-5009c680-762b-11e9-930b-86a55b7a2435.png)

#### Tech stack
* [Spring Boot](http://spring.io/projects/spring-boot) for creating the RESTful Web Services
* [MockMVC](https://spring.io/guides/gs/testing-web/) for testing the Web Layer
* [Mockito](https://site.mockito.org/) for testing the Services Layer
* [Postgres](https://www.postgresql.org/) as database
* [Maven](https://maven.apache.org/) for managing the project's build
* [Docker](https://www.docker.com/) for building and managing the application distribution using containers 

## Install
#### Download the repository
```sh
$ git clone https://github.com/thiagoferrax/little-url.git
```
#### With docker and docker-compose installed
```sh
$ cd little-url && docker-compose up
```

## License

MIT © [thiagoferrax](https://github.com/thiagoferrax)

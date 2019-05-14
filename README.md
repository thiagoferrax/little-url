# little-url
> 

<a href="https://opensource.org/licenses/MIT"><img src="https://img.shields.io/badge/License-MIT-blue.svg"></a>

## About

LittleUrl is an API for short URL creation.  

The main requirements that guided the design and implementation of LittleUrl were:
*	Design and implement an API for short URL creation
*	Implement forwarding of short URLs to the original ones
*	There should be some form of persistent storage
*	The application should be distributed as one or more Docker images
*	It should be readable, maintainable, and extensible where appropriate
*	The implementation should preferably be in Java

## Next steps

There are still some important requirements that will guide the next steps of this API implementation:
* Design and implement an API for gathering different statistics
* Implement an authentication service
* Implement a caching to improve API performance

## Tech stack details:

* [Spring Boot](http://spring.io/projects/spring-boot) for creating the RESTful Web Services
* [MockMVC](https://spring.io/guides/gs/testing-web/) for testing the Web Layer
* [Mockito](https://site.mockito.org/) for testing the Services Layer
* [Postgres](https://www.postgresql.org/) as database
* [Maven](https://maven.apache.org/) for managing the project's build
* [Docker](https://www.docker.com/) for building and managing the application distribution using containers 

## Install
##### Download the repository
```sh
$ git clone https://github.com/thiagoferrax/little-url.git
```
##### With docker and docker-compose installed
```sh
$ cd little-url && docker-compose up
```

## License

MIT © [thiagoferrax](https://github.com/thiagoferrax)

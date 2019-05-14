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

## Built With

* [Spring Boot](http://spring.io/projects/spring-boot)
* [Postgres](https://www.postgresql.org/)
* [Maven](https://maven.apache.org/)
* [Docker](https://www.docker.com/)

## Install
### Download the repository
```sh
$ git clone https://github.com/thiagoferrax/little-url.git
```
### With docker and docker-compose installed
```sh
$ docker-compose up
```

## License

MIT © [thiagoferrax](https://github.com/thiagoferrax)

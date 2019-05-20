# little-url
> 

<a href="https://opensource.org/licenses/MIT"><img src="https://img.shields.io/badge/License-MIT-blue.svg"></a>

## About

The little-url is an API for short URL creation.  

The main requirements that guided the design and implementation of little-url:
*	Design and implement an API for short URL creation
*	Implement forwarding of short URLs to the original ones
*	Add an API for gathering different statistics
*	There should be some form of persistent storage
*	The application should be distributed as one or more Docker images
*	It should be readable, maintainable, and extensible where appropriate
*	The implementation should preferably be in Java

#### Next steps

There are still other requirements that will guide the next steps of this API implementation:
* Implement authentication and authorization services
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
│   │   │               │   ├── Url.java
│   │   │               │   └── Statistic.java
│   │   │               ├── dto
│   │   │               │   ├── UrlDTO.java
│   │   │               │   ├── UrlUpdateDTO.java
│   │   │               │   ├── StatisticsDTO.java
│   │   │               │   └── StatisticsSummaryDTO.java
│   │   │               ├── helpers
│   │   │               │   └── UrlShortnerHelper.java
│   │   │               ├── repositories
│   │   │               │   ├── UrlRepository.java
│   │   │               │   └── StatisticRepository.java
│   │   │               ├── resources
│   │   │               │   ├── UrlResources.java
│   │   │               │   └── StatisticResources.java
│   │   │               ├── services
│   │   │               │   ├── exceptions
│   │   │               │   │   └── UrlNotFoundException.java
│   │   │               │   ├── UrlService.java
│   │   │               │   └── StatisticService.java
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
│                       │   ├── UrlResourcesTest.java
│                       │   └── StatisticResourcesTest.java
│                       └── services
│                           ├── UrlServiceTest.java
│                           ├── StatisticServiceTest.java
│                           └── StatisticServiceParameterizedTest.java
├── docker-compose.yml
├── pom.xml
└── README.md
```
#### Overview picture

![littleurl](https://user-images.githubusercontent.com/43149895/57924693-390de300-787c-11e9-9752-920a385531c2.png)

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
## Usage

Request Method | URI | Body (JSON) | Description |  
:---: | :--- | :---: | :--- |
GET | http://localhost/urls | - | Get all urls | 
GET | http://localhost/urls/{code} | - | Find long url and redirect | 
GET | http://localhost/urls/{code}/longUrl | - | Find and return long url | 
GET | http://localhost/statistics | - | Get all statistics |
GET | http://localhost/urls/{code}/statistics | - | Get the statistics for a specific url code |
GET | http://localhost/statistics/summary | - | Get the statistics summary |
GET | http://localhost/statistics/summary/{code} | - | Get the statistics summary for a specific url code |
POST | http://localhost/urls/ | { "longUrl": "[http...]" } | Find or create url and return its shorten url in response headers | 
PUT | http://localhost/urls/{code} | { "code": "[code]", "longUrl": "[http...]" } | Update url | 
DELETE | http://localhost/urls/{code} | - | Remove url | 

## License

MIT © [thiagoferrax](https://github.com/thiagoferrax)

# Book shop in Java spring

## 1. Packages

	1.1 spring-boot-starter-web
	1.2 spring-boot-starter-data-rest (build restful API)
	1.3 spring-boot-starter-security
	1.4 spring-security-test
	1.5 spring-boot-starter-data-jpa
	1.6 h2 (database)
	1.7 spring-boot-starter-test (unit test)	
	1.8 mariadb-java-client (connect maria database)
	1.9 gson (json parser)
	1.10 jjwt (json webtoken)
	1.11 spring-boot-configuration-processor (load configuration)
	1.12 opencsv (import/export CSV)
	1.13 spring-boot-starter-log4j2 (logging)
	1.14 disruptor (asynchronous loggers)
	1.15 jackson-dataformat-yaml 
	1.16 spring-context-support
	1.17 modelmapper (entity <> DTO)
	1.18 hibernate-validator 
	1.19 spring-boot-devtools (enable live reload)
		
## 2. Features
		
	- Handle all exceptions (with message and defined status code)
	- Handle all request security 
	- Unit test cover
	- Read configuration from properties
	- Handle multiple types of constraint database
		
## 3. Samples
	
	- Book shop
	- CSV import/export
	- Cache
	- Logging			
	
## 4. Runs

	- Checkout source code
	- Run project by command: "mvn spring-boot:run"	
	
## 5. APIs

	5.1 	 /api/v1/logout
	5.2 	POST /api/v1/auth
	5.3 	POST /api/v1/author/{id}
	5.4 	GET /api/v1/author
	5.5 	POST /api/v1/author/{id}/vote
	5.6 	GET /api/v1/vote
	5.7 	GET /api/v1/book/{id}
	5.8 	GET /api/v1/book
	5.9 	PUT /api/v1/book/{id}
	5.10 	POST /api/v1/book
	5.11 	POST /api/v1/book/{id}/rate
	5.12 	DELETE /api/v1/book/{id}
	5.13	GET /api/v1/book/{id}/rate
	5.14	GET /api/v1/clear-cache
	5.15	 /error
	5.16 	 /api/v1/error
	5.17 	POST /api/v1/create-data
	5.18	POST /api/v1/file/upload
	5.19	GET /api/v1/file/download/{id}
	5.20 	POST /api/v1/file/uploadMultiple
	5.21	POST /api/v1/user/regist
	5.22	GET /api/v1/user	
	5.23 	PUT /api/v1/user/{id}	
	5.24	GET /api/v1/user/{id}/following
	
## 5. APIs document Postman
	
	https://documenter.getpostman.com/view/339456/SzRxXqjF?version=latest
	
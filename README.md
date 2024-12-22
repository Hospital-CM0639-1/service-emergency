Spring Boot Docker Service Emergency
==========================
This service is a Spring Boot application that provides a RESTful API to handle beds, wards, assign patients to doctor, create patient invoce

Docker
========
Before to create the image and run the docker container, change the datasource of _application.properties_:
```
spring.datasource.url=jdbc:postgresql://hospital-database:5432/hospital
```
Assume that the name of the postgres container is *hospital-database* and the server database is running on port *5432*.
Then run the following command:
```
docker compose up --build -t emergency-service -d
```
The whole service will run at port 6001

API Documentation
========

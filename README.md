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
HOSPITAL BED
- Get all pageable hospitals beds<br/>
  `GET /hospital-beds`
- Get bed by id<br/>
  `GET /hospital-beds/{id}`
- Get bed by patient id<br/>
  `GET /hospital-beds/patient/{patientId}`
- Get bed by bed number<br/>
  `GET /hospital-beds/bed-number/{bedNumber}`
- Get bed by ward and current status<br/>
  `GET /status/{currentStatus}/ward-section/{wardSection}`
- Save a new bed<br/>
  `POST /hospital-beds`
- Update a bed<br/>
  `PUT /hospital-beds/{id}`
- Delete a bed<br/>
`DELETE /hospital-beds/{id}`

  _Example of object:_
    ```json
    {
        "bedNumber": "O207",
        "wardSection": "Orthopedic Ward",
        "currentStatus": "AVAILABLE",
        "lastCleanedTimestamp": "2024-12-21T17:04:11",
        "emergencyVisit": {
            "id": 2
        }
    }
  ```
Spring Boot Docker Service Emergency
==========================
This service is a Spring Boot application that provides a RESTful API to handle beds, wards, assign patients to doctor, create patient invoce

This service provides these functionalities:
- Create, read, update, delete hospital beds
- Create, read, update, delete emergency visits (Assign patients to doctors, nurses, etc)
- Create, read, update, delete patient vitals
- Create, read, update, delete patient invoices

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
The whole service will run at port 6061

API Documentation
========
HOSPITAL BED
- Get all pageable hospitals beds<br/>
  `GET /api/v1/hospital-beds`
- Get bed by id<br/>
  `GET /api/v1/hospital-beds/{id}`
- Get bed by patient id<br/>
  `GET /api/v1/hospital-beds/patient/{patientId}`
- Get bed by bed number<br/>
  `GET /api/v1/hospital-beds/bed-number/{bedNumber}`
- Get bed by ward and current status<br/>
  `GET /api/v1/status/{currentStatus}/ward-section/{wardSection}`
- Save a new bed and assign<br/>
  `POST /api/v1/hospital-beds`
<br/>Example:
  ```json
  {
    "bedNumber": "C102",
    "wardSection": "Cardiac Ward",
    "currentStatus": "AVAILABLE",
    "lastCleanedTimestamp": "2024-12-21T16:04:11.929244",
    "emergencyVisit": { // can be empty
      "id": 1
    }
  }
  ```
- Update a bed<br/>
  `PUT /api/v1/hospital-beds/{id}`
- Assign a patient to a bed<br/>
  `PUT /api/v1/hospital-beds/assign-patient/{id}/hospital-bed/{id}`
- Delete a bed<br/>
`DELETE /api/v1/hospital-beds/{id}`

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
EMERGENCY VISIT
- Get all pageable emergency visits<br/>
  `GET /api/v1/emergency-visit-staff`
- Get all patient assigned to a staff<br/>
  `GET /api/v1/emergency-visit-staff/doctor/{doctorId}`
- Get all staff assigned to a patient<br/>
  `GET /api/v1/emergency-visit-staff/patient/{patientId}`
- Get staff and patient involed in an emergency visit<br/>
  `GET /api/v1/emergency-visit-staff/visit/{visitId}`
- Get all emergency visits between date<br/>
  `GET /api/v1/emergency-visit-staff/dates?startDate/{yyyy-MM-dd HH:mm}&endDate/{yyyy-MM-dd HH:mm}`
- Save a new emergency visit<br/>
  `POST /api/v1/emergency-visit-staff`
- Update an emergency visit<br/>
  `PUT /api/v1/emergency-visit-staff/visit/{visitId}/staff/{staffId}`
- Delete an emergency visit<br/>
  `DELETE /api/v1/emergency-visit-staff/visit/{visitId}/staff/{staffId}`

  _Example of object:_
    ```json
    {
        "staffRole": "NURSE",
        "visitId": 1,
        "staffId": 3,
        "assignedAt": "2024-12-21T16:19:11.920876"
    }
  ```
PATIENT VITALS
- Get all vitals of patient in a specific period of time.<br/>
  `GET /api/v1/patient-vitals/patient/{patientId}?startDate={yyyyy-MM-dd HH:mm}&endDate={yyyyy-MM-dd HH:mm}`
- Get all patient vitals visited from staff in a specific period of time.<br/>
  `GET /api/v1/patient-vitals/staff/{doctorId}?startDate={yyyyy-MM-dd HH:mm}&endDate={yyyyy-MM-dd HH:mm}`
- Get all patient vitals paged.<br/>
  `GET /api/v1/patient-vitals?page={page}&size={size}&sort={sort}`
- Create a new patient vitals.<br/>
  `POST /api/v1/patient-vitals`
- Update a patient vitals.<br/>
  `PUT /api/v1/patient-vitals/{id}`
- Delete a patient vitals.<br/>
  `DELETE /api/v1/patient-vitals/{id}`
  _Example of object:_
  ```json
  {
    "id": 1,
    "recordedAt": "2024-12-04T09:30:00",
    "bodyTemperature": 38.2,
    "bloodPressureSystolic": 130,
    "bloodPressureDiastolic": 85,
    "heartRate": 90,
    "respiratoryRate": 20,
    "oxygenSaturation": 96.5,
    "additionalObservations": "Post-surgery monitoring, vitals stable",
    "staff": {
      "id": 1
    },
    "emergencyVisit": {
      "id": 1
    }
  }
  ```
PATIENT INVOICE
- Get all invoices<br/>
  `GET /api/v1/patient-invoices`
- Get invoice by id<br/>
- `GET /api/v1/patient-invoices/{id}`
- Get invoice by patient id<br/>
  `GET /api/v1/patient-invoices/patient/{patientId}`
- Get invoice by visit id<br/>
  `GET /api/v1/patient-invoices/visit/{visitId}`
- Save a new invoice<br/>
  `POST /api/v1/patient-invoices`
- Update an invoice<br/>
  `PUT /api/v1/patient-invoices/{id}`
- Delete an invoice<br/>
  `DELETE /patient-invoices/{id}`
 <br/> _Example of object:_

  ```json
  {
    "totalAmount": 1500.0,
    "paymentStatus": "pending",
    "invoiceTimestamp": "2024-12-21T18:04:11.931438",
    "paymentReceivedTimestamp": null,
    "paymentReceivedAmount": 0.0,
    "emergencyVisit": {
        "id": 1
    },
    "createdByStaff": {
        "id": 5
    }
  }
  ```

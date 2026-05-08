# LuxStay – Hotel Management System

LuxStay is a web-based hotel management system developed as part of the Database Systems course at the Faculty of Computer Science and Engineering (FINKI).

The system supports complete hotel reservation management through a modern web application built with Spring Boot and PostgreSQL, with a strong focus on advanced database design and SQL features.

---

## Features

### Guest Functionalities

* User registration and authentication
* Room search by check-in/check-out dates
* Room reservation with packages and additional services
* Automatic disabling of services already included in selected packages
* Installment payments (up to 2 payments)
* Airport Transfer with fixed one-time pricing
* Reservation cancellation with automatic refund calculation
* Review and rating system for completed reservations

### Admin Functionalities

* Reservation management dashboard
* Reservation status updates
* Top rooms analytics using materialized views
* Guest reservation history
* Revenue and guest analysis reports
* Monthly report generation

---

## Technologies Used

### Backend
- Java
- Spring Boot
- Spring Security

### Frontend
- Thymeleaf
- HTML
- CSS
- JavaScript

### Database
- PostgreSQL
---

## Advanced Database Features

### Triggers

* `trg_calculate_price`
* `trg_update_total_price`
* `trg_cancellation_status`
* `trg_check_availability`
* `trg_refresh_top_rooms`

### Views

* `Reservation_Details`
* `Guest_Reservation_History`

### Materialized View

* `top_rooms`

### Functions

* `hotel_revenue_report()`
* `guest_analysis_report()`
* `calculate_total_price()`
* `update_total_price()`

### Procedures

* `create_reservation()`
* `generate_monthly_report()`

### Indexes

* `idx_reservation_guest`
* `idx_reservation_room`
* `idx_reservation_dates`
* `idx_review_reservation`
* `idx_payment_reservation`
* `idx_offer_type`

---

# Database Setup

The project includes a `db/` folder containing all SQL scripts required to initialize the database.

## SQL Files

```text
db/
├── schema.sql
├── data.sql
├── views.sql
├── functions.sql
├── procedures.sql
├── triggers.sql
└── indexes.sql
```

These scripts can be executed on any PostgreSQL database instance.

---

## Database Initialization Order

Execute the SQL files in the following order:

```text
1. schema.sql
2. data.sql
3. views.sql
4. functions.sql
5. procedures.sql
6. triggers.sql
7. indexes.sql
```

---

## Running the Application

### Requirements

* Java 17+
* PostgreSQL
* Maven

### Steps

1. Clone the repository

```bash
git clone <repository-url>
```

2. Create a PostgreSQL database

3. Execute all SQL scripts from the `db/` folder in the correct order

4. Configure database credentials in:

```properties
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/luxstay
spring.datasource.username=postgres
spring.datasource.password=your_password
```

5. Run the application

```bash
mvn spring-boot:run
```

6. Open in browser

```text
http://localhost:8080
```

---

## System Highlights

* Business logic implemented directly in the database
* Prevention of double room reservations using triggers
* Automatic total price calculation
* Installment payment support
* Refund calculation based on cancellation policy
* Performance optimization through indexes and materialized views
* Advanced SQL analytics and reporting

---

## Project Structure

```text
src/
├── controller/
├── service/
├── repository/
├── model/
├── config/
└── templates/

db/
├── schema.sql
├── data.sql
├── views.sql
├── functions.sql
├── procedures.sql
├── triggers.sql
└── indexes.sql
```

---

## Author

Teona Ambarkova
Faculty of Computer Science and Engineering (FINKI)
Ss. Cyril and Methodius University – Skopje

---

## License

This project was developed for educational purposes as part of the Database Systems course at FINKI.

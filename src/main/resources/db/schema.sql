-- =============================================
-- LuxStay Hotel Management System
-- 01_schema.sql - Database Schema
-- =============================================

CREATE TABLE Room_Type (
                           room_type_id SERIAL PRIMARY KEY,
                           type_name VARCHAR(50) NOT NULL,
                           description TEXT,
                           capacity INTEGER NOT NULL,
                           base_price DECIMAL(10,2) NOT NULL
);

CREATE TABLE Room_Amenity (
                              amenity_id SERIAL PRIMARY KEY,
                              amenity_name VARCHAR(100) NOT NULL,
                              description TEXT
);

CREATE TABLE Room_Type_Amenity (
                                   room_type_id INTEGER REFERENCES Room_Type(room_type_id),
                                   amenity_id INTEGER REFERENCES Room_Amenity(amenity_id),
                                   PRIMARY KEY (room_type_id, amenity_id)
);

CREATE TABLE Room (
                      room_id SERIAL PRIMARY KEY,
                      room_number VARCHAR(10) NOT NULL,
                      floor INTEGER NOT NULL,
                      status VARCHAR(20) NOT NULL,
                      room_type_id INTEGER REFERENCES Room_Type(room_type_id)
);

CREATE TABLE Users (
                       user_id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Guest (
                       guest_id SERIAL PRIMARY KEY,
                       first_name VARCHAR(50) NOT NULL,
                       last_name VARCHAR(50) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       nationality VARCHAR(50),
                       date_of_birth DATE,
                       user_id INTEGER REFERENCES Users(user_id)
);

CREATE TABLE Admin (
                       user_id INTEGER PRIMARY KEY REFERENCES Users(user_id)
);

CREATE TABLE Guest_Phone (
                             guest_id INTEGER REFERENCES Guest(guest_id),
                             phone_number VARCHAR(20),
                             PRIMARY KEY (guest_id, phone_number)
);

CREATE TABLE Offer (
                       offer_id SERIAL PRIMARY KEY,
                       offer_name VARCHAR(100) NOT NULL,
                       description TEXT,
                       price DECIMAL(10,2) NOT NULL,
                       offer_type VARCHAR(20) NOT NULL
                           CHECK (offer_type IN ('package', 'service')),
                       is_fixed BOOLEAN DEFAULT FALSE
);

CREATE TABLE Package (
                         offer_id INTEGER PRIMARY KEY
                             REFERENCES Offer(offer_id)
);

CREATE TABLE Service (
                         offer_id INTEGER PRIMARY KEY
                             REFERENCES Offer(offer_id),
                         service_type VARCHAR(50)
);

CREATE TABLE Package_Service (
                                 package_id INTEGER REFERENCES Package(offer_id),
                                 service_id INTEGER REFERENCES Service(offer_id),
                                 PRIMARY KEY (package_id, service_id)
);

CREATE TABLE Reservation (
                             reservation_id SERIAL PRIMARY KEY,
                             reservation_date DATE NOT NULL,
                             check_in_date DATE NOT NULL,
                             check_out_date DATE NOT NULL,
                             number_of_guests INTEGER NOT NULL,
                             total_price DECIMAL(10,2),
                             status VARCHAR(20) NOT NULL,
                             guest_id INTEGER REFERENCES Guest(guest_id),
                             room_id INTEGER REFERENCES Room(room_id)
);

CREATE TABLE Reservation_Offer (
                                   reservation_id INTEGER REFERENCES Reservation(reservation_id),
                                   offer_id INTEGER REFERENCES Offer(offer_id),
                                   total_offer_price DECIMAL(10,2),
                                   PRIMARY KEY (reservation_id, offer_id)
);

CREATE TABLE Payment (
                         payment_id SERIAL PRIMARY KEY,
                         payment_method VARCHAR(50) NOT NULL,
                         amount DECIMAL(10,2) NOT NULL,
                         payment_date DATE NOT NULL,
                         reservation_id INTEGER REFERENCES Reservation(reservation_id)
);

CREATE TABLE Cancellation (
                              cancellation_id SERIAL PRIMARY KEY,
                              cancellation_date DATE NOT NULL,
                              refund_amount DECIMAL(10,2) NOT NULL,
                              reason TEXT,
                              reservation_id INTEGER REFERENCES Reservation(reservation_id)
);

CREATE TABLE Review (
                        review_id SERIAL PRIMARY KEY,
                        rating INTEGER CHECK (rating BETWEEN 1 AND 5),
                        comment TEXT,
                        review_date DATE NOT NULL,
                        guest_id INTEGER REFERENCES Guest(guest_id),
                        reservation_id INTEGER REFERENCES Reservation(reservation_id)
);

CREATE TABLE Monthly_Report (
                                report_id SERIAL PRIMARY KEY,
                                report_date DATE NOT NULL,
                                month INTEGER NOT NULL,
                                year INTEGER NOT NULL,
                                total_reservations BIGINT,
                                total_revenue NUMERIC,
                                avg_stay NUMERIC,
                                total_cancellations BIGINT,
                                most_popular_room_type TEXT
);

-- =============================================
-- ALTER TABLE Constraints
-- =============================================

ALTER TABLE Room
    ADD CONSTRAINT check_room_status
        CHECK (status IN ('available', 'occupied', 'maintenance'));

ALTER TABLE Reservation
    ADD CONSTRAINT check_reservation_status
        CHECK (status IN ('pending', 'confirmed', 'cancelled', 'completed'));

ALTER TABLE Reservation
    ADD CONSTRAINT check_dates
        CHECK (check_out_date > check_in_date);

ALTER TABLE Reservation
    ADD CONSTRAINT check_guests
        CHECK (number_of_guests > 0);

ALTER TABLE Payment
    ADD CONSTRAINT check_amount
        CHECK (amount > 0);

ALTER TABLE Cancellation
    ADD CONSTRAINT check_refund
        CHECK (refund_amount >= 0);

ALTER TABLE Room_Type
    ADD CONSTRAINT check_base_price
        CHECK (base_price > 0);
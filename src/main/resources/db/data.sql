-- =============================================
-- LuxStay Hotel Management System
-- 07_data.sql - Test Data
-- =============================================

INSERT INTO Users (username, password) VALUES
                                           ('admin', 'admin123'),
                                           ('james.anderson', 'pass123'),
                                           ('sofia.mueller', 'pass123'),
                                           ('marco.rossi', 'pass123'),
                                           ('emma.dubois', 'pass123'),
                                           ('alex.petrovski', 'pass123'),
                                           ('yuki.tanaka', 'pass123'),
                                           ('maria.garcia', 'pass123'),
                                           ('john.smith', 'pass123'),
                                           ('teonaamb', 'teona123'),
                                           ('test', 'test');

INSERT INTO Admin (user_id) VALUES (1);

INSERT INTO Room_Type (type_name, description, capacity, base_price) VALUES
                                                                         ('Standard Room', 'Удобна соба со брачен или twin кревет и приватна бања', 2, 75.00),
                                                                         ('Superior Room', 'Попространа соба со поглед на градот', 2, 110.00),
                                                                         ('Deluxe Room', 'Елегантна соба со поглед на море и балкон', 2, 160.00),
                                                                         ('Junior Suite', 'Соба со одделна дневна соба и панорамски поглед', 2, 250.00),
                                                                         ('Executive Suite', 'Луксузен апартман со две спални и приватна тераса', 4, 400.00),
                                                                         ('Family Room', 'Семејна соба со два queen size кревети', 4, 180.00);

INSERT INTO Room_Amenity (amenity_name, description) VALUES
                                                         ('WiFi', 'Бесплатен брз интернет'),
                                                         ('Mini Bar', 'Мини бар со пијалоци'),
                                                         ('Balcony', 'Приватен балкон'),
                                                         ('Jacuzzi', 'Приватна јакузи'),
                                                         ('Safe', 'Електронски сеф'),
                                                         ('Air Conditioning', 'Клима уред');

INSERT INTO Room_Type_Amenity (room_type_id, amenity_id) VALUES
-- Standard Room (1): WiFi, Air Conditioning
(1, 1), (1, 6),
-- Superior Room (2): WiFi, Mini Bar, Safe, Air Conditioning
(2, 1), (2, 2), (2, 5), (2, 6),
-- Deluxe Room (3): WiFi, Mini Bar, Balcony, Safe, Air Conditioning
(3, 1), (3, 2), (3, 3), (3, 5), (3, 6),
-- Junior Suite (4): WiFi, Mini Bar, Balcony, Jacuzzi, Safe, Air Conditioning
(4, 1), (4, 2), (4, 3), (4, 4), (4, 5), (4, 6),
-- Executive Suite (5): сите amenities
(5, 1), (5, 2), (5, 3), (5, 4), (5, 5), (5, 6),
-- Family Room (6): WiFi, Air Conditioning, Safe
(6, 1), (6, 5), (6, 6);

INSERT INTO Room (room_number, floor, status, room_type_id) VALUES
-- Standard Rooms
('101', 1, 'available', 1),
('102', 1, 'available', 1),
('103', 1, 'available', 1),
-- Superior Rooms
('201', 2, 'available', 2),
('202', 2, 'available', 2),
-- Deluxe Rooms
('301', 3, 'available', 3),
('302', 3, 'available', 3),
-- Junior Suites
('401', 4, 'available', 4),
('402', 4, 'available', 4),
-- Executive Suites
('501', 5, 'available', 5),
('502', 5, 'available', 5),
-- Family Rooms
('601', 6, 'available', 6),
('602', 6, 'available', 6);

INSERT INTO Guest (first_name, last_name, email, nationality, date_of_birth, user_id) VALUES
                                                                                          ('James', 'Anderson', 'james.anderson@gmail.com', 'British', '1985-03-15', 2),
                                                                                          ('Sofia', 'Müller', 'sofia.mueller@gmail.com', 'German', '1990-07-22', 3),
                                                                                          ('Marco', 'Rossi', 'marco.rossi@gmail.com', 'Italian', '1978-11-08', 4),
                                                                                          ('Emma', 'Dubois', 'emma.dubois@gmail.com', 'French', '1995-04-30', 5),
                                                                                          ('Aleksandar', 'Petrovski', 'alex.petrovski@gmail.com', 'Macedonian', '1988-09-12', 6),
                                                                                          ('Yuki', 'Tanaka', 'yuki.tanaka@gmail.com', 'Japanese', '1992-01-25', 7),
                                                                                          ('Maria', 'Garcia', 'maria.garcia@gmail.com', 'Spanish', '1983-06-18', 8),
                                                                                          ('John', 'Smith', 'john.smith@gmail.com', 'American', '1975-12-03', 9),
                                                                                          ('Teona', 'Ambarkova', 'teonaambarkova@gmail.com', 'Македонка', '2004-07-30', 10),
                                                                                          ('Marc', 'Jacobs', 'marcjacobs@email.com', 'English', '1991-07-27', 11);

INSERT INTO Guest_Phone (guest_id, phone_number) VALUES
-- James Anderson
(1, '+44 7911 123456'),
(1, '+44 7922 654321'),
-- Sofia Müller
(2, '+49 151 23456789'),
-- Marco Rossi
(3, '+39 333 1234567'),
(3, '+39 347 7654321'),
-- Emma Dubois
(4, '+33 6 12345678'),
-- Aleksandar Petrovski
(5, '+389 70 123456'),
-- Yuki Tanaka
(6, '+81 90 1234 5678'),
-- Maria Garcia
(7, '+34 612 345678'),
(7, '+34 698 765432'),
-- John Smith
(8, '+1 212 555 0123');

INSERT INTO Offer (offer_name, description, price, offer_type, is_fixed) VALUES
-- Packages
('All Inclusive', 'Целосен пакет со сите оброци и пијалоци', 120.00, 'package', FALSE),
('Bed and Breakfast', 'Пакет со ноќевање и појадок', 25.00, 'package', FALSE),
('Half Board', 'Пакет со појадок и вечера', 55.00, 'package', FALSE),
('Full Board', 'Пакет со три оброци дневно', 85.00, 'package', FALSE),
-- Services
('Spa Treatment', 'Релаксирачки спа третман во траење од 60 мин', 45.00, 'service', FALSE),
('Airport Transfer', 'Трансфер од/до аеродром', 30.00, 'service', TRUE),
('Room Service', 'Достава на храна и пијалоци во собата', 15.00, 'service', FALSE),
('City Tour', 'Организирана тура низ градот', 35.00, 'service', FALSE),
('Massage', 'Професионална масажа во траење од 45 мин', 40.00, 'service', FALSE),
('Breakfast', 'Богата шведска маса појадок', 15.00, 'service', FALSE),
('Lunch', 'Тричасовен ручек со избор на менија', 20.00, 'service', FALSE),
('Dinner', 'Вечера со а ла карт мени', 25.00, 'service', FALSE),
('Pool Access', 'Пристап до затворен и отворен базен', 10.00, 'service', FALSE);

INSERT INTO Package (offer_id) VALUES (1), (2), (3), (4);

INSERT INTO Service (offer_id, service_type) VALUES
                                                 (5, 'Wellness'),
                                                 (6, 'Transport'),
                                                 (7, 'Food'),
                                                 (8, 'Tour'),
                                                 (9, 'Wellness'),
                                                 (10, 'Food'),
                                                 (11, 'Food'),
                                                 (12, 'Food'),
                                                 (13, 'Recreation');

INSERT INTO Package_Service (package_id, service_id) VALUES
                                                         (1, 5),
                                                         (1, 6),
                                                         (1, 7),
                                                         (1, 8),
                                                         (1, 9),
                                                         (1, 10),
                                                         (1, 11),
                                                         (1, 12),
                                                         (1, 13),
                                                         (2, 10),
                                                         (3, 10),
                                                         (3, 12),
                                                         (4, 10),
                                                         (4, 11),
                                                         (4, 12);

INSERT INTO Reservation (reservation_id, reservation_date, check_in_date, check_out_date, number_of_guests, total_price, status, guest_id, room_id) VALUES
                                                                                                                                                        (9,  '2024-01-15', '2024-02-01', '2024-02-07', 2, 850.00,  'completed', 1, 1),
                                                                                                                                                        (10, '2024-02-10', '2024-03-01', '2024-03-05', 2, 620.00,  'completed', 2, 4),
                                                                                                                                                        (11, '2024-03-20', '2024-04-10', '2024-04-15', 2, 950.00,  'completed', 3, 6),
                                                                                                                                                        (12, '2024-04-05', '2024-05-01', '2024-05-03', 4, 480.00,  'completed', 4, 12),
                                                                                                                                                        (13, '2024-05-12', '2024-06-01', '2024-06-10', 2, 1200.00, 'confirmed', 5, 8),
                                                                                                                                                        (14, '2024-06-18', '2024-07-15', '2024-07-20', 2, 750.00,  'completed', 6, 3),
                                                                                                                                                        (15, '2024-07-22', '2024-08-01', '2024-08-05', 4, 920.00,  'cancelled', 7, 13),
                                                                                                                                                        (16, '2024-08-30', '2024-09-10', '2024-09-15', 2, 680.00,  'cancelled', 8, 2),
                                                                                                                                                        (17, '2026-04-26', '2026-04-30', '2026-05-09', 1, 1755.00, 'cancelled', 5, 9),
                                                                                                                                                        (19, '2026-04-29', '2026-07-20', '2026-07-27', 1, 525.00,  'cancelled', 9, 2),
                                                                                                                                                        (20, '2026-04-29', '2026-09-20', '2026-09-29', 2, 675.00,  'cancelled', 9, 3),
                                                                                                                                                        (21, '2026-04-29', '2026-10-20', '2026-10-25', 1, 375.00,  'cancelled', 9, 2),
                                                                                                                                                        (24, '2026-04-29', '2026-12-20', '2026-12-27', 1, 1225.00, 'cancelled', 9, 5),
                                                                                                                                                        (25, '2026-04-29', '2026-05-30', '2026-06-07', 2, 1280.00, 'cancelled', 9, 3),
                                                                                                                                                        (26, '2026-04-29', '2026-04-30', '2026-05-07', 1, 1470.00, 'pending',   4, 2),
                                                                                                                                                        (27, '2026-04-29', '2026-07-07', '2026-07-17', 2, 1480.00, 'confirmed', 4, 5),
                                                                                                                                                        (28, '2026-04-29', '2026-05-01', '2026-05-10', 2, 3699.99, 'cancelled', 9, 8),
                                                                                                                                                        (29, '2026-04-29', '2026-09-01', '2026-09-10', 2, 2700.00, 'confirmed', 3, 12),
                                                                                                                                                        (30, '2026-05-03', '2026-05-15', '2026-05-20', 2, 2300.00, 'confirmed', 9, 11),
                                                                                                                                                        (31, '2026-05-06', '2026-05-07', '2026-05-17', 2, 2780.00, 'confirmed', 10, 8),
                                                                                                                                                        (33, '2026-05-06', '2026-05-20', '2026-05-25', 1, 1055.00, 'cancelled', 9, 5),
                                                                                                                                                        (34, '2026-05-07', '2026-05-26', '2026-05-31', 4, 2530.00, 'confirmed', 9, 10),
                                                                                                                                                        (35, '2026-05-07', '2026-05-30', '2026-06-02', 2, 540.00,  'pending',   9, 2);

INSERT INTO Reservation_Offer (reservation_id, offer_id, total_offer_price) VALUES
                                                                                (17, 1,  1080.00),
                                                                                (24, 2,  175.00),
                                                                                (24, 9,  280.00),
                                                                                (25, 3,  0.00),
                                                                                (25, 6,  0.00),
                                                                                (26, 4,  0.00),
                                                                                (26, 9,  0.00),
                                                                                (26, 13, 0.00),
                                                                                (27, 2,  0.00),
                                                                                (27, 6,  0.00),
                                                                                (27, 13, 0.00);

INSERT INTO Payment (payment_method, amount, payment_date, reservation_id) VALUES
                                                                               ('credit_card', 850.00,   '2024-02-01', 9),
                                                                               ('cash',        620.00,   '2024-03-01', 10),
                                                                               ('credit_card', 950.00,   '2024-04-10', 11),
                                                                               ('paypal',      480.00,   '2024-05-01', 12),
                                                                               ('credit_card', 1200.00,  '2024-06-01', 13),
                                                                               ('cash',        750.00,   '2024-07-15', 14),
                                                                               ('credit_card', 200.00,   '2026-04-29', 21),
                                                                               ('cash',        175.00,   '2026-04-29', 21),
                                                                               ('credit_card', 1225.00,  '2026-04-29', 24),
                                                                               ('credit_card', 1279.99,  '2026-04-29', 25),
                                                                               ('credit_card', 0.01,     '2026-04-29', 25),
                                                                               ('credit_card', 1469.99,  '2026-04-29', 26),
                                                                               ('credit_card', 0.01,     '2026-04-29', 26),
                                                                               ('credit_card', 3699.99,  '2026-04-29', 28),
                                                                               ('credit_card', 2700.00,  '2026-04-29', 29),
                                                                               ('credit_card', 2299.99,  '2026-05-03', 30),
                                                                               ('credit_card', 0.01,     '2026-05-03', 30),
                                                                               ('credit_card', 2780.00,  '2026-05-06', 31),
                                                                               ('credit_card', 1480.00,  '2026-05-06', 27),
                                                                               ('credit_card', 1055.00,  '2026-05-06', 33),
                                                                               ('credit_card', 2529.99,  '2026-05-07', 34),
                                                                               ('credit_card', 0.01,     '2026-05-07', 34);

INSERT INTO Cancellation (cancellation_date, refund_amount, reason, reservation_id) VALUES
                                                                                        ('2024-09-01', 340.00,   'Лични причини',                              16),
                                                                                        ('2026-04-26', 0.00,     'Откажано од корисник',                       17),
                                                                                        ('2026-04-27', 0.00,     'Откажано од корисник',                       15),
                                                                                        ('2026-04-29', 0.00,     'Поради работни обврски',                     19),
                                                                                        ('2026-04-29', 0.00,     'Неодложливи обврски поврзани со студирање',  20),
                                                                                        ('2026-04-29', 375.00,   'Поради работни обврски',                     21),
                                                                                        ('2026-04-29', 0.00,     'Неодложливи обврски поврзани со студирање',  26),
                                                                                        ('2026-04-29', 3699.99,  'Закажана операција',                         28),
                                                                                        ('2026-05-06', 1225.00,  'Поради работни обврски',                     24),
                                                                                        ('2026-05-07', 1280.00,  'Поради работни обврски',                     25),
                                                                                        ('2026-05-07', 1055.00,  'Неодложливи обврски поврзани со студирање',  33);

INSERT INTO Review (rating, comment, review_date, guest_id, reservation_id) VALUES
                                                                                (4, 'Многу убава соба, препорачувам!',        '2024-03-06', 2, 10),
                                                                                (5, 'Фантастично искуство, ќе се вратам!',    '2024-04-16', 3, 11),
                                                                                (3, 'Добро, но можеше подобро',               '2024-05-04', 4, 12),
                                                                                (5, 'Прекрасен хотел, со многу услуги!',      '2026-04-29', 1, 9);
-- =============================================
-- LuxStay Hotel Management System
-- 03_views.sql - Views and Materialized Views
-- =============================================

-- View: Guest Reservation History
CREATE VIEW public.guest_reservation_history AS
SELECT g.guest_id,
       (g.first_name::text || ' '::text) || g.last_name::text AS guest_name,
        g.nationality,
       count(r.reservation_id) AS total_reservations,
       sum(r.total_price) AS total_spent,
       avg(rv.rating) AS average_rating
FROM guest g
         LEFT JOIN reservation r ON g.guest_id = r.guest_id
         LEFT JOIN review rv ON r.reservation_id = rv.reservation_id
GROUP BY g.guest_id, g.first_name, g.last_name, g.nationality;

-- View: Reservation Details
CREATE VIEW public.reservation_details AS
SELECT r.reservation_id,
       (g.first_name::text || ' '::text) || g.last_name::text AS guest_name,
        rm.room_number,
       rt.type_name AS room_type,
       r.check_in_date,
       r.check_out_date,
       r.check_out_date - r.check_in_date AS duration,
       r.number_of_guests,
       r.total_price,
       r.status
FROM reservation r
         JOIN guest g ON r.guest_id = g.guest_id
         JOIN room rm ON r.room_id = rm.room_id
         JOIN room_type rt ON rm.room_type_id = rt.room_type_id;

-- Materialized View: Top Rooms
CREATE MATERIALIZED VIEW public.top_rooms AS
SELECT rt.type_name,
       rm.room_number,
       count(r.reservation_id) AS broj_rezervacii,
       sum(r.total_price) AS vkupni_prihodi,
       avg(r.check_out_date - r.check_in_date) AS prosecen_prestoj,
       avg(rv.rating) AS prosecen_rejting
FROM room_type rt
         JOIN room rm ON rt.room_type_id = rm.room_type_id
         LEFT JOIN reservation r ON rm.room_id = r.room_id
         LEFT JOIN review rv ON r.reservation_id = rv.reservation_id
GROUP BY rt.type_name, rm.room_number
ORDER BY (count(r.reservation_id)) DESC;
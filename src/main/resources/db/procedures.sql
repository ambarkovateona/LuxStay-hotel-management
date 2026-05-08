-- =============================================
-- LuxStay Hotel Management System
-- 05_procedures.sql - Procedures
-- =============================================

-- Procedure: Create Reservation
CREATE OR REPLACE PROCEDURE public.create_reservation(
    IN p_guest_id integer,
    IN p_room_id integer,
    IN p_check_in date,
    IN p_check_out date,
    IN p_guests integer,
    IN p_offer_id integer DEFAULT NULL
)
LANGUAGE plpgsql
AS $$
DECLARE
v_base_price DECIMAL(10,2);
    v_nights INTEGER;
    v_total DECIMAL(10,2);
    v_offer_price DECIMAL(10,2);
    v_reservation_id INTEGER;
BEGIN
    v_nights := p_check_out - p_check_in;

SELECT rt.base_price INTO v_base_price
FROM Room r
         JOIN Room_Type rt ON r.room_type_id = rt.room_type_id
WHERE r.room_id = p_room_id;

v_total := v_base_price * v_nights;

    IF p_offer_id IS NOT NULL THEN
SELECT price INTO v_offer_price
FROM Offer WHERE offer_id = p_offer_id;
v_total := v_total + (v_offer_price * v_nights * p_guests);
END IF;

INSERT INTO Reservation (
    reservation_date, check_in_date, check_out_date,
    number_of_guests, total_price, status, guest_id, room_id
) VALUES (
                     CURRENT_DATE, p_check_in, p_check_out,
                     p_guests, v_total, 'pending', p_guest_id, p_room_id
         ) RETURNING reservation_id INTO v_reservation_id;

IF p_offer_id IS NOT NULL THEN
        INSERT INTO Reservation_Offer (reservation_id, offer_id, total_offer_price)
        VALUES (v_reservation_id, p_offer_id, v_offer_price * v_nights * p_guests);
END IF;

    RAISE NOTICE 'Резервацијата е креирана со ID: %', v_reservation_id;
END;
$$;

-- Procedure: Generate Monthly Report
CREATE OR REPLACE PROCEDURE public.generate_monthly_report(
    IN p_month integer,
    IN p_year integer
)
LANGUAGE plpgsql
AS $$
DECLARE
v_total_reservations BIGINT;
    v_total_revenue NUMERIC;
    v_avg_stay NUMERIC;
    v_total_cancellations BIGINT;
    v_most_popular TEXT;
BEGIN
SELECT COUNT(*) INTO v_total_reservations
FROM Reservation
WHERE EXTRACT(MONTH FROM reservation_date) = p_month
  AND EXTRACT(YEAR FROM reservation_date) = p_year;

SELECT COALESCE(SUM(total_price), 0) INTO v_total_revenue
FROM Reservation
WHERE EXTRACT(MONTH FROM reservation_date) = p_month
  AND EXTRACT(YEAR FROM reservation_date) = p_year
  AND status = 'completed';

SELECT COALESCE(AVG(check_out_date - check_in_date), 0) INTO v_avg_stay
FROM Reservation
WHERE EXTRACT(MONTH FROM reservation_date) = p_month
  AND EXTRACT(YEAR FROM reservation_date) = p_year;

SELECT COUNT(*) INTO v_total_cancellations
FROM Cancellation c
         JOIN Reservation r ON c.reservation_id = r.reservation_id
WHERE EXTRACT(MONTH FROM c.cancellation_date) = p_month
  AND EXTRACT(YEAR FROM c.cancellation_date) = p_year;

SELECT rt.type_name INTO v_most_popular
FROM Room_Type rt
         JOIN Room rm ON rt.room_type_id = rm.room_type_id
         JOIN Reservation r ON rm.room_id = r.room_id
WHERE EXTRACT(MONTH FROM r.reservation_date) = p_month
  AND EXTRACT(YEAR FROM r.reservation_date) = p_year
GROUP BY rt.type_name
ORDER BY COUNT(*) DESC
    LIMIT 1;

INSERT INTO Monthly_Report (
    report_date, month, year,
    total_reservations, total_revenue,
    avg_stay, total_cancellations,
    most_popular_room_type
) VALUES (
                     CURRENT_DATE, p_month, p_year,
                     v_total_reservations, v_total_revenue,
                     v_avg_stay, v_total_cancellations,
                     v_most_popular
         );

RAISE NOTICE 'Извештајот за %/% е успешно генериран!', p_month, p_year;
END;
$$;
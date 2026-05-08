-- =============================================
-- LuxStay Hotel Management System
-- 04_functions.sql - Functions
-- =============================================

-- Function: Calculate Total Price (for trigger)
CREATE OR REPLACE FUNCTION public.calculate_total_price()
RETURNS trigger
LANGUAGE plpgsql
AS $$
DECLARE
v_base_price DECIMAL(10,2);
    v_nights INTEGER;
BEGIN
SELECT rt.base_price INTO v_base_price
FROM Room r
         JOIN Room_Type rt ON r.room_type_id = rt.room_type_id
WHERE r.room_id = NEW.room_id;

v_nights := NEW.check_out_date - NEW.check_in_date;
    NEW.total_price := v_base_price * v_nights;

RETURN NEW;
END;
$$;

-- Function: Update Total Price (for trigger)
CREATE OR REPLACE FUNCTION public.update_total_price()
RETURNS trigger
LANGUAGE plpgsql
AS $$
DECLARE
v_base_price DECIMAL(10,2);
    v_nights INTEGER;
    v_offer_total DECIMAL(10,2);
BEGIN
SELECT rt.base_price, r.check_out_date - r.check_in_date
INTO v_base_price, v_nights
FROM Reservation r
         JOIN Room rm ON r.room_id = rm.room_id
         JOIN Room_Type rt ON rm.room_type_id = rt.room_type_id
WHERE r.reservation_id = NEW.reservation_id;

SELECT COALESCE(SUM(
                        CASE WHEN o.is_fixed THEN o.price
                             ELSE o.price * v_nights
                            END
                ), 0) INTO v_offer_total
FROM Reservation_Offer ro
         JOIN Offer o ON ro.offer_id = o.offer_id
WHERE ro.reservation_id = NEW.reservation_id;

UPDATE Reservation
SET total_price = (v_base_price * v_nights) + v_offer_total
WHERE reservation_id = NEW.reservation_id;

RETURN NEW;
END;
$$;

-- Function: Update Reservation Status (for trigger)
CREATE OR REPLACE FUNCTION public.update_reservation_status()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
UPDATE Reservation
SET status = 'cancelled'
WHERE reservation_id = NEW.reservation_id;
RETURN NEW;
END;
$$;

-- Function: Check Room Availability (for trigger)
CREATE OR REPLACE FUNCTION public.check_room_availability()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM Reservation
        WHERE room_id = NEW.room_id
        AND status NOT IN ('cancelled')
        AND reservation_id != NEW.reservation_id
        AND (
            NEW.check_in_date < check_out_date
            AND
            NEW.check_out_date > check_in_date
        )
    ) THEN
        RAISE EXCEPTION 'Собата не е достапна за овој период!';
END IF;
RETURN NEW;
END;
$$;

-- Function: Refresh Top Rooms (for trigger)
CREATE OR REPLACE FUNCTION public.refresh_top_rooms()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
    REFRESH MATERIALIZED VIEW top_rooms;
RETURN NEW;
END;
$$;

-- Function: Hotel Revenue Report
CREATE OR REPLACE FUNCTION public.hotel_revenue_report()
RETURNS TABLE(
    tip_na_soba text,
    broj_rezervacii bigint,
    vkupni_prihodi numeric,
    prosecna_cena numeric,
    prosecen_prestoj numeric,
    procent_od_vkupno numeric,
    rank_prihodi bigint
)
LANGUAGE plpgsql
AS $$
BEGIN
RETURN QUERY
SELECT
    rt.type_name::TEXT,
        COUNT(r.reservation_id)::BIGINT,
        COALESCE(SUM(r.total_price), 0)::NUMERIC,
        COALESCE(AVG(r.total_price), 0)::NUMERIC,
        COALESCE(AVG(r.check_out_date - r.check_in_date), 0)::NUMERIC,
        COALESCE(SUM(r.total_price) * 100.0 /
                 NULLIF(SUM(SUM(r.total_price)) OVER (), 0), 0)::NUMERIC,
        RANK() OVER (ORDER BY COALESCE(SUM(r.total_price), 0) DESC)::BIGINT
FROM Room_Type rt
         LEFT JOIN Room rm ON rt.room_type_id = rm.room_type_id
         LEFT JOIN Reservation r ON rm.room_id = r.room_id
    AND r.status = 'completed'
GROUP BY rt.type_name
ORDER BY SUM(r.total_price) DESC NULLS LAST;
END;
$$;

-- Function: Guest Analysis Report
CREATE OR REPLACE FUNCTION public.guest_analysis_report()
RETURNS TABLE(
    ime text,
    nacionalnost text,
    broj_rezervacii bigint,
    vkupno_potrosheno numeric,
    vkupno_noki bigint,
    prosecen_rejting numeric,
    posledna_rezervacija date,
    kategorija text,
    rank_gosti bigint
)
LANGUAGE plpgsql
AS $$
BEGIN
RETURN QUERY
    WITH guest_stats AS (
        SELECT
            g.guest_id,
            g.first_name || ' ' || g.last_name AS full_name,
            g.nationality,
            COUNT(r.reservation_id) AS total_reservations,
            COALESCE(SUM(r.total_price), 0) AS total_spent,
            COALESCE(SUM(r.check_out_date - r.check_in_date), 0) AS total_nights,
            COALESCE(AVG(rv.rating), 0) AS avg_rating,
            MAX(r.check_in_date) AS last_reservation
        FROM Guest g
        LEFT JOIN Reservation r ON g.guest_id = r.guest_id
        LEFT JOIN Review rv ON r.reservation_id = rv.reservation_id
        GROUP BY g.guest_id, g.first_name, g.last_name, g.nationality
    )
SELECT
    gs.full_name::TEXT,
        gs.nationality::TEXT,
        gs.total_reservations::BIGINT,
        gs.total_spent::NUMERIC,
        gs.total_nights::BIGINT,
        ROUND(gs.avg_rating::NUMERIC, 2),
    gs.last_reservation,
    CASE
        WHEN gs.total_reservations >= 3 THEN 'VIP Гостин'
        WHEN gs.total_reservations = 2 THEN 'Редовен гостин'
        ELSE 'Нов гостин'
        END::TEXT,
        RANK() OVER (ORDER BY gs.total_spent DESC)::BIGINT
FROM guest_stats gs
ORDER BY gs.total_spent DESC;
END;
$$;
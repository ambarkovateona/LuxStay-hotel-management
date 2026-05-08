-- =============================================
-- LuxStay Hotel Management System
-- 06_triggers.sql - Triggers
-- =============================================

-- Trigger: Calculate Price (BEFORE INSERT on Reservation)
CREATE TRIGGER trg_calculate_price
    BEFORE INSERT ON public.reservation
    FOR EACH ROW
    EXECUTE FUNCTION public.calculate_total_price();

-- Trigger: Check Availability (BEFORE INSERT OR UPDATE on Reservation)
CREATE TRIGGER trg_check_availability
    BEFORE INSERT OR UPDATE ON public.reservation
                         FOR EACH ROW
                         EXECUTE FUNCTION public.check_room_availability();

-- Trigger: Refresh Top Rooms (AFTER INSERT/UPDATE/DELETE on Reservation)
CREATE TRIGGER trg_refresh_top_rooms
    AFTER INSERT OR DELETE OR UPDATE ON public.reservation
    FOR EACH STATEMENT
    EXECUTE FUNCTION public.refresh_top_rooms();

-- Trigger: Update Total Price (AFTER INSERT on Reservation_Offer)
CREATE TRIGGER trg_update_total_price
    AFTER INSERT ON public.reservation_offer
    FOR EACH ROW
    EXECUTE FUNCTION public.update_total_price();

-- Trigger: Cancellation Status (AFTER INSERT on Cancellation)
CREATE TRIGGER trg_cancellation_status
    AFTER INSERT ON public.cancellation
    FOR EACH ROW
    EXECUTE FUNCTION public.update_reservation_status();
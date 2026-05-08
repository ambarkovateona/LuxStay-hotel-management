-- =============================================
-- LuxStay Hotel Management System
-- 02_indexes.sql - Indexes
-- =============================================

CREATE INDEX idx_reservation_guest ON public.reservation (guest_id);
CREATE INDEX idx_reservation_room ON public.reservation (room_id);
CREATE INDEX idx_reservation_dates ON public.reservation (check_in_date, check_out_date);
CREATE INDEX idx_review_reservation ON public.review (reservation_id);
CREATE INDEX idx_payment_reservation ON public.payment (reservation_id);
CREATE INDEX idx_offer_type ON public.offer (offer_type);
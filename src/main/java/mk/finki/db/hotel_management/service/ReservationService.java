package mk.finki.db.hotel_management.service;

import mk.finki.db.hotel_management.model.Cancellation;
import mk.finki.db.hotel_management.model.Reservation;
import mk.finki.db.hotel_management.model.dto.ReservationDto;
import java.util.List;

public interface ReservationService {
    Reservation createReservation(Integer guestId, ReservationDto dto);
    List<Reservation> findByGuestId(Integer guestId);
    List<Reservation> findAll();
    Cancellation cancelReservation(Integer reservationId, String reason);
    void updateStatus(Integer reservationId, String status);
}
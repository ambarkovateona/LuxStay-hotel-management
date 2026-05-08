package mk.finki.db.hotel_management.service;

import mk.finki.db.hotel_management.model.Cancellation;
import mk.finki.db.hotel_management.model.Reservation;

public interface CancellationService {
    Cancellation cancelReservation(Integer reservationId, String reason);
    double calculateRefund(Reservation reservation);
}
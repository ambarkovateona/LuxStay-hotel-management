package mk.finki.db.hotel_management.service;

import mk.finki.db.hotel_management.model.Payment;

import java.util.List;

public interface PaymentService {
    List<Payment> findByReservationId(Integer reservationId);
    Payment processPayment(Integer reservationId, String paymentMethod, Double amount);
    double getPaidAmount(Integer reservationId);
    double getRemainingAmount(Integer reservationId);
}
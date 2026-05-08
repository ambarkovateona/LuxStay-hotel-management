package mk.finki.db.hotel_management.service.impl;

import jakarta.transaction.Transactional;
import mk.finki.db.hotel_management.model.Payment;
import mk.finki.db.hotel_management.model.Reservation;
import mk.finki.db.hotel_management.model.enums.ReservationStatus;
import mk.finki.db.hotel_management.repository.PaymentRepository;
import mk.finki.db.hotel_management.repository.ReservationRepository;
import mk.finki.db.hotel_management.service.PaymentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              ReservationRepository reservationRepository) {
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Payment> findByReservationId(Integer reservationId) {
        return paymentRepository.findByReservationReservationId(reservationId);
    }

    @Override
    public double getPaidAmount(Integer reservationId) {
        double paid = paymentRepository.findByReservationReservationId(reservationId)
                .stream().mapToDouble(Payment::getAmount).sum();
        return Math.round(paid * 100.0) / 100.0;
    }

    @Override
    public double getRemainingAmount(Integer reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Резервацијата не постои"));
        double remaining = reservation.getTotalPrice() - getPaidAmount(reservationId);
        return Math.round(remaining * 100.0) / 100.0;
    }

    @Override
    @Transactional
    public Payment processPayment(Integer reservationId, String paymentMethod, Double amount) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Резервацијата не постои"));

        List<Payment> existing = paymentRepository.findByReservationReservationId(reservationId);

        if (existing.size() >= 2) {
            throw new RuntimeException("Максимум 2 рати се дозволени!");
        }

        double roundedAmount = Math.round(amount * 100.0) / 100.0;

        double remaining = getRemainingAmount(reservationId);
        if (roundedAmount > remaining ) {
            throw new RuntimeException("Износот е поголем од преостанатата сума!");
        }

        Payment payment = new Payment();
        payment.setReservation(reservation);
        payment.setPaymentMethod(paymentMethod);
        payment.setAmount(roundedAmount);
        payment.setPaymentDate(LocalDate.now());
        Payment saved = paymentRepository.save(payment);

        double newPaid = Math.round(getPaidAmount(reservationId) * 100.0) / 100.0;
        double total = Math.round(reservation.getTotalPrice() * 100.0) / 100.0;

        if (newPaid >= total) {
            reservation.setStatus(ReservationStatus.confirmed);
            reservationRepository.save(reservation);
        }

        return saved;
    }
}
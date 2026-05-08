package mk.finki.db.hotel_management.service.impl;

import jakarta.transaction.Transactional;
import mk.finki.db.hotel_management.model.Cancellation;
import mk.finki.db.hotel_management.model.Reservation;
import mk.finki.db.hotel_management.repository.CancellationRepository;
import mk.finki.db.hotel_management.repository.ReservationRepository;
import mk.finki.db.hotel_management.service.CancellationService;
import mk.finki.db.hotel_management.service.PaymentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class CancellationServiceImpl implements CancellationService {

    private final CancellationRepository cancellationRepository;
    private final ReservationRepository reservationRepository;
    private final PaymentService paymentService;

    public CancellationServiceImpl(CancellationRepository cancellationRepository,
                                   ReservationRepository reservationRepository,
                                   PaymentService paymentService) {
        this.cancellationRepository = cancellationRepository;
        this.reservationRepository = reservationRepository;
        this.paymentService = paymentService;
    }

    @Override
    public double calculateRefund(Reservation reservation) {
        long daysUntilCheckIn = ChronoUnit.DAYS.between(LocalDate.now(), reservation.getCheckInDate());
        double paid = paymentService.getPaidAmount(reservation.getReservationId());

        if (daysUntilCheckIn > 7) {
            return paid; // 100%
        } else if (daysUntilCheckIn >= 3) {
            return paid * 0.5; // 50%
        } else {
            return 0.0; // 0%
        }
    }

    @Override
    @Transactional
    public Cancellation cancelReservation(Integer reservationId, String reason) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Резервацијата не постои"));

        double refund = calculateRefund(reservation);

        Cancellation cancellation = new Cancellation();
        cancellation.setReservation(reservation);
        cancellation.setCancellationDate(LocalDate.now());
        cancellation.setRefundAmount(refund);
        cancellation.setReason(reason);

        // Trigger ќе го смени статусот во cancelled автоматски
        return cancellationRepository.save(cancellation);
    }
}
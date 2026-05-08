package mk.finki.db.hotel_management.repository;

import mk.finki.db.hotel_management.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByReservationReservationId(Integer reservationId);
}

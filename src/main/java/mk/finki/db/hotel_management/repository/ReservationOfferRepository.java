package mk.finki.db.hotel_management.repository;

import mk.finki.db.hotel_management.model.ReservationOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationOfferRepository extends JpaRepository<ReservationOffer, Long> {

}

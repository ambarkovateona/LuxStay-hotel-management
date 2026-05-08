package mk.finki.db.hotel_management.repository;

import mk.finki.db.hotel_management.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByGuestGuestId(Integer guestId);
    Optional<Review> findByReservationReservationId(Integer reservationId);
}

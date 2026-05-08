package mk.finki.db.hotel_management.repository;

import mk.finki.db.hotel_management.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Integer> {
    Optional<Guest> findByUserUserId(Integer userId);
    Optional<Guest> findByEmail(String email);
}

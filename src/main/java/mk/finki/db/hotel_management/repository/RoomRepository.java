package mk.finki.db.hotel_management.repository;

import mk.finki.db.hotel_management.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    List<Room> findByStatus(String status);

    @Query("""
        SELECT r FROM Room r
        WHERE r.status = 'available'
        AND r.roomId NOT IN (
            SELECT res.room.roomId FROM Reservation res
            WHERE res.status != 'cancelled'
            AND res.checkInDate < :checkOut
            AND res.checkOutDate > :checkIn
        )
    """)
    List<Room> findAvailableRooms(LocalDate checkIn, LocalDate checkOut);
}
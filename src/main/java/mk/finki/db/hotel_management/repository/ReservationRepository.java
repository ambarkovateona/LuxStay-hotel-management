package mk.finki.db.hotel_management.repository;

import mk.finki.db.hotel_management.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByGuestGuestId(Integer guestId);

    @Query(value = "SELECT * FROM Reservation_Details WHERE guest_name = :guestName",
            nativeQuery = true)
    List<Object[]> findFromReservationDetails(String guestName);

    @Query(value = "SELECT * FROM Reservation_Details", nativeQuery = true)
    List<Object[]> findAllFromReservationDetails();

    @Query(value = "SELECT * FROM hotel_revenue_report()", nativeQuery = true)
    List<Object[]> getHotelRevenueReport();

    @Query(value = "SELECT * FROM guest_analysis_report()", nativeQuery = true)
    List<Object[]> getGuestAnalysisReport();

    @Query(value = "SELECT * FROM Monthly_Report ORDER BY year, month",
            nativeQuery = true)
    List<Object[]> getMonthlyReport();


    @Query(value = "SELECT * FROM Guest_Reservation_History", nativeQuery = true)
    List<Object[]> findAllGuestHistory();

    @Query(value = "SELECT * FROM top_rooms LIMIT 5", nativeQuery = true)
    List<Object[]> getTopRooms();
}
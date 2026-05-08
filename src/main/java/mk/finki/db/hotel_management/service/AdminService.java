package mk.finki.db.hotel_management.service;

import java.util.List;

public interface AdminService {
    List<Object[]> getRevenueReport();
    List<Object[]> getGuestReport();
    List<Object[]> getMonthlyReport();
    List<Object[]> getTopRooms();
    List<Object[]> getReservationDetails();
    List<Object[]> getGuestHistory();
}
package mk.finki.db.hotel_management.service.impl;

import mk.finki.db.hotel_management.repository.ReservationRepository;
import mk.finki.db.hotel_management.service.AdminService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final ReservationRepository reservationRepository;

    public AdminServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Object[]> getRevenueReport() {
        return reservationRepository.getHotelRevenueReport();
    }

    @Override
    public List<Object[]> getGuestReport() {
        return reservationRepository.getGuestAnalysisReport();
    }

    @Override
    public List<Object[]> getMonthlyReport() {
        return reservationRepository.getMonthlyReport();
    }

    @Override
    public List<Object[]> getTopRooms() {
        return reservationRepository.getTopRooms();
    }

    @Override
    public List<Object[]> getReservationDetails() {
        return reservationRepository.findAllFromReservationDetails();
    }

    @Override
    public List<Object[]> getGuestHistory() {
        return reservationRepository.findAllGuestHistory();
    }
}
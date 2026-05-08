package mk.finki.db.hotel_management.web;

import mk.finki.db.hotel_management.model.Guest;
import mk.finki.db.hotel_management.model.Reservation;
import mk.finki.db.hotel_management.model.enums.ReservationStatus;
import mk.finki.db.hotel_management.service.ReservationService;
import mk.finki.db.hotel_management.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final ReservationService reservationService;
    private final UserService userService;

    public DashboardController(ReservationService reservationService,
                               UserService userService) {
        this.reservationService = reservationService;
        this.userService = userService;
    }

    @GetMapping
    public String dashboard(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        Guest guest = userService.findGuestByUsername(userDetails.getUsername());

        List<Reservation> reservations =
                reservationService.findByGuestId(guest.getGuestId());


        long activeReservations = reservations.stream()
                .filter(r -> r.getStatus() == ReservationStatus.confirmed
                        || r.getStatus() == ReservationStatus.pending)
                .count();


        long completedReservations = reservations.stream()
                .filter(r -> r.getStatus() == ReservationStatus.completed)
                .count();

        model.addAttribute("guest", guest);
        model.addAttribute("reservations", reservations);
        model.addAttribute("activeReservations", activeReservations);
        model.addAttribute("completedReservations", completedReservations);

        return "dashboard";
    }
}
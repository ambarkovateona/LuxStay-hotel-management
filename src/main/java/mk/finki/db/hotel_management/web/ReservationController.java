package mk.finki.db.hotel_management.web;

import jakarta.validation.Valid;
import mk.finki.db.hotel_management.model.Reservation;
import mk.finki.db.hotel_management.model.dto.ReservationDto;
import mk.finki.db.hotel_management.service.ReservationService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import mk.finki.db.hotel_management.service.UserService;
import mk.finki.db.hotel_management.model.User;
import mk.finki.db.hotel_management.model.Guest;
import mk.finki.db.hotel_management.repository.GuestRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import mk.finki.db.hotel_management.model.Cancellation;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final UserService userService;


    public ReservationController(ReservationService reservationService,
                                 UserService userService
                                ) {
        this.reservationService = reservationService;
        this.userService = userService;

    }

    @PostMapping("/create")
    public String createReservation(
            @ModelAttribute ReservationDto reservationDto,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes) {

        try {

            Guest guest = userService.findGuestByUsername(userDetails.getUsername());

            if (guest == null) {
                redirectAttributes.addFlashAttribute("error", "Guest not found");
                return "redirect:/rooms";
            }

            Reservation reservation = reservationService.createReservation(
                    guest.getGuestId(),
                    reservationDto
            );

            redirectAttributes.addFlashAttribute("success", "Reservation created successfully!");

            return "redirect:/dashboard";

        } catch (Exception e) {

            e.printStackTrace();

            redirectAttributes.addFlashAttribute("error", e.getMessage());

            return "redirect:/rooms";
        }
    }

    @PostMapping("/cancel/{reservationId}")
    public String cancelReservation(@PathVariable Integer reservationId,
                                    @RequestParam String reason,
                                    RedirectAttributes redirectAttributes) {
        try {
            Cancellation cancellation = reservationService.cancelReservation(reservationId, reason);
            double refund = cancellation.getRefundAmount();

            if (refund > 0) {
                redirectAttributes.addFlashAttribute("success",
                        "Резервацијата е откажана. Refund: " + refund + " €");
            } else {
                redirectAttributes.addFlashAttribute("success",
                        "Резервацијата е откажана. Нема refund.");
            }
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/dashboard";
    }
}
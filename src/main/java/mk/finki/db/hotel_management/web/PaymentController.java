package mk.finki.db.hotel_management.web;

import mk.finki.db.hotel_management.model.Reservation;
import mk.finki.db.hotel_management.repository.ReservationRepository;
import mk.finki.db.hotel_management.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final ReservationRepository reservationRepository;

    public PaymentController(PaymentService paymentService,
                             ReservationRepository reservationRepository) {
        this.paymentService = paymentService;
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/pay/{reservationId}")
    public String payPage(@PathVariable Integer reservationId, Model model) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Резервацијата не постои"));

        model.addAttribute("reservation", reservation);
        model.addAttribute("paid", paymentService.getPaidAmount(reservationId));
        model.addAttribute("remaining", paymentService.getRemainingAmount(reservationId));
        model.addAttribute("paymentCount", paymentService.findByReservationId(reservationId).size());
        return "payment";
    }

    @PostMapping("/pay/{reservationId}")
    public String processPayment(@PathVariable Integer reservationId,
                                 @RequestParam String paymentMethod,
                                 @RequestParam Double amount,
                                 RedirectAttributes redirectAttributes) {
        try {
            paymentService.processPayment(reservationId, paymentMethod, amount);
            redirectAttributes.addFlashAttribute("success", "Плаќањето е успешно!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/payments/pay/" + reservationId;
        }
        return "redirect:/dashboard";
    }
}
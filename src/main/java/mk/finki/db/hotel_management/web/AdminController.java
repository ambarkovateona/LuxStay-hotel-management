package mk.finki.db.hotel_management.web;

import mk.finki.db.hotel_management.service.ReservationService;
import mk.finki.db.hotel_management.service.AdminService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final ReservationService reservationService;
    private final AdminService adminService;

    public AdminController(ReservationService reservationService,
                           AdminService adminService) {
        this.reservationService = reservationService;
        this.adminService = adminService;
    }

    @GetMapping
    public String adminDashboard(Model model) {
        model.addAttribute("reservationDetails", adminService.getReservationDetails());
        model.addAttribute("topRooms", adminService.getTopRooms());
        return "admin/dashboard";
    }

    @GetMapping("/reports")
    public String reports(Model model) {
        model.addAttribute("revenueReport", adminService.getRevenueReport());
        model.addAttribute("guestReport", adminService.getGuestReport());
        model.addAttribute("monthlyReport", adminService.getMonthlyReport());
        return "admin/reports";
    }

    @GetMapping("/guests")
    public String guests(Model model) {
        model.addAttribute("guestHistory", adminService.getGuestHistory());
        return "admin/guests";
    }

    @PostMapping("/reservations/{id}/status")
    public String updateStatus(@PathVariable Integer id,
                               @RequestParam String status,
                               RedirectAttributes redirectAttributes) {
        try {
            reservationService.updateStatus(id, status);
            redirectAttributes.addFlashAttribute("success", "Статусот е успешно променет!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin";
    }
}
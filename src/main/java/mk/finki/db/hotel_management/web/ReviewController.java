package mk.finki.db.hotel_management.web;


import mk.finki.db.hotel_management.model.Guest;
import mk.finki.db.hotel_management.service.ReviewService;
import mk.finki.db.hotel_management.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    public ReviewController(ReviewService reviewService, UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @PostMapping("/add/{reservationId}")
    public String addReview(@PathVariable Integer reservationId,
                            @RequestParam Integer rating,
                            @RequestParam String comment,
                            @AuthenticationPrincipal UserDetails userDetails,
                            RedirectAttributes redirectAttributes) {
        try {
            Guest guest = userService.findGuestByUsername(userDetails.getUsername());
            reviewService.addReview(reservationId, guest.getGuestId(), rating, comment);
            redirectAttributes.addFlashAttribute("success", "Рецензијата е успешно додадена!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/dashboard";
    }
}
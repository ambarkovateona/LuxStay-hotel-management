package mk.finki.db.hotel_management.service.impl;

import jakarta.transaction.Transactional;
import mk.finki.db.hotel_management.model.Guest;
import mk.finki.db.hotel_management.model.Reservation;
import mk.finki.db.hotel_management.model.Review;
import mk.finki.db.hotel_management.model.enums.ReservationStatus;
import mk.finki.db.hotel_management.repository.GuestRepository;
import mk.finki.db.hotel_management.repository.ReservationRepository;
import mk.finki.db.hotel_management.repository.ReviewRepository;
import mk.finki.db.hotel_management.service.ReviewService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             ReservationRepository reservationRepository,
                             GuestRepository guestRepository) {
        this.reviewRepository = reviewRepository;
        this.reservationRepository = reservationRepository;
        this.guestRepository = guestRepository;
    }

    @Override
    @Transactional
    public Review addReview(Integer reservationId, Integer guestId, Integer rating, String comment) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Резервацијата не постои"));

        if (reservation.getStatus() != ReservationStatus.completed) {
            throw new RuntimeException("Можете да оставите рецензија само за завршени резервации");
        }

        if (hasReview(reservationId)) {
            throw new RuntimeException("Веќе оставивте рецензија за оваа резервација");
        }

        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new RuntimeException("Гостинот не постои"));

        Review review = new Review();
        review.setReservation(reservation);
        review.setGuest(guest);
        review.setRating(rating);
        review.setComment(comment);
        review.setReviewDate(LocalDate.now());

        return reviewRepository.save(review);
    }

    @Override
    public boolean hasReview(Integer reservationId) {
        return reviewRepository.findByReservationReservationId(reservationId).isPresent();
    }
}

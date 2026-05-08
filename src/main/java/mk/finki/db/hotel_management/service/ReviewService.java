package mk.finki.db.hotel_management.service;

import mk.finki.db.hotel_management.model.Review;

public interface ReviewService {
    Review addReview(Integer reservationId, Integer guestId, Integer rating, String comment);
    boolean hasReview(Integer reservationId);
}
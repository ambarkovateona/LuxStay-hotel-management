package mk.finki.db.hotel_management.model;

import jakarta.persistence.*;

@Entity
@Table(name = "reservation_offer")
public class ReservationOffer {

    @EmbeddedId
    private ReservationOfferId id = new ReservationOfferId(); // ВАЖНО: иницијализирај го!

    @ManyToOne
    @MapsId("reservationId")
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne
    @MapsId("offerId")
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @Column(name = "total_offer_price")
    private Double totalOfferPrice;

    public ReservationOfferId getId() {
        return id;
    }

    public void setId(ReservationOfferId id) {
        this.id = id;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Double getTotalOfferPrice() {
        return totalOfferPrice;
    }

    public void setTotalOfferPrice(Double totalOfferPrice) {
        this.totalOfferPrice = totalOfferPrice;
    }
}
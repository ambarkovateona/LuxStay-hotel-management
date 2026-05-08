package mk.finki.db.hotel_management.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ReservationOfferId implements Serializable {

    private Integer reservationId;
    private Integer offerId;

    public ReservationOfferId() {}

    public ReservationOfferId(Integer reservationId, Integer offerId) {
        this.reservationId = reservationId;
        this.offerId = offerId;
    }

    public Integer getReservationId() { return reservationId; }
    public void setReservationId(Integer reservationId) { this.reservationId = reservationId; }

    public Integer getOfferId() { return offerId; }
    public void setOfferId(Integer offerId) { this.offerId = offerId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationOfferId)) return false;
        ReservationOfferId that = (ReservationOfferId) o;
        return Objects.equals(reservationId, that.reservationId) &&
                Objects.equals(offerId, that.offerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId, offerId);
    }
}
package mk.finki.db.hotel_management.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cancellation")
public class Cancellation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cancellation_id")
    private Integer cancellationId;

    @Column(name = "cancellation_date", nullable = false)
    private LocalDate cancellationDate;

    @Column(name = "refund_amount", nullable = false)
    private Double refundAmount;

    @Column(name = "reason")
    private String reason;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
}
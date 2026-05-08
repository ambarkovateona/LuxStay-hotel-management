package mk.finki.db.hotel_management.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "service")
public class Service {

    @Id
    @Column(name = "offer_id")
    private Integer offerId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @Column(name = "service_type")
    private String serviceType;

    @ManyToMany(mappedBy = "services")
    private List<Package> packages;
}
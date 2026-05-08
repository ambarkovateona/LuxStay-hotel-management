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
@Table(name = "package")
public class Package {

    @Id
    @Column(name = "offer_id")
    private Integer offerId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @ManyToMany
    @JoinTable(
            name = "package_service",
            joinColumns = @JoinColumn(name = "package_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Service> services;
}
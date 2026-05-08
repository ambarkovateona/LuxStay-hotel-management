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
@Table(name = "room_amenity")
public class RoomAmenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "amenity_id")
    private Integer amenityId;

    @Column(name = "amenity_name", nullable = false)
    private String amenityName;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "amenities")
    private List<RoomType> roomTypes;
}
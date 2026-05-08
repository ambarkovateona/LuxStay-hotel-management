package mk.finki.db.hotel_management.repository;

import mk.finki.db.hotel_management.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {
    List<Offer> findByOfferType(String offerType);
    @Query(value = "SELECT ps.service_id FROM Package_Service ps WHERE ps.package_id = :packageId",
            nativeQuery = true)
    List<Integer> findServiceIdsByPackageId(Integer packageId);
}
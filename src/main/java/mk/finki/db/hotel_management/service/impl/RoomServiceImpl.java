package mk.finki.db.hotel_management.service.impl;

import mk.finki.db.hotel_management.model.Offer;
import mk.finki.db.hotel_management.model.Room;
import mk.finki.db.hotel_management.repository.OfferRepository;
import mk.finki.db.hotel_management.repository.RoomRepository;
import mk.finki.db.hotel_management.service.RoomService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final OfferRepository offerRepository;

    public RoomServiceImpl(RoomRepository roomRepository,
                           OfferRepository offerRepository) {
        this.roomRepository = roomRepository;
        this.offerRepository = offerRepository;
    }

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> findAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
        return roomRepository.findAvailableRooms(checkIn, checkOut);
    }

    @Override
    public Room findById(Integer id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Собата не е пронајдена"));
    }

    @Override
    public List<Offer> findAllPackages() {
        return offerRepository.findByOfferType("package");
    }



}
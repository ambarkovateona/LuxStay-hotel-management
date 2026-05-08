package mk.finki.db.hotel_management.service;

import mk.finki.db.hotel_management.model.Room;
import mk.finki.db.hotel_management.model.Offer;
import mk.finki.db.hotel_management.model.Service;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {
    List<Room> findAll();
    List<Room> findAvailableRooms(LocalDate checkIn, LocalDate checkOut);
    Room findById(Integer id);
    List<Offer> findAllPackages();

}
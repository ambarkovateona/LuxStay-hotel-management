package mk.finki.db.hotel_management.web;

import mk.finki.db.hotel_management.model.Offer;
import mk.finki.db.hotel_management.model.Room;
import mk.finki.db.hotel_management.model.dto.ReservationDto;
import mk.finki.db.hotel_management.repository.OfferRepository;
import mk.finki.db.hotel_management.service.RoomService;
import mk.finki.db.hotel_management.service.ServiceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;
    private final ServiceService serviceService;
    private final OfferRepository offerRepository;

    public RoomController(RoomService roomService,
                          ServiceService serviceService,
                          OfferRepository offerRepository) {
        this.roomService = roomService;
        this.serviceService = serviceService;
        this.offerRepository = offerRepository;
    }

    @GetMapping
    public String rooms(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
            Model model) {

        List<Room> rooms;
        if (checkIn != null && checkOut != null) {
            rooms = roomService.findAvailableRooms(checkIn, checkOut);
        } else {
            rooms = roomService.findAll();
        }

        model.addAttribute("rooms", rooms);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
        return "rooms";
    }

    @GetMapping("/{id}/reserve")
    public String reservePage(@PathVariable Integer id,
                              @RequestParam(required = false)
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
                              @RequestParam(required = false)
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
                              Model model) {

        List<Offer> packages = roomService.findAllPackages();

        // Map: packageId -> list of serviceIds кои се дел од пакетот
        Map<Integer, List<Integer>> packageServicesMap = new HashMap<>();
        for (Offer pkg : packages) {
            List<Integer> serviceIds = offerRepository.findServiceIdsByPackageId(pkg.getOfferId());
            packageServicesMap.put(pkg.getOfferId(), serviceIds);
        }

        model.addAttribute("room", roomService.findById(id));
        model.addAttribute("packages", packages);
        model.addAttribute("services", serviceService.findAllServices());
        model.addAttribute("packageServicesMap", packageServicesMap);

        ReservationDto res = new ReservationDto();
        res.setRoomId(id);
        model.addAttribute("reservationDto", res);

        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
        return "reserve";
    }
}
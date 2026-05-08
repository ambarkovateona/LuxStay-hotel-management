package mk.finki.db.hotel_management.service.impl;

import mk.finki.db.hotel_management.model.*;
import mk.finki.db.hotel_management.model.dto.ReservationDto;
import mk.finki.db.hotel_management.model.enums.ReservationStatus;
import mk.finki.db.hotel_management.repository.*;
import mk.finki.db.hotel_management.service.PaymentService;
import mk.finki.db.hotel_management.service.ReservationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final OfferRepository offerRepository;
    private final CancellationRepository cancellationRepository;
    private final ReservationOfferRepository reservationOfferRepository;
    private final PaymentService paymentService;

    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  RoomRepository roomRepository,
                                  GuestRepository guestRepository,
                                  OfferRepository offerRepository,
                                  CancellationRepository cancellationRepository,
                                  ReservationOfferRepository reservationOfferRepository,
                                  PaymentService paymentService) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.offerRepository = offerRepository;
        this.cancellationRepository = cancellationRepository;
        this.reservationOfferRepository = reservationOfferRepository;
        this.paymentService = paymentService;
    }

    @Override
    @Transactional
    public Reservation createReservation(Integer guestId, ReservationDto dto) {

        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new RuntimeException("Guest not found"));

        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if (dto.getCheckInDate().isAfter(dto.getCheckOutDate())) {
            throw new RuntimeException("Check-in must be before check-out");
        }

        Reservation reservation = new Reservation();
        reservation.setGuest(guest);
        reservation.setRoom(room);
        reservation.setReservationDate(LocalDate.now());
        reservation.setCheckInDate(dto.getCheckInDate());
        reservation.setCheckOutDate(dto.getCheckOutDate());
        reservation.setNumberOfGuests(dto.getNumberOfGuests());
        reservation.setStatus(ReservationStatus.pending);
        reservation.setTotalPrice(0.0);

        Reservation saved = reservationRepository.save(reservation);

        // Додај пакет
        if (dto.getPackageId() != null) {
            Offer pkg = offerRepository.findById(dto.getPackageId())
                    .orElseThrow(() -> new RuntimeException("Package not found"));

            ReservationOffer ro = new ReservationOffer();
            ro.setReservation(saved);
            ro.setOffer(pkg);
            ro.setTotalOfferPrice(0.0);
            reservationOfferRepository.save(ro);
        }

        // Додај услуги — прескокни ги оние кои се веќе дел од избраниот пакет
        if (dto.getServiceIds() != null && !dto.getServiceIds().isEmpty()) {

            // Земи ги сервисите кои се дел од избраниот пакет
            List<Integer> packageServiceIds = List.of();
            if (dto.getPackageId() != null) {
                packageServiceIds = offerRepository.findServiceIdsByPackageId(dto.getPackageId());
            }

            final List<Integer> finalPackageServiceIds = packageServiceIds;

            for (Integer serviceId : dto.getServiceIds()) {

                // Прескокни ако е веќе во пакетот
                if (finalPackageServiceIds.contains(serviceId)) {
                    continue;
                }

                Offer svc = offerRepository.findById(serviceId)
                        .orElseThrow(() -> new RuntimeException("Service not found"));

                ReservationOffer ro = new ReservationOffer();
                ro.setReservation(saved);
                ro.setOffer(svc);
                ro.setTotalOfferPrice(0.0);
                reservationOfferRepository.save(ro);
            }
        }

        return reservationRepository.findById(saved.getReservationId())
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    @Override
    public List<Reservation> findByGuestId(Integer guestId) {
        return reservationRepository.findByGuestGuestId(guestId);
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    @Transactional
    public Cancellation cancelReservation(Integer reservationId, String reason) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Резервацијата не постои"));

        long daysUntilCheckIn = ChronoUnit.DAYS.between(LocalDate.now(), reservation.getCheckInDate());
        double paid = paymentService.getPaidAmount(reservationId);

        double refund;
        if (daysUntilCheckIn > 7) {
            refund = paid;
        } else if (daysUntilCheckIn >= 3) {
            refund = paid * 0.5;
        } else {
            refund = 0.0;
        }

        Cancellation cancellation = new Cancellation();
        cancellation.setReservation(reservation);
        cancellation.setCancellationDate(LocalDate.now());
        cancellation.setRefundAmount(refund);
        cancellation.setReason(reason);
        return cancellationRepository.save(cancellation);
    }

    @Override
    @Transactional
    public void updateStatus(Integer reservationId, String status) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Резервацијата не постои"));
        reservation.setStatus(ReservationStatus.valueOf(status));
        reservationRepository.save(reservation);
    }
}
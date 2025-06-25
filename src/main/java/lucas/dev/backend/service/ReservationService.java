package lucas.dev.backend.service;

import org.springframework.stereotype.Component;
import lucas.dev.backend.model.Room;
import lucas.dev.backend.model.Reservation;
import lucas.dev.backend.repository.ReservationRepository;
import lucas.dev.backend.repository.RoomRepository;
import org.springframework.transaction.annotation.Transactional;
import lucas.dev.backend.exception.ReservationNotFoundException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lucas.dev.backend.validator.ReservationValidationService;
import lucas.dev.backend.validator.RoomLookupService;

@Component
public class ReservationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);
    private ReservationRepository reservationRepository;
    private RoomRepository roomRepository;
    private final ReservationValidationService reservationValidationService;
    private final RoomLookupService roomLookupService;

    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository,
                             ReservationValidationService reservationValidationService,
                             RoomLookupService roomLookupService) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.reservationValidationService = reservationValidationService;
        this.roomLookupService = roomLookupService;
    }

    @Transactional
    public Reservation throwReservation(Reservation reservation) {
        String roomNumber = reservation.getRoom().getRoomNumber();
        logger.info("Tentando criar reserva para quarto: {}", roomNumber);

        Room room = roomLookupService.findRoomOrThrow(roomNumber);
        reservationValidationService.validateRoomVacancy(room);
        reservationValidationService.validateReservationConflict(room, reservation.getCheckIn(), reservation.getCheckOut(), reservationRepository);

        reservation.setRoom(room);
        Reservation savedReservation = reservationRepository.save(reservation);
        logger.info("Reserva criada com sucesso. ID: {}, Quarto: {}, Hóspede: {}", 
            savedReservation.getId(), roomNumber, reservation.getGuest().getName());
        return savedReservation;
    }
    
    public Reservation findById(Long id) {
        logger.debug("Buscando reserva por ID: {}", id);
        Reservation reservation = reservationRepository.findById(id)
            .orElseThrow(() -> {
                logger.warn("Reserva não encontrada. ID: {}", id);
                return new ReservationNotFoundException("Reservation Not Found");
            });
        logger.debug("Reserva encontrada. ID: {}, Quarto: {}", id, reservation.getRoom().getRoomNumber());
        return reservation;
    }

    public List<Reservation> findAll() {
        logger.debug("Buscando todas as reservas");
        List<Reservation> reservations = reservationRepository.findAll();
        logger.debug("Encontradas {} reservas", reservations.size());
        return reservations;
    }

    @Transactional
    public Reservation update(Long id, Reservation reservation) {
        logger.info("Tentando atualizar reserva com ID: {}", id);
        Reservation existingReservation = reservationRepository.findById(id)
            .orElseThrow(() -> {
                logger.warn("Tentativa de atualizar reserva inexistente. ID: {}", id);
                return new ReservationNotFoundException("Reservation Not Found");
            });

        Room room = reservation.getRoom();
        reservationValidationService.validateReservationUpdateConflict(existingReservation, room, reservation.getCheckIn(), reservation.getCheckOut(), reservationRepository);

        existingReservation.setGuest(reservation.getGuest());
        existingReservation.setRoom(room);
        existingReservation.setCheckIn(reservation.getCheckIn());
        existingReservation.setCheckOut(reservation.getCheckOut());
        Reservation updatedReservation = reservationRepository.save(existingReservation);
        logger.info("Reserva atualizada com sucesso. ID: {}, Quarto: {}", id, room.getRoomNumber());
        return updatedReservation;
    }

    @Transactional
    public void deleteById(Long id) {
        logger.info("Tentando deletar reserva com ID: {}", id);
        if (!reservationRepository.existsById(id)) {
            logger.warn("Tentativa de deletar reserva inexistente. ID: {}", id);
            throw new ReservationNotFoundException("Reservation Not Found");
        }
        reservationRepository.deleteById(id);
        logger.info("Reserva deletada com sucesso. ID: {}", id);
    }
}

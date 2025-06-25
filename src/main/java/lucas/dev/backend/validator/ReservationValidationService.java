package lucas.dev.backend.validator;

import lucas.dev.backend.exception.RoomNotVacantException;
import lucas.dev.backend.exception.RoomAlreadyReservedException;
import lucas.dev.backend.model.Room;
import lucas.dev.backend.model.Reservation;
import lucas.dev.backend.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class ReservationValidationService {
    public void validateRoomVacancy(Room room) {
        if (!room.isVacant()) {
            throw new RoomNotVacantException("O quarto não está vago");
        }
    }
    public void validateReservationConflict(Room room, LocalDateTime checkIn, LocalDateTime checkOut, ReservationRepository reservationRepository) {
        boolean conflict = reservationRepository.existsByRoomAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
            room, checkOut, checkIn);
        if (conflict) {
            throw new RoomAlreadyReservedException("Já existe uma reserva para este quarto neste período");
        }
    }
    public void validateReservationUpdateConflict(Reservation existingReservation, Room room, LocalDateTime checkIn, LocalDateTime checkOut, ReservationRepository reservationRepository) {
        boolean conflict = reservationRepository.existsByRoomAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
            room, checkOut, checkIn);
        if (conflict && !(existingReservation.getRoom().equals(room)
                && existingReservation.getCheckIn().equals(checkIn)
                && existingReservation.getCheckOut().equals(checkOut))) {
            throw new RoomAlreadyReservedException("Já existe uma reserva para este quarto neste período");
        }
    }
} 
package lucas.dev.backend.repository;

import lucas.dev.backend.model.Reservation;
import lucas.dev.backend.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByRoomAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(Room room, LocalDateTime checkOut, LocalDateTime checkIn);
    void deleteByCheckOutBefore(LocalDateTime dateTime);
}

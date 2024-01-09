package lucas.dev.backend.repository;

import java.util.Optional;
import lucas.dev.backend.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    Optional<Room> findByNumber(Integer number);
}

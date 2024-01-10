package lucas.dev.backend.service;

import java.util.Optional;
import lucas.dev.backend.model.Room;
import lucas.dev.backend.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Transactional
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    @Transactional
    public void deleteById(int id) {
        // Exception handling has already been done in the method below
        Optional<Room> room = findById(id);

        roomRepository.delete(room.get());
    }

    public Optional<Room> findById(int id) {
        Optional<Room> room = roomRepository.findById(id);

        if (!roomRepository.existsById(id)) {
            throw new IllegalArgumentException("Object not found");
        }

        return room;
    }
}

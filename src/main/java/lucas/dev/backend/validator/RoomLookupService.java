package lucas.dev.backend.validator;

import lucas.dev.backend.model.Room;
import lucas.dev.backend.repository.RoomRepository;
import org.springframework.stereotype.Service;

@Service
public class RoomLookupService {
    private final RoomRepository roomRepository;
    public RoomLookupService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    public Room findRoomOrThrow(String roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber)
            .orElseThrow(() -> new IllegalArgumentException("Room not found with number: " + roomNumber));
    }
} 
package lucas.dev.backend.service;

import java.util.List;
import java.util.Optional;
import lucas.dev.backend.model.Room;
import lucas.dev.backend.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RoomService {

    private RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Transactional
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    @Transactional
    public void deleteById(Long id) throws Exception{
        Optional<Room> room = findById(id);

        if(room.isEmpty()){
            throw new Exception("Not Found");
        } else {
            roomRepository.delete(room.get());
        }
    }

    public Optional<Room> findById(Long id) {
        Optional<Room> room = roomRepository.findById(id);

        return room;
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Transactional
    public Room update(Long id, Room room) {
        Optional<Room> newRoom = roomRepository.findById(id);

        newRoom.get().setRoomNumber(room.getRoomNumber());
        newRoom.get().setRoomDescription(room.getRoomDescription());

        return save(room);
    }
}

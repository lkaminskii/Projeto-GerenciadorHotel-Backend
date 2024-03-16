package lucas.dev.backend.service;

import java.util.List;
import java.util.Optional;

import lucas.dev.backend.dto.RoomModel;
import lucas.dev.backend.model.Room;
import lucas.dev.backend.repository.RoomRepository;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
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
        if(!roomRepository.existsById(id)){
            throw new Exception("Object Not Found");
        }
        roomRepository.deleteById(id);
    }

    public Optional<RoomModel> findById(Long id) throws Exception{
        if(!roomRepository.existsById(id)){
            throw new Exception("Object Not Found");
        }

        Optional<Room> room = roomRepository.findById(id);
        Optional<RoomModel> roomModel = room.map(x -> new RoomModel(x));

        return roomModel;
    }

    public List<RoomModel> findAll() {
        List<Room> list = roomRepository.findAll();
        List<RoomModel> convertedList = list.stream().map(x -> new RoomModel(x)).toList();
        
        return convertedList;
    }

    @Transactional
    public Room update(Long id, Room room) {
        Optional<Room> newRoom = roomRepository.findById(id);

        newRoom.get().setRoomNumber(room.getRoomNumber());
        newRoom.get().setRoomDescription(room.getRoomDescription());

        return save(room);
    }
}

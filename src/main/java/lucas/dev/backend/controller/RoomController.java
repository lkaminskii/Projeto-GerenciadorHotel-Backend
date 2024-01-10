package lucas.dev.backend.controller;

import lucas.dev.backend.model.Room;
import lucas.dev.backend.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping(value = "/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController (RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Room insertRoom(@RequestBody Room room) {
        return roomService.save(room);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Optional<Room> findRoomById (int id) {
        return roomService.findById(id);
    }

}

package lucas.dev.backend.controller;

import lucas.dev.backend.model.Room;
import lucas.dev.backend.service.RoomService;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private RoomService roomService;

    public RoomController (RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Room insertRoom(@Valid @RequestBody Room room) {
        return roomService.save(room);
    }

    @GetMapping("/{id}")
    public ResponseEntity findRoomById (@PathVariable Long id) {
        return roomService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        return new ResponseEntity(roomService.findAll(), HttpStatus.OK);
    }


    @ResponseBody
    @DeleteMapping
    public void deleteRoomById(@RequestParam Long id) throws Exception{
        roomService.deleteById(id);
    }
    @PatchMapping
    public Room updateRoom (@PathVariable Long id, @RequestBody Room room) {
        return roomService.update(id, room);
    }
}

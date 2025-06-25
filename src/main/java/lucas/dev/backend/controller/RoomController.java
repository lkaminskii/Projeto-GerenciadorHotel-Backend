package lucas.dev.backend.controller;

import lucas.dev.backend.dto.RoomModel;
import lucas.dev.backend.model.Room;
import lucas.dev.backend.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Room> findRoomById (@PathVariable Long id) {
        return ResponseEntity.ok(roomService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok(roomService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoomById(@PathVariable Long id) {
        roomService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Room> partialUpdateRoom(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Room updatedRoom = roomService.partialUpdate(id, updates);
        return ResponseEntity.ok(updatedRoom);
    }
}

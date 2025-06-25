package lucas.dev.backend.service;

import lucas.dev.backend.model.Room;
import lucas.dev.backend.repository.RoomRepository;
import lucas.dev.backend.exception.RoomNotFoundException;
import lucas.dev.backend.exception.RoomNumberAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    private Room room;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        room = new Room();
        room.setId(1L);
        room.setRoomNumber("101");
        room.setRoomDescription("Desc");
        room.setValuePerDay(100.0);
        room.setVacant(true);
        
    }

    @Test
    void save_WhenRoomNumberNotExists_SavesRoom() {
        when(roomRepository.existsByRoomNumber(room.getRoomNumber())).thenReturn(false);
        when(roomRepository.save(room)).thenReturn(room);
        Room saved = roomService.save(room);
        assertThat(saved).isNotNull();
        verify(roomRepository).save(room);
    }

    @Test
    void save_WhenRoomNumberExists_ThrowsException() {
        when(roomRepository.existsByRoomNumber(room.getRoomNumber())).thenReturn(true);
        assertThatThrownBy(() -> roomService.save(room))
            .isInstanceOf(RoomNumberAlreadyExistsException.class);
        verify(roomRepository, never()).save(any());
    }

    @Test
    void deleteById_WhenRoomExists_DeletesRoom() {
        when(roomRepository.existsById(1L)).thenReturn(true);
        roomService.deleteById(1L);
        verify(roomRepository).deleteById(1L);
    }

    @Test
    void deleteById_WhenRoomNotExists_ThrowsException() {
        when(roomRepository.existsById(1L)).thenReturn(false);
        assertThatThrownBy(() -> roomService.deleteById(1L))
            .isInstanceOf(RoomNotFoundException.class);
        verify(roomRepository, never()).deleteById(any());
    }

    @Test
    void findById_WhenRoomExists_ReturnsRoom() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        Room found = roomService.findById(1L);
        assertThat(found).isNotNull().isEqualTo(room);
    }

    @Test
    void findById_WhenRoomNotExists_ThrowsException() {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> roomService.findById(1L))
            .isInstanceOf(RoomNotFoundException.class);
    }

    @Test
    void findAll_ReturnsListOfRooms() {
        List<Room> rooms = Arrays.asList(room, new Room());
        when(roomRepository.findAll()).thenReturn(rooms);
        List<Room> result = roomService.findAll();
        assertThat(result).hasSize(2);
    }

    @Test
    void update_WhenRoomExists_UpdatesRoom() {
        Room updated = new Room();
        updated.setRoomNumber("102");
        updated.setRoomDescription("Nova Desc");
        updated.setValuePerDay(200.0);
        updated.setVacant(false);
        
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(roomRepository.existsByRoomNumber(anyString())).thenReturn(false);
        when(roomRepository.save(any(Room.class))).thenReturn(updated);
        Room result = roomService.update(1L, updated);
        assertThat(result.getRoomNumber()).isEqualTo("102");
        assertThat(result.getValuePerDay()).isEqualTo(200.0);
    }

    @Test
    void update_WhenRoomNotExists_ThrowsException() {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> roomService.update(1L, room))
            .isInstanceOf(RoomNotFoundException.class);
    }

    @Test
    void partialUpdate_WhenRoomExists_UpdatesFields() {
        Map<String, Object> updates = new HashMap<>();
        updates.put("isVacant", false);
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(roomRepository.save(any(Room.class))).thenReturn(room);
        Room result = roomService.partialUpdate(1L, updates);
        assertThat(result.isVacant()).isFalse();
    }

    @Test
    void partialUpdate_WhenRoomNotExists_ThrowsException() {
        Map<String, Object> updates = new HashMap<>();
        updates.put("isVacant", false);
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> roomService.partialUpdate(1L, updates))
            .isInstanceOf(RoomNotFoundException.class);
    }
} 